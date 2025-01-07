package com.eghm.dto.member.tag;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 标签下的会员列表查询
 *
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TagMemberQueryRequest extends DatePagingComparator {

    @Schema(description = "标签id")
    @NotNull(message = "请选择标签")
    private Long tagId;

    @Schema(description = "状态 false:注销 true:正常")
    private Boolean state;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    @OptionInt(value = {0, 1, 2}, required = false, message = "性别参数非法")
    private Integer sex;

    @Schema(description = "注册渠道 PC ANDROID IOS H5 WECHAT ALIPAY")
    private String channel;

    @Schema(description = "注册开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "注册结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;
}
