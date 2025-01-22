package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 店铺使用范围: 民宿, 线路, 场馆
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Data
@TableName("redeem_code_scope")
public class RedeemCodeScope {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "配置id")
    private Long redeemCodeId;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "店铺类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
