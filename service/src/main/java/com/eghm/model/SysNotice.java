package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统公告
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_notice")
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity {

    /**
     * 未发布
     */
    public static final int STATE_0 = 0;

    /**
     * 已发布
     */
    public static final int STATE_1 = 1;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告类型(数据字典表sys_notice_type)")
    private Integer noticeType;

    @ApiModelProperty("公告内容(富文本)")
    private String content;

    @ApiModelProperty("是否发布 0:未发布 1:已发布")
    private Integer state;

}