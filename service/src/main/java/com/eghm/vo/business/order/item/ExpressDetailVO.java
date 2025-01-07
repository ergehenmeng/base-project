package com.eghm.vo.business.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpressDetailVO {

    @Schema(description = "收货人姓名")
    private String nickName;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "省份id")
    @JsonIgnore
    private Long provinceId;

    @Schema(description = "城市id")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区id")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "快递单号")
    private String expressNo;

    @Schema(description = "快递公司名称")
    private String expressName;

    @Schema(description = "物流节点信息(默认倒序)")
    private List<ExpressVO> expressList;

}
