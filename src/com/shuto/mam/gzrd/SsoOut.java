/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.gzrd;

import org.springframework.web.filter.OncePerRequestFilter;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: SsoOut
 * @Package com.shuto.mam.gzrd
 * @Description: 退出并 注销session
 * @date 2018-10-18 17:08
 */
public class SsoOut extends OncePerRequestFilter {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws IOException {

        String url = "http://" + request.getServerName() + "/maximo/ui/sso";
        MX_LOGGER.info(url);
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect(url);
            return;
        }
        //清除session
        session.invalidate();
        response.sendRedirect(url);
    }
}
