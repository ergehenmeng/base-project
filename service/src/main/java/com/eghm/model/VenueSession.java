package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 场馆场次价格信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("venue_session")
public class VenueSession extends BaseEntity {

    @ApiModelProperty(value = "所属场馆id")
    private Long venueId;

    @ApiModelProperty(value = "场次名称")
    private String title;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

}
