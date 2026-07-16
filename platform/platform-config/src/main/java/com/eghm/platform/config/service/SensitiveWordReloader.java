package com.eghm.platform.config.service;

@FunctionalInterface
public interface SensitiveWordReloader {

    void reloadLexicon(boolean sync);
}
