package com.eghm.dto.member.tag;

import com.eghm.enums.NoticeType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class SendNotifyRequest {

    @ApiModelProperty("标题名称")
    @NotBlank(message = "标题名称不能为空")
    @Size(min = 2, max = 20, message = "标题名称长度2~20位")
    @WordChecker(message = "标签名称存在敏感词")
    private String title;

    @ApiModelProperty("站内信内容")
    @NotBlank(message = "站内信内容不能为空")
    @Size(min = 10, max = 200, message = "站内信内容长度10~200位")
    @WordChecker(message = "站内信内容存在敏感词")
    private String content;

    @ApiModelProperty("通知类型")
    @NotBlank(message = "通知类型不能为空")
    private NoticeType noticeType;

    @ApiModelProperty("会员id(二选一优先级最高)")
    private List<Long> memberIds;

    @ApiModelProperty("标签id(二选一优先级次之)")
    private Long tagId;

}
