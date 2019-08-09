package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数表
 * @author 二哥很猛
 */
@Data
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = -3006821887681558075L;

    /**
     * 主键<br>
     * 表 : system_config<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 参数标示符<br>
     * 表 : system_config<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 参数名称<br>
     * 表 : system_config<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 参数值<br>
     * 表 : system_config<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 备注信息<br>
     * 表 : system_config<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 参数类型,见system_dict表nid=config_classify<br>
     * 表 : system_config<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 锁定状态(禁止编辑) 0:未锁定,1:锁定<br>
     * 表 : system_config<br>
     * 对应字段 : locked<br>
     */
    private Boolean locked;

    /**
     * 添加时间<br>
     * 表 : system_config<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : system_config<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 系统参数有效开始时间<br>
     * 表 : system_config<br>
     * 对应字段 : start_time<br>
     */
    private Date startTime;

    /**
     * 系统参数有效结束时间<br>
     * 表 : system_config<br>
     * 对应字段 : end_time<br>
     */
    private Date endTime;

    /**
     * 系统参数备用值,在有效期之外自动切换该值<br>
     * 表 : system_config<br>
     * 对应字段 : reserve_content<br>
     */
    private String reserveContent;

    /**
     * 参数类型 数据字典名称
     */
    private String classifyName;
}