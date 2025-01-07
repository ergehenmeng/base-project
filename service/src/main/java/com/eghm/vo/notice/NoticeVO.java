package com.eghm.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
public class NoticeVO {

    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "公告名称")
    private String title;
}
