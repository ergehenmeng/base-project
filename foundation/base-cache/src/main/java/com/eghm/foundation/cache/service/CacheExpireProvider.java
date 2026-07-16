package com.eghm.foundation.cache.service;

@FunctionalInterface
public interface CacheExpireProvider {

    long getExpireSeconds();
}
