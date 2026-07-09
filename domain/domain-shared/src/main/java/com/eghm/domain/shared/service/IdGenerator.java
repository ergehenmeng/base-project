package com.eghm.domain.shared.service;

/**
 * Application port for generating business identifiers.
 */
public interface IdGenerator {

    /**
     * Generate the next unique long id.
     *
     * @return unique id
     */
    Long nextId();
}
