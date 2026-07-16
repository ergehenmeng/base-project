package com.eghm.platform.iam.dto;

import com.eghm.foundation.core.annotation.Assign;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/26 17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryRequest extends PagingQuery {

    @Schema(description = "锁定状态 0:锁定 1:正常")
    @OptionInt(value = {0, 1}, required = false)
    private Integer state;

    @Schema(description = "用户类型 1:系统用户 2:商户管理员 3:商户普通用户", hidden = true)
    @Assign
    private Integer userType;
}
