package com.eghm.dto.business.order.homestay;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDateComparator;
import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.validation.annotation.AfterNow;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HomestayOrderCreateDTO extends AbstractDateComparator {

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品不能为空")
    private Long roomId;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @Schema(description = "房间数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 9, message = "房间不能超过9间")
    private Integer num;

    @Schema(description = "入住人员信息列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 9, message = "入住人数不能超过9人")
    @NotEmpty(message = "住房人信息不能为空")
    private transient List<VisitorDTO> visitorList;

    @Schema(description = "入店日期(含晚上)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "入店日期不能为空")
    @AfterNow(message = "请选择合法的入店日期")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "离店日期(不含晚上)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "离店日期不能为空")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "兑换码")
    @Size(max = 20, message = "兑换码最大20字符")
    private String cdKey;

    @Schema(description = "备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;

}
