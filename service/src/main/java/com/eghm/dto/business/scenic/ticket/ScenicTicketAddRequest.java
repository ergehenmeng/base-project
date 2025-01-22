package com.eghm.dto.business.scenic.ticket;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.dto.ext.DateComparator;
import com.eghm.enums.ref.TicketType;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/15 21:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScenicTicketAddRequest extends DateComparator {

    @Schema(description = "门票所属景区", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择景区")
    private Long scenicId;

    @Schema(description = "门票名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "门票名称不能为空")
    @Size(min = 2, max = 20, message = "门票名称长度2~20位")
    @WordChecker(message = "门票名称存在敏感词")
    private String title;

    @Schema(description = "门票种类 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "门票种类不能为空")
    private TicketType category;

    @Schema(description = "组合票关联的票id")
    private List<Long> ticketIds;

    @Schema(description = "划线价")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer linePrice;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer salePrice;

    @Schema(description = "提前多少天购票", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "提前购票时间不能为空")
    private Integer advanceDay;

    @Schema(description = "单次最大购买数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单次最大购买数量不能为空")
    private Integer quota;

    @Schema(description = "虚拟销量", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "虚拟销量不能小于0")
    private Integer virtualNum;

    @Schema(description = "开始预订时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止预订时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "剩余库存", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @Schema(description = "门票介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "门票介绍不能为空")
    @WordChecker(message = "门票介绍存在敏感词")
    @Expose(serialize = false)
    private String introduce;

    @Schema(description = "是否实名购票 false:不实名 true:实名", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean realBuy;

}
