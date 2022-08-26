package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value="LineDayConfig对象", description="线路日配置信息")
public class LineDayConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "线路商品id")
    private Long lineId;

    @ApiModelProperty(value = "行程排序(第几天)")
    private Integer routeIndex;

    @ApiModelProperty(value = "出发地点")
    private String startPoint;

    @ApiModelProperty(value = "结束地点")
    private String endPoint;

    @ApiModelProperty(value = "交通方式 1:飞机 2:汽车 3:轮船 4:火车 5:其他")
    private Boolean trafficType;

    @ApiModelProperty(value = "包含就餐 1:早餐 2:午餐 4:晚餐")
    private Integer repast;

    @ApiModelProperty(value = "详细描述信息")
    private String describe;

}
