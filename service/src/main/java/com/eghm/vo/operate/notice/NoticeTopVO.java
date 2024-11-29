package com.eghm.vo.operate.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
public class NoticeTopVO {

    @ApiModelProperty("公告id")
    private Long id;

    @ApiModelProperty("公告名称")
    private String title;
}
