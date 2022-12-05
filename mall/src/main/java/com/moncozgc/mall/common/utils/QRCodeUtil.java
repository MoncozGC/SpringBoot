package com.moncozgc.mall.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.moncozgc.mall.dto.Base64Dto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

/**
 * QRCodeUtil
 */
@Slf4j
@UtilityClass
public class QRCodeUtil {

    /**
     * 默认宽度
     */
    private static final Integer WIDTH = 140;
    /**
     * 默认高度
     */
    private static final Integer HEIGHT = 140;

    /**
     * LOGO 默认宽度
     */
    private static final Integer LOGO_WIDTH = 22;
    /**
     * LOGO 默认高度
     */
    private static final Integer LOGO_HEIGHT = 22;

    /**
     * 图片格式
     */
    private static final String IMAGE_FORMAT = "png";
    private static final String CHARSET = "utf-8";
    /**
     * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析
     */
    private static final String BASE64_IMAGE = "data:image/png;base64,%s";

    /**
     * 生成二维码，使用默认尺寸
     *
     * @param content 内容
     * @return
     */
    public String getBase64QRCode(String content) {
        return getBase64Image(content, WIDTH, HEIGHT, null, null, null);
    }

    /**
     * 生成二维码，使用默认尺寸二维码，插入默认尺寸logo
     *
     * @param content 内容
     * @param logoUrl logo地址
     * @return
     */
    public String getBase64QRCode(String content, String logoUrl) {
        return getBase64Image(content, WIDTH, HEIGHT, logoUrl, LOGO_WIDTH, LOGO_HEIGHT);
    }

    /**
     * 生成二维码
     *
     * @param content    内容
     * @param width      二维码宽度
     * @param height     二维码高度
     * @param logoUrl    logo 在线地址
     * @param logoWidth  logo 宽度
     * @param logoHeight logo 高度
     * @return
     */
    public String getBase64QRCode(String content, Integer width, Integer height, String logoUrl, Integer logoWidth, Integer logoHeight) {
        return getBase64Image(content, width, height, logoUrl, logoWidth, logoHeight);
    }

    private String getBase64Image(String content, Integer width, Integer height, String logoUrl, Integer logoWidth, Integer logoHeight) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage bufferedImage = crateQRCode(content, width, height, logoUrl, logoWidth, logoHeight);
        try {
            ImageIO.write(bufferedImage, IMAGE_FORMAT, os);
        } catch (IOException e) {
            log.error("[生成二维码，错误{}]", e);
        }
        // 转出即可直接使用
        return String.format(BASE64_IMAGE, Base64.encode(os.toByteArray()));
    }


    /**
     * 生成二维码
     *
     * @param content    内容
     * @param width      二维码宽度
     * @param height     二维码高度
     * @param logoUrl    logo 在线地址
     * @param logoWidth  logo 宽度
     * @param logoHeight logo 高度
     * @return
     */
    private BufferedImage crateQRCode(String content, Integer width, Integer height, String logoUrl, Integer logoWidth, Integer logoHeight) {
        if (StrUtil.isNotBlank(content)) {
            ServletOutputStream stream = null;
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>(4);
            // 指定字符编码为utf-8
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 设置图片的边距
            hints.put(EncodeHintType.MARGIN, 2);
            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                if (StrUtil.isNotBlank(logoUrl)) {
                    insertLogo(bufferedImage, width, height, logoUrl, logoWidth, logoHeight);
                }
                return bufferedImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 二维码插入logo
     *
     * @param source     二维码
     * @param width      二维码宽度
     * @param height     二维码高度
     * @param logoUrl    logo 在线地址
     * @param logoWidth  logo 宽度
     * @param logoHeight logo 高度
     * @throws Exception
     */
    private void insertLogo(BufferedImage source, Integer width, Integer height, String logoUrl, Integer logoWidth, Integer logoHeight) throws Exception {
        // logo 源可为 File/InputStream/URL
        Image src = ImageIO.read(new URL(logoUrl));
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (width - logoWidth) / 2;
        int y = (height - logoHeight) / 2;
        graph.drawImage(src, x, y, logoWidth, logoHeight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }


    /**
     * 获取二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws IOException
     */
    public void getQRCode(String content, OutputStream output) throws IOException {
        BufferedImage image = crateQRCode(content, WIDTH, HEIGHT, null, null, null);
        ImageIO.write(image, IMAGE_FORMAT, output);
    }

    /**
     * 获取二维码
     *
     * @param content 内容
     * @param logoUrl logo资源
     * @param output  输出流
     * @throws Exception
     */
    public void getQRCode(String content, String logoUrl, OutputStream output) throws Exception {
        BufferedImage image = crateQRCode(content, WIDTH, HEIGHT, logoUrl, LOGO_WIDTH, LOGO_HEIGHT);
        ImageIO.write(image, IMAGE_FORMAT, output);
    }

    /**
     * 插入LOGO
     *
     * @param source         二维码图片
     * @param width          二维码宽度,如果为空或小于等于0采用默认宽度
     * @param height         二维码高度,如果为空或小于等于0采用默认高度
     * @param imgPath        LOGO图片地址
     * @param isFixedLogSize 是否固定二维码中间log图标大小
     * @param text           二维码底部文本内容
     * @throws Exception
     */
    private static void insertImageAndText(BufferedImage source, Integer width, Integer height, String imgPath,
                                           boolean isFixedLogSize, String text) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int lwidth = src.getWidth(null);
        int lheight = src.getHeight(null);
        if (isFixedLogSize || lwidth >= width || lheight >= height) { // 固定LOGO大小
            if (lwidth > width) {
                lwidth = LOGO_WIDTH;
            }
            if (lheight > height) {
                lheight = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(lwidth, lheight, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(lwidth, lheight, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (width - lwidth) / 2;
        int y = (height - lheight) / 2;
        graph.drawImage(src, x, y, lwidth, lheight, null);
        Shape shape = new RoundRectangle2D.Float(x, y, lwidth, lheight, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);

        if (!StrUtil.isEmpty(text)) {
            int fontStyle = 1;
            int fontSize = 12; //
            // 计算文字开始的位置(居中显示)
            // x开始的位置：（图片宽度-字体大小*字的个数）/2
            int startX = (width - (fontSize * text.length())) / 2;
            // y开始的位置：图片高度-（图片高度-图片宽度）/2
            int startY = height - (height - width) / 2;
            graph.setColor(Color.BLUE);
            graph.setFont(new Font(null, fontStyle, fontSize)); // 字体风格与字体大小 graph.drawString(text, startX, startY);
            graph.drawString(text, startX, startY);
        }

        graph.dispose();
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * imgFilePath 待保存的本地路径
     *
     * @param base64Dto   待转码的base64
     * @param imgFilePath 保存路径
     */
    public static String GenerateImage(Base64Dto base64Dto, String imgFilePath) {
        // 获取类中的字符串
        String base64 = base64Dto.getBase64str();
        if (base64 == null || "".equals(base64)) {
            return null;
        }
        String file = "";
        FileOutputStream fops = null;
        base64 = base64.replace("data:image/png;base64,", "");
        System.out.println(base64);
        byte[] buff = DatatypeConverter.parseBase64Binary(base64);
        try {
            file = imgFilePath + "生成图片-" + System.currentTimeMillis() + ".png";
            fops = new FileOutputStream(file);
            fops.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}