package com.eghm.dto.business.homestay.room;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomQueryDTO extends PagingQuery {

    @Schema(description = "民宿id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择民宿")
    private Long homestayId;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @Schema(description = "居住人数")
    private Integer resident;

}
