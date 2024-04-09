package com.eghm.dto.user;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/26 17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryRequest extends PagingQuery {

    @ApiModelProperty("锁定状态 0:锁定 1:正常")
    @OptionInt(value = {0, 1}, required = false)
    private Integer state;

    @ApiModelProperty(value = "用户类型 1:系统用户 2:商户管理员 3:商户普通用户", hidden = true)
    @Assign
    private Integer userType;
}
