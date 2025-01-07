package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("member_address")
@EqualsAndHashCode(callSuper = true)
public class MemberAddress extends BaseEntity {

    /**
     * 默认地址
     */
    public static final int STATE_DEFAULT = 1;

    /**
     * 普通地址
     */
    public static final int STATE_COMMON = 0;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "状态 0: 普通地址  1:默认地址")
    private Integer state;

    @Schema(description = "收货人姓名")
    private String nickName;

    @Schema(description = "收货人手机号")
    private String mobile;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "城市名称")
    private String cityName;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "县区")
    private String countyName;

    @Schema(description = "详细地址")
    private String detailAddress;

}