package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class SysOperatorRole extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long operatorId;

    @ApiModelProperty("角色id")
    private Long roleId;

}