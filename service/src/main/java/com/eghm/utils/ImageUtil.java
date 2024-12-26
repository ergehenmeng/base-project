package com.eghm.utils;

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
 * @since 2020/12/22
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
        g2d.setBackground(new Color(255, 255, 255));
        g2d.clearRect(0, 0, SIZE, SIZE);
        int size = bufferedImages.size();
        int x;
        int y;
        int width = getWidth(size);
        // 横向多少张图片
        int maxWidth = size > 4 ? 3 : 2;
        // 纵向多少张图片
        int maxHeight = size % maxWidth == 0 ? size / maxWidth : size / maxWidth + 1;
        // 顶部多少张图片 2
        int topWidth = size % maxWidth;
        // 顶部间距
        int topGap = getTopGap(maxWidth, maxHeight, width);
        // 顶部间距
        int leftGap = getLeftGap(maxWidth, topWidth, width);
        for (int i = 0; i < size; i++) {
            if (i < topWidth) {
                y = topGap;
                if (topWidth == 1) {
                    x = leftGap;
                } else {
                    x = leftGap + (width + GAP) * i;
                }
            } else {
                y = topGap + (width + GAP) * getRow(i, topWidth, maxWidth);
                x = GAP + (width + GAP) * ((i - topWidth) % maxWidth);
            }
            g2d.drawImage(bufferedImages.get(i), x, y, null);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(outImage, "jpg", stream);
        return stream.toByteArray();
    }

    private static int getRow(int i, int topWidth, int maxWidth) {
        if (topWidth > 0) {
            return 1 + (i - topWidth) / maxWidth;
        }
        return (i - topWidth) / maxWidth;
    }

    /**
     * 根据图片数获取图片的理论长宽 2~4宽度一样 5~9宽度一样
     *
     * @param num 数量 2-9
     * @return 宽度
     */
    private static int getWidth(int num) {
        switch (num) {
            case 2:
            case 3:
            case 4:
                return (SIZE - 3 * GAP) / 2;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return (SIZE - 4 * GAP) / 3;
            default:
                return 0;
        }
    }

    /**
     * 顶部间距
     */
    private static int getTopGap(int maxWidthImg, int maxHeightImg, int width) {
        return maxHeightImg == maxWidthImg ? GAP : calc(maxHeightImg, width);
    }

    /**
     * 左间距
     */
    private static int getLeftGap(int maxWidthImg, int topWidth, int width) {
        return (maxWidthImg == topWidth || topWidth == 0) ? GAP : calc(topWidth, width);
    }

    private static int calc(int topWidth, int width) {
        return topWidth == 2 ? (SIZE - 2 * width - GAP) / 2 : (SIZE - width) / 2;
    }

    public static void main(String[] args) throws IOException {
        List<String> list = Lists.newArrayList();
        String str = "http://img.duoziwang.com/2019/05/08211345616446.jpg";
        list.add(str);
        list.add(str);
        list.add(str);
        list.add(str);
        list.add(str);
        list.add(str);
        log.info(Base64.encode(merge(list)));
    }
}
