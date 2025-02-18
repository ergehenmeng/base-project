package com.eghm.dto.statistics;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.AbstractDateComparator;
import com.eghm.enums.ProductType;
import com.eghm.enums.SelectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductRequest extends AbstractDateComparator {

    @ApiModelProperty(value = "开始日期 yyyy-MM-dd", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期 yyyy-MM-dd", required = true)
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @ApiModelProperty("商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty(value = "查询类型")
    private SelectType selectType;

}
