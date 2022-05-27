package com.eghm.dao.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scenic")
@ApiModel(value="Scenic对象", description="景区信息表")
public class Scenic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "景区名称")
    private String scenicName;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3:3A 2:2A 1:A 0:其他")
    private Boolean level;

    @ApiModelProperty(value = "景区营业时间")
    private String openTime;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区描述信息")
    private String describe;

    @ApiModelProperty(value = "景区图片")
    private String coverUrl;

    @ApiModelProperty(value = "景区详细介绍信息")
    private String introduce;

}
