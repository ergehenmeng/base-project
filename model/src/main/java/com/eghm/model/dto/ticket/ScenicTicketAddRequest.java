package com.eghm.model.dto.ticket;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.eghm.model.validation.annotation.OptionInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛 2022/6/15 21:13
 */
@Data
public class ScenicTicketAddRequest {

    @ApiModelProperty(value = "门票所属景区")
    @NotNull(message = "请选择景区")
    private Long scenicId;

    @ApiModelProperty(value = "门票名称", required = true)
    @NotBlank(message = "门票名称不能为空")
    @Size(min = 2, max = 20, message = "门票名称长度2~20位")
    private String title;

    @ApiModelProperty(value = "门票种类 1: 成人票 2: 老人票 3:儿童票", required = true)
    @OptionInt(value = {1, 2, 3}, message = "门票种类错误")
    private Integer category;

    @ApiModelProperty(value = "门票封面图", required = true)
    @NotBlank(message = "门票封面图不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价", required = true)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "成本价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer costPrice;

    @ApiModelProperty(value = "提前多少天购票", required = true)
    @NotNull(message = "提前购票时间不能为空")
    private Integer advanceDay;

    @ApiModelProperty(value = "单次最大购买数量", required = true)
    @NotNull(message = "单次最大购买数量不能为空")
    private Integer quota;

    @ApiModelProperty(value = "开始预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "剩余库存", required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "景区介绍", required = true)
    @NotBlank(message = "景区介绍不能为空")
    private String introduce;
    // TODO 待完善
    @ApiModelProperty(value = "有效期购买之日起")
    private Integer validDays;

    @ApiModelProperty(value = "生效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectDate;

    @ApiModelProperty(value = "失效日期(包含)")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "使用范围: 1:周一 2:周二 4:周三 8:周四 16:周五 32:周六 64:周日")
    @OptionInt(value = {1, 2, 4, 8, 16, 32, 64}, message = "使用范围格式错误")
    private Integer useScope;

    @ApiModelProperty(value = "核销方式 1:手动核销 2:自动核销 (凌晨自动核销)")
    @OptionInt(value = {1, 2}, message = "核销方式格式错误")
    private Integer verificationType;

    @ApiModelProperty(value = "是否支持退款 1:支持 0:不支持")
    @OptionInt(value = {0, 1}, message = "是否支持退款不能为空")
    private Boolean supportRefund;

    @ApiModelProperty(value = "是否实名购票 0:不实名 1:实名")
    @OptionInt(value = {0, 1}, message = "是否实名购票不能为空")
    private Boolean realBuy;
        
}
