package com.eghm.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.Img;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 殿小二
 * @date 2020/12/22
 */
@Slf4j
public class ImageUtil {

    /**
     * 长宽一样
     */
    private static final int SIZE = 240;

    /**
     * 最多9张图片
     */
    private static final int MAX_IMAGE = 9;

    /**
     * 图像之间的最小间距
     */
    private static final int GAP = 10;

    /**
     * 生成组合头像
     */
    public static byte[] merge(List<String> paths) throws IOException {

        if (paths.size() > MAX_IMAGE) {
            paths = paths.subList(0, MAX_IMAGE);
        }

        List<Image> bufferedImages = new ArrayList<>();
        int imgSize = getWidth(paths.size());
        for (String path : paths) {
            bufferedImages.add(Img.from(new URL(path)).scale(imgSize, imgSize).getImg());
        }

        BufferedImage outImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        // 生成画布
        Graphics g = outImage.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        // 设置背景色
        g2d.setBackground(new Color(255,255,255));
        g2d.clearRect(0, 0, SIZE, SIZE);

        int size = bufferedImages.size();
        int x;
        int y;
        for (int i = 0; i < size; i++) {
            int width = getWidth(size);
            switch (size) {
                case 2:
                    y = (SIZE - width) / 2;
                    x = GAP + (width + GAP) * i;
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                case 3:
                    if (i == 0) {
                        g2d.drawImage(bufferedImages.get(i), (SIZE - width) / 2, GAP, null);
                    } else {
                        y = width + 2 * GAP;
                        x = GAP + (width + GAP) * (i - 1);
                        g2d.drawImage(bufferedImages.get(i), x, y, null);
                    }
                    break;
                case 4:
                    if (i <= 1) {
                        x = GAP + (width + GAP) * i;
                        g2d.drawImage(bufferedImages.get(i), x, GAP, null);
                    } else {
                        y = width + 2 * GAP;
                        x = GAP + (width + GAP) * (i - 2);
                        g2d.drawImage(bufferedImages.get(i), x, y, null);
                    }
                    break;
                case 5:
                    y = (SIZE - width * 2 - GAP) / 2;
                    if (i <= 1) {
                        x = y + (width + GAP) * i;
                    } else {
                        y = y + width + GAP;
                        x = GAP + (width + GAP) * (i - 2);
                    }
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                case 6:
                    y = (SIZE - width * 2 - GAP) / 2;
                    if (i <= 2) {
                        x = GAP + (width + GAP) * i;
                    } else {
                        y = y + width + GAP;
                        x = GAP + (width + GAP) * (i - 3);
                    }
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                case 7:
                    if (i == 0) {
                        x = (SIZE - width) / 2;
                        y = GAP;
                    }else if (i <= 3) {
                        x = GAP + (width + GAP) * (i - 1);
                        y = GAP * 2 + width;
                    } else {
                        y = GAP * 3 + width * 2;
                        x = GAP + (width + GAP) * (i - 4);
                    }
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                case 8:
                    if (i < 2) {
                        x = (SIZE - width * 2 - GAP) / 2 + (width + GAP) * i;
                        y = GAP;
                    }else if (i <= 4) {
                        x = GAP + (width + GAP) * (i - 2);
                        y = GAP * 2 + width;
                    } else {
                        y = GAP * 3 + width * 2;
                        x = GAP + (width + GAP) * (i - 5);
                    }
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                case 9:
                    x = GAP + (width + GAP) * (i % 3);
                    y = GAP + (width + GAP) * (i / 3);
                    g2d.drawImage(bufferedImages.get(i), x, y, null);
                    break;
                default: break;
            }
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(outImage, "jpg", stream);
        return stream.toByteArray();
    }

    /**
     * 根据图片数获取图片的理论长宽 2~4宽度一样 5~9宽度一样
     * @param num 数量 2-9
     * @return 宽度
     */
    private static int getWidth(int num) {
        switch (num) {
            case 2:
            case 3:
            case 4: return (SIZE - 3 * GAP) / 2;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: return (SIZE - 4 * GAP) / 3;
            default: return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> list = Lists.newArrayList();
        list.add("https://test-img.caochangjihe.com/img/18451280511.jpeg");
        list.add("https://test-img.caochangjihe.com/img/18451280511.jpeg");
        list.add("https://test-img.caochangjihe.com/img/18451280511.jpeg");
        list.add("https://test-img.caochangjihe.com/img/18451280511.jpeg");
        byte[] merge = merge(list);
        String encode = Base64.encode(merge);
        System.out.println(encode);
    }
}
