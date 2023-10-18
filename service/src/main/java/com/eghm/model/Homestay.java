package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 民宿信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay")
public class Homestay extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "民宿名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty(value = "民宿所属商家")
    private Long merchantId;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市")
    private Long cityId;

    @ApiModelProperty(value = "县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "描述信息")
    private String intro;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "特色服务")
    private String keyService;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty("分数")
    private BigDecimal score;
}
