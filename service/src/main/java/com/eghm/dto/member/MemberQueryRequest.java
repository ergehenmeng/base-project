package com.eghm.dto.member;

import com.eghm.annotation.DateFormatter;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
import com.eghm.validation.annotation.OptionInt;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberQueryRequest extends AbstractDatePagingComparator {

    @Schema(description = "状态 false:冻结 true:正常")
    private Boolean state;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    @OptionInt(value = {0, 1, 2}, required = false, message = "性别参数非法")
    private Integer sex;

    @Schema(description = "注册渠道 PC ANDROID IOS H5 WECHAT ALIPAY")
    private String channel;

    @Schema(description = "邀请人手机号(用来查看该手机号邀请的下级会员)")
    private String mobile;

    @Schema(description = "注册开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "注册结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
}
