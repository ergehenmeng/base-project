package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDateComparator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueSitePriceRequest extends AbstractDateComparator {

    @Assign
    @Schema(description = "所属场馆", hidden = true)
    private Long venueId;

    @Schema(description = "场地id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择场地")
    private Long venueSiteId;

    @Schema(description = "周期 1:星期一 2:星期二 ... 7:星期日", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择周期")
    public transient List<Integer> week;

    @Schema(description = "开始日期yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止日期yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "截止日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "场次价格",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请设置场次价格")
    private transient List<PriceRequest> priceList;
}
