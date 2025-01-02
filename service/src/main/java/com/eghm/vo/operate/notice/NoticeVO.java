package com.eghm.vo.operate.notice;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeVO {

    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "公告名称")
    private String title;

    @Schema(description = "公告类型名称")
    private String noticeType;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "状态 false:未上架 true:已上架")
    private Boolean state;

    @Schema(description = "是否已删除 false:未删除 true:已删除")
    private Boolean deleted;

}
