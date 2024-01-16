package com.eghm.dto.ext;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 包含操作人或操作姓名均可以继承此类
 *
 * @author 二哥很猛
 * @date 2019/8/8 11:47
 */
@Getter
@Setter
public class ActionRecord {

    @Assign
    @ApiModelProperty(value = "操作人姓名", hidden = true)
    private String userName;

    @Assign
    @ApiModelProperty(value = "操作人id", hidden = true)
    private Long userId;

}
