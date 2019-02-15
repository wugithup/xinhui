/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.qrcode;

import com.google.zxing.WriterException;
import com.shuto.mam.util.QrCodeUtil;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ===============================================
 * Version    Author     Time        Desc
 * 1.0        xiamy      2018/05/31
 * ===============================================
 *
 * @author SumMer
 */
public class QrCodeSerlet extends HttpServlet {

    /**
     * LOGO路径，在maximo系统配置中配置
     */
    private static final String LOGO_IMG = "mxe.qrcode.logo.img";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        MXServer mxServer = MXServer.getMXServer();
        //logo图标
        String logoImgPath = mxServer.getProperty(LOGO_IMG);

        //二维码内容
        String content = "";
        //是否压缩logo图标
        boolean needCompress = true;

        String m = req.getParameter("m");
        if (!"".equals(m) && m != null) {
            content = m;
        }
        try {
            QrCodeUtil qrCodeUtil = new QrCodeUtil();
            BufferedImage image = qrCodeUtil.createImage(content, logoImgPath, needCompress);
            resp.setContentType("image/png");
            ImageIO.write(image, "png", resp.getOutputStream());
        } catch (MXApplicationException me) {
            me.printStackTrace();
        } catch (WriterException we) {
            we.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
