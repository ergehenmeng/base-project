package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 民宿订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay_order")
@ApiModel(value="HomestayOrder对象", description="民宿订单表")
public class HomestayOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "酒店id(冗余字段)")
    private Long homestayId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "房型id")
    private Long roomId;

    @ApiModelProperty(value = "入住开始时间(含)")
    private LocalDate startDate;

    @ApiModelProperty(value = "入住结束时间(含)")
    private LocalDate endDate;

    @ApiModelProperty(value = "几室")
    private Boolean room;

    @ApiModelProperty(value = "几厅")
    private Boolean hall;

    @ApiModelProperty(value = "几厨")
    private Boolean kitchen;

    @ApiModelProperty(value = "卫生间数")
    private Boolean washroom;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "床数")
    private Boolean bed;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Boolean roomType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "联系人手机号")
    private String mobile;

}
