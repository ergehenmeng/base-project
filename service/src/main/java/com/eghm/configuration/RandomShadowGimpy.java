package com.eghm.configuration;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.ShadowFilter;
import com.jhlabs.image.TransformFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wyb
 * @since 2023/5/18
 */
public class RandomShadowGimpy extends Configurable implements GimpyEngine {

    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        NoiseProducer noiseProducer = getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();

        ShadowFilter shadowFilter = new ShadowFilter();
        shadowFilter.setRadius(10);
        shadowFilter.setDistance(5);
        shadowFilter.setOpacity(1);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        RippleFilter rippleFilter = new RippleFilter();
        rippleFilter.setWaveType(RippleFilter.SINE);
        rippleFilter.setXAmplitude(7.6F);

        rippleFilter.setYAmplitude(random.nextFloat() + 1.0F);
        rippleFilter.setXWavelength(random.nextInt(7) + 8F);
        rippleFilter.setYWavelength(random.nextInt(3) + 2F);
        rippleFilter.setEdgeAction(TransformFilter.BILINEAR);

        BufferedImage effectImage = rippleFilter.filter(baseImage, null);
        effectImage = shadowFilter.filter(effectImage, null);

        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();

        noiseProducer.makeNoise(distortedImage, .1F, .1F, .25F, .25F);
        noiseProducer.makeNoise(distortedImage, .1F, .25F, .5F, .9F);

        return distortedImage;
    }
}
