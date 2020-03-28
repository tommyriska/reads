package no.badask.reads.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingServiceImpl implements LoggingService {

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    // FIXME: Logging requests does not work for some reason
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        log.info("==================== Request start ====================");
        log.info("method:       {}", httpServletRequest.getMethod());
        log.info("path:         {}", httpServletRequest.getRequestURL());
        log.info("headers:      {}", buildHeadersMap(httpServletRequest));
        if (!parameters.isEmpty()) {
            log.info("parameters:   {}", parameters);
        }
        if (body != null) {
            log.info("body:         {}", body);
        }
        log.info("==================== Request end ====================");
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        log.info("==================== Response start ====================");
        log.info("method:   {}", httpServletRequest.getMethod());
        log.info("path:     {}", httpServletRequest.getRequestURL());
        log.info("headers:  {}", buildHeadersMap(httpServletResponse));
        log.info("body:     {}", body);
        log.info("==================== Response end ====================");
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
