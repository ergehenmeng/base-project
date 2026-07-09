package com.eghm.operate.model;

import com.eghm.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 评论敏感词库
 * </p>
 *
 * @author author
 * @since 2021-12-04
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SensitiveWord extends BaseEntity {

    /** 敏感字 */
    private String keyword;

    public void initialize(String keyword) {
        this.keyword = keyword;
    }

}
