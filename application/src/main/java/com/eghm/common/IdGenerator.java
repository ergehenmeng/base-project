package com.eghm.common;

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
