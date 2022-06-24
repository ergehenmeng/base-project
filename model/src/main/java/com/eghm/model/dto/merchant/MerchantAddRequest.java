package com.eghm.model.dto.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Data
public class MerchantAddRequest {
    
    @ApiModelProperty(value = "商家名称",required = true)
    @NotBlank(message = "商家名称不能为空")
    @Size(min = 2, max = 20, message = "商家名称长度2~20位")
    private String merchantName;
    
    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路", required = true)
    @NotNull(message = "请选择商家类型")
    @Min(value = 1, message = "商家类型错误")
    @Max(value = 31, message = "商家类型错误")
    private Integer type;
    
    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    @Size(min = 2, max = 10, message = "联系人姓名长度2~10位")
    private String nickName;
    
    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    @Size(min = 7, max = 15, message = "联系人电话长度7~15位")
    private String mobile;
    
    @ApiModelProperty(value = "账号名称", required = true)
    @NotBlank(message = "账号名称不能为空")
    @Size(min = 6, max = 20, message = "账号名称长度6~20位")
    private String userName;

}
