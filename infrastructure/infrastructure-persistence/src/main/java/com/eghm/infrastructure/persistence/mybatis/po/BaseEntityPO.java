package com.eghm.infrastructure.persistence.mybatis.po;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2021/12/18 17:12
 */
@Getter
@Setter
public abstract class BaseEntityPO {
    /** id主键 */
    private Long id;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 是否已删除 0:未删除 1:已删除 */
    private Boolean deleted;

}
