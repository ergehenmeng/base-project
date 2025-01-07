package com.eghm.vo.business.homestay.room.config;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.eghm.vo.business.BaseConfigResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2022/6/29 22:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RoomConfigResponse extends BaseConfigResponse {

    @Schema(description = "是否可订 false:不可订 true:可定")
    private Boolean state;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @Schema(description = "库存数")
    private Integer stock;

}
