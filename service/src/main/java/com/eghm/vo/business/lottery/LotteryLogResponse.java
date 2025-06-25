package com.eghm.vo.business.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/4/10
 */

@Data
public class LotteryLogResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "是否中奖 false:未中奖 true:中奖")
    private Boolean winning;

    @Schema(description = "是否发放奖励 0:未发放 1:已发放")
    private Boolean issue;

    @Schema(description = "奖品名称")
    private String prizeTitle;

    @Schema(description = "中奖时间时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
