package com.eghm.dto.business.lottery;

import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 殿小二
 * @date 2023/3/27
 */
@Data
public class LotteryAddRequest {
    
    @ApiModelProperty(value = "活动名称", required = true)
    @NotBlank(message = "活动名称不能为空")
    @Size(min = 2, max = 20, message = "活动名称应为2~20字符")
    private String title;
    
    @ApiModelProperty(value = "开始时间 yyyy-MM-dd HH:mm::ss", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @ApiModelProperty(value = "结束时间 yyyy-MM-dd HH:mm:ss", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    @ApiModelProperty(value = "单日抽奖次数限制", required = true)
    @NotNull(message = "单日抽奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "单日抽奖次数应为1~9999次")
    private Integer lotteryDay;
    
    @ApiModelProperty(value = "总抽奖次数限制", required = true)
    @NotNull(message = "总抽奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "总抽奖次数应为1~9999次")
    private Integer lotteryTotal;
    
    @ApiModelProperty(value = "中奖次数限制", required = true)
    @NotNull(message = "中奖次数不能为空")
    @RangeInt(min = 1, max = 9999, message = "中奖次数应为1~9999次")
    private Integer winNum;
    
    @ApiModelProperty(value = "抽奖页面封面图", required = true)
    @NotBlank(message = "请选择抽奖封面图")
    private String coverUrl;
    
    @ApiModelProperty(value = "抽奖标题", required = true)
    @NotBlank(message = "抽奖标题不能为空")
    @Length(min = 2, max = 10, message = "抽奖标题长度应为2~10字符")
    @WordChecker
    private String subTitle;
    
    @ApiModelProperty(value = "抽奖规则", required = true)
    @NotBlank(message = "抽奖规则不能为空")
    @Length(min = 10, max = 1000, message = "抽奖规则长度为10~1000字符")
    @WordChecker
    private String rule;

    @ApiModelProperty(value = "奖品列表",required = true)
    @NotEmpty(message = "奖品列表不能为空")
    @Size(min = 1, max = 8, message = "奖品列表不能大于8个")
    private List<LotteryPrizeRequest> prizeList;
    
    @ApiModelProperty(value = "中奖配置信息", required = true)
    @NotEmpty(message = "中奖配置不能为空")
    @Size(min = 8, max = 8, message = "中奖配置应为8条")
    private List<LotteryConfigRequest> configList;
    
}
