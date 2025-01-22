package com.eghm.dto.business.order.line;

import com.eghm.annotation.Assign;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.validation.annotation.AfterNow;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
public class LineOrderCreateDTO {

    @Schema(description = "用户ID")
    @Assign
    private Long memberId;

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品不能为空")
    private Long lineId;

    @Schema(description = "优惠券id")
    private Long couponId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99张")
    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @Schema(description = "线路人数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 99, message = "人数不能超过99人")
    private List<VisitorDTO> visitorList;

    @Schema(description = "游玩日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "游玩日期不能为空")
    @AfterNow(message = "请选择合法的游玩日期")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate configDate;

    @Schema(description = "兑换码")
    @Size(max = 20, message = "兑换码最大20字符")
    private String cdKey;

    @Schema(description = "备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
