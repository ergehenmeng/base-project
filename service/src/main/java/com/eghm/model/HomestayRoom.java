package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 房型信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay_room")
@ApiModel(value="HomestayRoom对象", description="房型信息表")
public class HomestayRoom extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "民宿id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long homestayId;

    @ApiModelProperty(value = "所属商户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long merchantId;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty("是否为推荐房型 true:是 false:不是")
    private Boolean recommend;

    @ApiModelProperty(value = "几室")
    private Integer room;

    @ApiModelProperty(value = "几厅")
    private Integer hall;

    @ApiModelProperty(value = "几厨")
    private Integer kitchen;

    @ApiModelProperty(value = "卫生间数")
    private Integer washroom;

    @ApiModelProperty(value = "面积")
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    private Integer resident;

    @ApiModelProperty(value = "床数")
    private Integer bed;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    private Integer roomType;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty(value = "退款描述")
    private String refundDescribe;
}
