package com.eghm.vo.business.homestay.room;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/12
 */

@Data
public class HomestayRoomVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty("是否为推荐房型 true:是 false:不是")
    private Boolean recommend;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "房型类型  1:标间 2:大床房 3:双人房 4: 钟点房, 5:套房 6:合租")
    private RefundType roomType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "屋内设施")
    private String infrastructure;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    private String refundDescribe;

    @ApiModelProperty("最低价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;
}
