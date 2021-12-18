package com.eghm.dao.model;

import lombok.*;

/**
 * 系统异常记录
 * @author 二哥很猛
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionLog extends BaseEntity {

    /**
     * 访问链接<br>
     * 表 : exception_log<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 请求参数(json)<br>
     * 表 : exception_log<br>
     * 对应字段 : request_param<br>
     */
    private String requestParam;

    /**
     * 错误日志<br>
     * 表 : exception_log<br>
     * 对应字段 : error_msg<br>
     */
    private String errorMsg;

}