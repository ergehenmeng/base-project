package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统公告
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysNotice extends BaseEntity {

    /**
     * 未发布
     */
    public static final byte STATE_0 = 0;

    /**
     * 已发布
     */
    public static final byte STATE_1 = 1;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告类型(数据字典表sys_notice_type)")
    private Byte classify;

    @ApiModelProperty("公告内容(富文本)")
    private String content;

    @ApiModelProperty("是否发布 0:未发布 1:已发布")
    private Byte state;

}