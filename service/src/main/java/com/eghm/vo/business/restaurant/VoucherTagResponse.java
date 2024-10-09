package com.eghm.vo.business.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 餐饮标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-09
 */
@Data
public class VoucherTagResponse  {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "餐饮店铺ID")
    private Long restaurantId;

    @ApiModelProperty(value = "餐饮店铺名称")
    private String restaurantName;

    @ApiModelProperty(value = "状态 0:禁用 1:正常")
    private Boolean state;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
