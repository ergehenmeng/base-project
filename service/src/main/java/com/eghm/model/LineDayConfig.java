package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 线路日配置信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("line_day_config")
public class LineDayConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "线路商品id")
    private Long lineId;

    @ApiModelProperty(value = "行程排序(第几天)")
    private Integer routeIndex;

    @ApiModelProperty(value = "出发地点")
    private String startPoint;

    @ApiModelProperty(value = "结束地点")
    private String endPoint;

    @ApiModelProperty(value = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他")
    private Integer trafficType;

    @ApiModelProperty(value = "包含就餐 1:早餐 2:午餐 4:晚餐")
    private Integer repast;

    @ApiModelProperty(value = "详细描述信息")
    private String depict;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
