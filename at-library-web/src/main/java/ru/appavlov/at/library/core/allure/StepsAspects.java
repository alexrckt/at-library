package ru.appavlov.at.library.core.allure;

import cucumber.api.java.ru.И;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.qameta.allure.util.AspectUtils.getName;
import static io.qameta.allure.util.AspectUtils.getParameters;
import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;

@Aspect
public class StepsAspects {

    private static final InheritableThreadLocal<AllureLifecycle> LIFECYCLE =
            new InheritableThreadLocal<AllureLifecycle>() {
                @Override
                protected AllureLifecycle initialValue() {
                    return Allure.getLifecycle();
                }
            };

    @Pointcut("@annotation(cucumber.api.java.ru.И)")
    public void withStepAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Before("anyMethod() && withStepAnnotation()")
    public void stepStart(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final И step = methodSignature.getMethod().getAnnotation(И.class);

        final String uuid = UUID.randomUUID().toString();
        String name = getName(step.value(), joinPoint);
        final List<Parameter> parameters = getParameters(methodSignature, joinPoint.getArgs());
        name = updateName(name, parameters);

        final StepResult result = new StepResult()
                .setName(name)
                .setParameters(parameters);

        boolean stepInfoDuplicate = getLifecycle().getCurrentTestCaseOrStep().get().contains(name);

        if (!stepInfoDuplicate) {
            getLifecycle().startStep(uuid, result);
        }
    }

    private String updateName(String name, List<Parameter> parameters) {
        name = name.replace("\"([^\"]*)\"", "PARAMETER");
        String[] split = name.split("PARAMETER");
        StringBuilder updateName = new StringBuilder();

        for (int i = 0; i < split.length; i++) {
            updateName.append(split[i]);
            if (i < parameters.size()) {
                updateName.append("\"").append(parameters.get(i).getValue()).append("\"");
            }
        }

        updateName = new StringBuilder(updateName.toString()
                .replace("^", "")
                .replace("$", ""));

        return updateName.toString();
    }

    @AfterThrowing(pointcut = "anyMethod() && withStepAnnotation()", throwing = "e")
    public void stepFailed(final Throwable e) {
        getLifecycle().updateStep(s -> s
                .setStatus(getStatus(e).orElse(Status.BROKEN))
                .setStatusDetails(getStatusDetails(e).orElse(null)));
        getLifecycle().stopStep();
    }

    @AfterReturning(pointcut = "anyMethod() && withStepAnnotation()")
    public void stepStop() {
        getLifecycle().updateStep(s -> s.setStatus(Status.PASSED));
        getLifecycle().stopStep();
    }

    public static AllureLifecycle getLifecycle() {
        return LIFECYCLE.get();
    }

    private boolean isTopLevelStep() {
        final Optional<String> currentTestCase = getLifecycle().getCurrentTestCase();
        final Optional<String> currentStep = getLifecycle().getCurrentTestCaseOrStep();
        if (currentTestCase.isPresent() && currentStep.isPresent()) {
            System.out.println(currentStep.get() + " contains " + currentTestCase.get());
            return currentStep.get().contains(currentTestCase.get());
        }
        return true;
    }
}