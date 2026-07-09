package com.eghm.interfaces.core.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.domain.shared.enums.Gender;
import com.eghm.domain.shared.enums.MemberState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 会员导出模型
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberExportResponse extends ExcelStyle {

    @ExcelProperty(value = "昵称", index = 0)
    private String nickName;

    @ExcelProperty(value = "手机号码", index = 1)
    private String mobile;

    @ExcelProperty(value = "电子邮箱", index = 2)
    private String email;

    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private MemberState state;

    @ExcelProperty(value = "积分", index = 4)
    private Integer score;

    @ExcelProperty(value = "邀请码", index = 5)
    private String inviteCode;

    @ExcelProperty(value = "性别", index = 6, converter = EnumExcelConverter.class)
    private Gender sex;

    @ExcelProperty(value = "真实姓名", index = 7)
    private String realName;

    @ExcelProperty(value = "生日", index = 8)
    private String birthday;

    @ExcelProperty(value = "注册渠道", index = 9)
    private String channel;

    @ExcelProperty(value = "注册时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
