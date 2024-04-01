package com.eghm.dto.business.homestay.room;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.RoomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "民宿id", required = true)
    @NotNull(message = "请选择民宿")
    private Long homestayId;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4: 钟点房, 5:套房 6:合租")
    private RoomType roomType;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

}
