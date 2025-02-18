package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 后台系统操作日志
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysOperationLog extends BaseEntity {

    /**
     * 请求地址<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 操作人<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : operator_id<br>
     */
    private Long operatorId;

    /**
     * 请求参数<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : request<br>
     */
    private String request;

    /**
     * 添加时间<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 访问ip<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : ip<br>
     */
    private Long ip;

    /**
     * 业务耗时<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : business_time<br>
     */
    private Long businessTime;

    /**
     * 响应参数<br>
     * 表 : sys_operation_log<br>
     * 对应字段 : response<br>
     */
    private String response;


    private static final long serialVersionUID = 1L;

    /**
     * 操作人姓名 关联查询
     */
    private String operatorName;
}