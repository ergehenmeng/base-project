package com.eghm.dto.business.venue;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.VenueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueQueryDTO extends PagingQuery {

    @Schema(description = "场馆类型")
    private VenueType venueType;

    @Schema(description = "经度")
    @DecimalMin(value = "-180", message = "经度应(-180, 180]范围内", inclusive = false)
    @DecimalMax(value = "180", message = "经度应(-180, 180]范围内")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    @DecimalMin(value = "-90", message = "纬度应[-90, 90]范围内")
    @DecimalMax(value = "90", message = "纬度应[-90, 90]范围内")
    private BigDecimal latitude;

    @Schema(description = "是否按距离排序 1:正序 2:倒序")
    private Boolean sortByDistance;
}
