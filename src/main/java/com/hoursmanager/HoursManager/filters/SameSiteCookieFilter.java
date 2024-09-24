package com.hoursmanager.HoursManager.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SameSiteCookieFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if (response instanceof HttpServletResponse && request instanceof HttpServletRequest)
        {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            Cookie[] cookies = httpServletRequest.getCookies();

            if (cookies != null)
            {
                for (Cookie cookie : cookies)
                {
                    if ("JSESSIONID".equals(cookie.getName()))
                    {
                        // Create a modified version of the cookie with the SameSite attribute set
                        String cookieValue = String.format("%s=%s; Path=/; HttpOnly; SameSite=None; Secure",
                                cookie.getName(), cookie.getValue());
                        httpServletResponse.setHeader("Set-Cookie", cookieValue);
                    }
                }
            }
        }

        // Continue with the rest of the filter chain
        chain.doFilter(request, response);
    }
}