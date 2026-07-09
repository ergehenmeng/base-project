package com.eghm.domain.shared.event;

import java.time.LocalDateTime;

/**
 * 领域事件基类
 *
 * @author 二哥很猛
 * @since 2024/01/01
 */
public abstract class DomainEvent {

    /**
     * 事件发生时间
     */
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.occurredOn = LocalDateTime.now();
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
}
