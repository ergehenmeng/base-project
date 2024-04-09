package com.eghm.vo.business.homestay.room;

import com.eghm.enums.ref.RoomType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
public class HomestayRoomListVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty("房型图片")
    private String coverUrl;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;
}
