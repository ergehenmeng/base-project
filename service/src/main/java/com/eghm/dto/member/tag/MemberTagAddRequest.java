package com.eghm.dto.member.tag;

import com.eghm.enums.Channel;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class MemberTagAddRequest {

    @ApiModelProperty(value = "标签名称", required = true)
    @NotBlank(message = "标签名称不能为空")
    @Size(min = 2, max = 10, message = "标签名称长度2~10位")
    @WordChecker(message = "标签名称存在敏感词")
    private String title;

    @ApiModelProperty(value = "注册开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "注册开始日期不能为空")
    private LocalDate registerStartDate;

    @ApiModelProperty(value = "注册截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registerEndDate;

    @ApiModelProperty(value = "最近几天有消费")
    private Integer consumeDay;

    @ApiModelProperty(value = "最低消费次数")
    private Integer consumeNum;

    @ApiModelProperty(value = "最低消费金额")
    private Integer consumeAmount;

    @ApiModelProperty("注册渠道 PC ANDROID IOS, H5 OTHER")
    private Channel channel;

    @ApiModelProperty("性别 0:未知 1:男 2:女 ")
    private Integer sex;
}
