package com.eghm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2021/12/18 17:12
 */
@Getter
@Setter
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleted;

}
