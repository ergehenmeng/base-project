package com.eghm.dto.business.homestay.room;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomQueryDTO extends PagingQuery {

    @ApiModelProperty("民宿id")
    private Long homestayId;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Integer roomType;

    @ApiModelProperty(value = "几室")
    private Integer room;

    @ApiModelProperty(value = "是否有厨房")
    private Boolean hasKitchen;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

}
