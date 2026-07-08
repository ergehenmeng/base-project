package com.eghm.vo.business.collect;

import com.eghm.enums.CollectType;
import com.eghm.vo.business.news.NewsVO;
import com.eghm.vo.operate.notice.NoticeVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCollectVO {

    @Schema(description = "收藏id")
    private Long collectId;

    @Schema(description = "收藏对象类型(1:资讯 2:公告)")
    private CollectType collectType;

    @Schema(description = "资讯")
    private NewsVO news;

    @Schema(description = "公告")
    private NoticeVO notice;
}
