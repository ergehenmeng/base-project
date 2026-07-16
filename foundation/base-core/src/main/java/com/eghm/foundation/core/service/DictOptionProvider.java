package com.eghm.foundation.core.service;

@FunctionalInterface
public interface DictOptionProvider {

    String[] getOptions(String key);
}
