package com.eghm.model.dto.business.order;

import com.eghm.model.validation.annotation.IdCard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Data
public class VisitorVO {

    @ApiModelProperty("用户姓名")
    @Size(min = 2, max = 10, message = "游客姓名2~10字符")
    private String userName;

    @ApiModelProperty("身份证")
    @IdCard
    private String idCard;

}
