package com.eghm.vo.business.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpressDetailVO {

    @ApiModelProperty("收货人姓名")
    private String nickName;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty(value = "省份id")
    @JsonIgnore
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("快递单号")
    private String expressNo;

    @ApiModelProperty("快递公司名称")
    private String expressName;

    @ApiModelProperty("物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;

}
