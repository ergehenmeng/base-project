package com.eghm.vo.business.comment;

import com.eghm.enums.ObjectType;
import com.eghm.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/17
 */

@Data
public class CommentReportResponse {

    @Schema(description = "举报用户昵称")
    private Long nickName;

    @Schema(description = "评价内容")
    private String commentContent;

    @Schema(description = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "举报内容")
    private String content;

    @Schema(description = "举报原因 (1:淫秽色情 2:营销广告 3:违法信息 4:网络暴力 5:虚假谣言 6:养老诈骗 7:其他)")
    private ReportType reportType;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
