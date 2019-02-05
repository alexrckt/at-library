package ru.bcs.at.library.core.log;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.log4j.Log4j2;

/**
 * <h1 style="color: green; font-size: 2.2em">Класс утилит, для чтения RestAssured в Log4j2</h1>
 *
 * @author Anton Pavlov
 */
@Log4j2
public class Log4jRestAssuredFilter implements OrderedFilter {
    @Override
    public int getOrder() {
        return 10000;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        log.debug("REQUEST method='{}' uri='{}'", requestSpec.getMethod(), requestSpec.getURI());
        if (log.isTraceEnabled()) {
            doLogHeaders(requestSpec.getHeaders());
            if (requestSpec.getBody() != null) {
                String formatted = DocumentFormatter.createPrettyPrint(requestSpec.getBody().toString(), requestSpec.getContentType());
                log.trace("BODY=[\n{}]", formatted);
            }
        }
        Response response = filterContext.next(requestSpec, responseSpec);
        log.debug("RESPONSE status='{}'", response.getStatusLine().toString());
        if (log.isTraceEnabled()) {
            doLogHeaders(response.getHeaders());
            if (response.getBody() != null) {
                String formatted = DocumentFormatter.createPrettyPrint(response.getBody().asString(), response.getContentType());
                log.trace("BODY=[\n{}]", formatted);
            }
        }
        return response;
    }

    private void doLogHeaders(Headers headers) {
        log.trace("HEADERS=[\n{}]", headers);
    }
}
