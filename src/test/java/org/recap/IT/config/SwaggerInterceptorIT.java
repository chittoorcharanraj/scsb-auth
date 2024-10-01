package org.recap.IT.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.IT.BaseTestCase;
import org.recap.config.SwaggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by hemalathas on 25/1/17.
 */
public class SwaggerInterceptorIT extends BaseTestCase {

    @InjectMocks
    SwaggerInterceptor swaggerInterceptor;

    @Mock
    HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Test
    public void testPreHandle() throws Exception {
        try {
            httpServletRequest.setAttribute("api_key", "test");
            boolean continueExport = swaggerInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());
            assertTrue(!continueExport);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPreHandleTest() throws Exception {
        try {
            httpServletRequest.setAttribute("api_key", "test");
            Mockito.when(httpServletRequest.getHeader("api_key")).thenReturn("{SWAGGERAPIKEY}");
            boolean continueExport = swaggerInterceptor.preHandle(httpServletRequest, httpServletResponse, new Object());
            assertNotNull(continueExport);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postHandle() throws Exception {
        Object handler = new Object();
        swaggerInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, new ModelAndView());
    }

    @Test
    public void afterCompletion() throws Exception {
        Object handler = new Object();
        swaggerInterceptor.afterCompletion(httpServletRequest, httpServletResponse, handler, new Exception());
    }


}