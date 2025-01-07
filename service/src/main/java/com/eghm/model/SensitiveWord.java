package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("sensitive_word")
@EqualsAndHashCode(callSuper = true)
public class SensitiveWord extends BaseEntity {

    @Schema(description = "敏感字")
    private String keyword;

}
