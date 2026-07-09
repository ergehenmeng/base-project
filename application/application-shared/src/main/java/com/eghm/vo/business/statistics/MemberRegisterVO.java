package com.eghm.vo.business.statistics;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRegisterVO {

    @Schema(description = "注册日期")
    @JsonFormat(pattern = "MM-dd")
    private LocalDate createDate;

    @Schema(description = "注册月份")
    private String createMonth;

    @Schema(description = "注册人数")
    private Integer registerNum;

    public MemberRegisterVO(LocalDate createDate) {
        this.createDate = createDate;
        this.registerNum = RandomUtil.randomInt(200);
    }

    public MemberRegisterVO(String createMonth) {
        this.createMonth = createMonth;
        this.registerNum = RandomUtil.randomInt(200);
    }
}
