package com.eghm.configuration;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.DissolveFilter;
import com.jhlabs.image.MarbleFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wyb
 * @since 2023/5/18
 */
public class RandomDissolveGimpy extends Configurable implements GimpyEngine {

    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = distortedImage.createGraphics();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        DissolveFilter dissolveFilter = new DissolveFilter();
        dissolveFilter.setDensity((float) random.nextDouble(0.6F, 0.8F));
        MarbleFilter marbleFilter = new MarbleFilter();
        marbleFilter.setXScale((float) random.nextDouble(1.0F, 1.2F));
        marbleFilter.setYScale((float) random.nextDouble(1.0F, 1.2F));
        BufferedImage effectImage = dissolveFilter.filter(baseImage, null);
        effectImage = marbleFilter.filter(effectImage, null);
        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();
        return distortedImage;
    }
}
