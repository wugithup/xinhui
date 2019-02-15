/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import psdi.util.MXApplicationException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * ===============================================
 * Version    Author     Time        Desc
 * 1.0        xiamy      2018/05/30
 * ===============================================
 *
 * @author SumMer
 */
public class QrCodeUtil {

    private static MXLogger MY_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private static final String CHARSET = "UTF-8";

    private static final String FORMAT_NAME = "JPG";

    /**
     * 二维码颜色
     */
    private static final int BLACK = 0xFF000000;

    /**
     * 二维码颜色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 二维码尺寸
     */
    private static final int QRCODE_SIZE = 300;

    /**
     * LOGO宽度
     */
    private static final int WIDTH = QRCODE_SIZE / 5;

    /**
     * LOGO高度
     */
    private static final int HEIGHT = QRCODE_SIZE / 5;


    /**
     * @param content      二维码内容
     * @param imgPath      logo图标
     * @param needCompress 是否需要压缩logo图标
     * @return 返回二维码图片
     * @throws IOException            IO异常
     * @throws MXApplicationException MAXIMO异常
     * @throws WriterException        读写流异常
     */
    public BufferedImage createImage(String content, String imgPath, boolean needCompress)
            throws IOException, MXApplicationException, WriterException {

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix =
                new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, imgPath, needCompress);
        return image;
    }


    /**
     * 添加logo图标
     *
     * @param source       二维码图片
     * @param imgPath      logo图标路径
     * @param needCompress 是否需要压缩logo图标
     * @throws MXApplicationException MAXIMO异常信息
     * @throws IOException            IO异常信息
     */
    private void insertImage(BufferedImage source, String imgPath, boolean needCompress)
            throws MXApplicationException, IOException {

        File file = new File(imgPath);
        if (!file.exists()) {
            throw new MXApplicationException("提示", "LOGO图标不存在： " + imgPath + "");
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 获取生成二维码
     *
     * @param content      二维码内容
     * @param imgPath      logo路径
     * @param needCompress 是否需要压缩logo图标
     * @param pathname     二维码存放路径
     * @throws MXApplicationException MAXIMO异常信息
     */
    public void encodeIO(String content, String imgPath, Boolean needCompress, File pathname)
            throws MXApplicationException {

        try {
            BufferedImage image = createImage(content, imgPath, needCompress);
            ImageIO.write(image, FORMAT_NAME, pathname);
            MY_LOGGER.info("二维码生成成功");
        } catch (Exception e) {
            MY_LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new MXApplicationException("提示", "二维码生成失败");
        }
    }

    /**
     * 获取生成二维码的图片流
     *
     * @param content      二维码内容
     * @param imgPath      logo图标路径
     * @param needCompress 是否需要压缩logo图标
     * @return 建储存图片二进制流的输出流
     * @throws MXApplicationException MAXIMO异常信息
     */
    public ByteArrayOutputStream encodeIO(String content, String imgPath, Boolean needCompress)
            throws MXApplicationException {

        ByteArrayOutputStream baos = null;
        try {

            BufferedImage image = createImage(content, imgPath, needCompress);
            //创建储存图片二进制流的输出流
            baos = new ByteArrayOutputStream();
            //将二进制数据写入ByteArrayOutputStream
            ImageIO.write(image, FORMAT_NAME, baos);
        } catch (Exception e) {
            MY_LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new MXApplicationException("提示", "生成二维码的图片流失败");
        }
        return baos;
    }

}
