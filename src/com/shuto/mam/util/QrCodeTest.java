/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.util;

import psdi.server.MXServer;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.io.File;

/**
 * ===============================================
 * Version    Author     Time        Desc
 * 1.0        xiamy      2018/05/30
 * ===============================================
 *
 * @author SumMer
 */
public class QrCodeTest {

    private static MXLogger MY_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    /**
     * logo图标
     * 值：d://1.png
     */
    private static final String LOGO_IMG = "mxe.qrcode.logo.img";

    /**
     * 二维码路径
     * 值：d://qrcode//
     */
    private static final String QR_CODE_IMG_PATH = "mxe.qrcode.img.path";

    /**
     * 后缀名
     */
    private static final String SUFFIX = ".JPG";

    public static void main(String[] args) {

        try {
            MXServer mxServer = MXServer.getMXServer();
            //二维码内容
            String content = "{\"PONUM\":\"PO1234\",\"TYPE\":\"PO\"}";
            //logo图标
            String logoImgPath = mxServer.getProperty(LOGO_IMG);
            //是否需要压缩logo
            boolean needCompress = true;
            //二维码路径
            String qrCodeImgPath = mxServer.getProperty(QR_CODE_IMG_PATH);
            //二维码名称
            String qrCodeName = "1";

            String pathName = qrCodeImgPath + qrCodeName + SUFFIX;

            QrCodeUtil qrCodeUtil = new QrCodeUtil();
            qrCodeUtil.encodeIO(content, logoImgPath, needCompress, new File(pathName));
        } catch (Exception e) {
            MY_LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
