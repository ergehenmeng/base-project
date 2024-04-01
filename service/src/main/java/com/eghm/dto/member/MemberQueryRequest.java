package com.eghm.dto.member;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
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
public class MemberQueryRequest extends DatePagingComparator {

    @ApiModelProperty("状态 false:注销 true:正常")
    private Boolean state;

    @ApiModelProperty("性别 0:未知 1:男 2:女 ")
    @OptionInt(value = {0, 1, 2}, required = false, message = "性别参数非法")
    private Integer sex;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private String channel;

    @ApiModelProperty("邀请人手机号(用来查看该手机号邀请的下级会员)")
    private String mobile;

    @ApiModelProperty("注册开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("注册结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;
}
