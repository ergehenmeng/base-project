package com.eghm.configuration;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码阴影
 *
 * @author wyb
 * @since 2023/5/18
 */
public class RandomShadowGimpy extends Configurable implements GimpyEngine {

    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        NoiseProducer noiseProducer = getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        DissolveFilter dissolveFilter = new DissolveFilter();
        dissolveFilter.setDensity(0.5F + random.nextFloat(0.3F));
        BufferedImage effectImage = dissolveFilter.filter(baseImage, null);
        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();
        noiseProducer.makeNoise(distortedImage, .1F, .1F, .25F, .25F);
        noiseProducer.makeNoise(distortedImage, .1F, .25F, .5F, .9F);
        return distortedImage;
    }
}
