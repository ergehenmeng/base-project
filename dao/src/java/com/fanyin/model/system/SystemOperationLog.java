package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台系统操作日志
 * @author 二哥很猛
 */
@Data
public class SystemOperationLog implements Serializable {
    /**
     * 主键<br>
     * 表 : system_operation_log<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 请求地址<br>
     * 表 : system_operation_log<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 操作人<br>
     * 表 : system_operation_log<br>
     * 对应字段 : operator_id<br>
     */
    private Integer operatorId;

    /**
     * 请求参数<br>
     * 表 : system_operation_log<br>
     * 对应字段 : request<br>
     */
    private String request;

    /**
     * 添加时间<br>
     * 表 : system_operation_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 访问ip<br>
     * 表 : system_operation_log<br>
     * 对应字段 : ip<br>
     */
    private String ip;

    /**
     * 业务耗时<br>
     * 表 : system_operation_log<br>
     * 对应字段 : business_time<br>
     */
    private Long businessTime;

    /**
     * 响应参数<br>
     * 表 : system_operation_log<br>
     * 对应字段 : response<br>
     */
    private String response;

    /**
     * 操作分类<br>
     * 表 : system_operation_log<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    private static final long serialVersionUID = 1L;

    /**
     * 操作人姓名 关联查询
     */
    private String operatorName;
}