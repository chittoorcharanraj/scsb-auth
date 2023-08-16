package org.recap.config;

import org.recap.ScsbCommonConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.recap.spring.SwaggerAPIProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by hemalathas on 7/9/16.
 */
@Component
public class SwaggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean continueExport;
        String date = new Date().toString();
        String key = request.getHeader(ScsbCommonConstants.API_KEY);
        if (key != null && SwaggerAPIProvider.getInstance().getSwaggerApiKey().equalsIgnoreCase(key)) {
            continueExport = true;
        } else {
            continueExport = false;
            response.setStatus(401);
            response.setHeader("Date" , date);
            response.getWriter().println("Authentication Failed");
        }
        return continueExport;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //Do nothing
    }
}




