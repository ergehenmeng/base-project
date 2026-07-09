package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sensitive_word")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SensitiveWordPO extends BaseEntityPO {

    /** 敏感字 */
    private String keyword;

}


