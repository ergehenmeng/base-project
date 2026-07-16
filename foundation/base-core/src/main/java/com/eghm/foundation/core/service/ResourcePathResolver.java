package com.eghm.foundation.core.service;

@FunctionalInterface
public interface ResourcePathResolver {

    String resolve(String path);
}
