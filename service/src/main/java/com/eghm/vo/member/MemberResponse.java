package com.eghm.vo.member;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.Gender;
import com.eghm.enums.MemberState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberResponse extends ExcelStyle {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    @ExcelProperty(value = "昵称", index = 0)
    private String nickName;

    @Schema(description = "手机号码")
    @ExcelProperty(value = "手机号码", index = 1)
    private String mobile;

    @Schema(description = "电子邮箱")
    @ExcelProperty(value = "电子邮箱", index = 2)
    private String email;

    @Schema(description = "状态 0:注销 1正常")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private MemberState state;

    @Schema(description = "总积分数")
    @ExcelProperty(value = "积分", index = 4)
    private Integer score;

    @Schema(description = "邀请码")
    @ExcelProperty(value = "邀请码", index = 5)
    private String inviteCode;

    @Schema(description = "性别 0:未知 1:男 2:女 ")
    @ExcelProperty(value = "性别", index = 6, converter = EnumExcelConverter.class)
    private Gender sex;

    @Schema(description = "真实姓名")
    @ExcelProperty(value = "真实姓名", index = 7)
    private String realName;

    @Schema(description = "生日yyyyMMdd")
    @ExcelProperty(value = "生日", index = 8)
    private String birthday;

    @Schema(description = "注册渠道 PC,ANDROID,IOS,H5,OTHER")
    @ExcelProperty(value = "注册渠道", index = 9)
    private String channel;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "注册时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
