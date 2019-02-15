/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.gzrd;

import org.springframework.web.filter.OncePerRequestFilter;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
import psdi.webclient.system.runtime.WebClientRuntime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SsoFilter extends OncePerRequestFilter {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            if (hasComeFromPotal(request, response)) {
                MXSession ms = WebClientRuntime.getMXSession(request.getSession());
                if (!ms.isConnected()) {

                    final String loginid = request.getRemoteUser();
                    if ("".equals(loginid) || loginid == null) {
                        return;
                    }
                    SsoService.sso.set("SSO");
                    //把用户放入session中，实现登陆
                    ms.setUserName(loginid);
                    ms.setClientAddr(request.getRemoteAddr());
                    ms.setClientHost(request.getRemoteHost());
                    ms.connect();
                }

            }
            filterChain.doFilter(request, response);
        } catch (MXException e) {
            MX_LOGGER.error(e);
        } finally {
            SsoService.sso.remove();
        }
    }

    private boolean hasComeFromPotal(HttpServletRequest request, HttpServletResponse response) {

        return true;
    }

    private void verifyUser(String username, String password, HttpServletResponse response) {

        if (username == null || "".equals(username)) {
            return;
        }
    }

}
