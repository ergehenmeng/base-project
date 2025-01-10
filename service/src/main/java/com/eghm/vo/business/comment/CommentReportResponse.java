package com.eghm.vo.business.comment;

import com.eghm.enums.ref.ObjectType;
import com.eghm.enums.ref.ReportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/17
 */

@Data
public class CommentReportResponse {

    @ApiModelProperty(value = "举报用户昵称")
    private Long nickName;

    @ApiModelProperty(value = "评价内容")
    private String commentContent;

    @ApiModelProperty(value = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @ApiModelProperty(value = "举报内容")
    private String content;

    @ApiModelProperty(value = "举报原因 (1:淫秽色情 2:营销广告 3:违法信息 4:网络暴力 5:虚假谣言 6:养老诈骗 7:其他)")
    private ReportType reportType;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
