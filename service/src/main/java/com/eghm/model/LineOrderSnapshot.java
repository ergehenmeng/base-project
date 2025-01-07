package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 线路订单每日行程配置快照表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-01
 */
@Data
@TableName("line_order_snapshot")
public class LineOrderSnapshot {

    @Schema(description = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "线路商品id")
    private Long lineId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "行程排序(第几天)")
    private Integer routeIndex;

    @Schema(description = "出发地点")
    private String startPoint;

    @Schema(description = "结束地点")
    private String endPoint;

    @Schema(description = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他")
    private Integer trafficType;

    @Schema(description = "包含就餐 1:早餐 2:午餐 4:晚餐")
    private Integer repast;

    @Schema(description = "详细描述信息")
    private String depict;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
