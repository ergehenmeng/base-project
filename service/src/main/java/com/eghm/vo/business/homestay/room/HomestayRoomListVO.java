package com.eghm.vo.business.homestay.room;

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

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Integer roomType;
}
