package com.eghm.vo.business.homestay.room;

import com.eghm.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
public class HomestayRoomListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "房型名称")
    private String title;

    @Schema(description = "房型图片")
    private String coverUrl;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;
}
