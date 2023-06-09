package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    
    /**
     * 2:民宿 4: 餐饮 8:特产 16:线路 只有一个店铺,  景区不关联店铺
     */
    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路, 多选时数字相加", required = true)
    @NotNull(message = "请选择商家类型")
    @RangeInt(min = 1, max = 31, message = "商家类型错误")
    private Integer type;
    
    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    @Size(min = 2, max = 10, message = "联系人姓名长度2~10位")
    private String nickName;
    
    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    @Size(min = 7, max = 15, message = "联系人电话长度7~15位")
    private String mobile;

}
