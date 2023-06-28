package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论敏感词库
 * </p>
 *
 * @author author
 * @since 2021-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sensitive_word")
@NoArgsConstructor
public class SensitiveWord extends BaseEntity {

    @ApiModelProperty("敏感字")
    private String keyword;

}
