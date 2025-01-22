package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.RefundType;
import com.eghm.enums.RoomType;
import com.eghm.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "民宿id")
    private Long homestayId;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "房型名称")
    private String title;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "是否为推荐房型 true:是 false:不是")
    private Boolean recommend;

    @Schema(description = "面积")
    private Integer dimension;

    @Schema(description = "居住人数")
    private Integer resident;

    @Schema(description = "房型类型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    private RoomType roomType;

    @Schema(description = "订单确认方式: 1: 自动确认 2:手动确认")
    private Integer confirmType;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "屋内设施")
    private String infrastructure;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @Schema(description = "退款描述")
    private String refundDescribe;

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}
