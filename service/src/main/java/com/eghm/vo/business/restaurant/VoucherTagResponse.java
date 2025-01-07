package com.eghm.vo.business.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "餐饮店铺ID")
    private Long restaurantId;

    @Schema(description = "餐饮店铺名称")
    private String restaurantName;

    @Schema(description = "状态 0:禁用 1:正常")
    private Boolean state;

    @Schema(description = "备注信息")
    private String remark;
}
