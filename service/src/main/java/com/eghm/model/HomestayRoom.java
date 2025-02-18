package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.RefundType;
import com.eghm.enums.RoomType;
import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 房型信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@TableName("homestay_room")
@EqualsAndHashCode(callSuper = true)
public class HomestayRoom extends BaseEntity {

    @ApiModelProperty(value = "民宿id")
    private Long homestayId;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty("是否为推荐房型 true:是 false:不是")
    private Boolean recommend;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @ApiModelProperty("订单确认方式: 1: 自动确认 2:手动确认")
    private Integer confirmType;

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

    @ApiModelProperty("创建日期")
    private LocalDate createDate;

    @ApiModelProperty(value = "创建月份")
    private String createMonth;
}
