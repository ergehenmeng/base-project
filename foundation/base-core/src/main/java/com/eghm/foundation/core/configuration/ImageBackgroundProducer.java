package com.eghm.foundation.core.configuration;

import com.google.code.kaptcha.BackgroundProducer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author wyb-eghm
 * @since 2026/7/31
 */
public class ImageBackgroundProducer implements BackgroundProducer {
    
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        // 创建新图像并设置背景色
        BufferedImage imageWithBg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imageWithBg.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.LIGHT_GRAY);
        this.drawDots(g, width, height);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return imageWithBg;
    }
    
    private void drawDots(Graphics2D g, int width, int height) {
        int dotCount = 200;
        for (int i = 0; i < dotCount; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int radius = (int) (Math.random() * 3 + 1);
            g.fillOval(x, y, radius, radius);
        }
    }
}
