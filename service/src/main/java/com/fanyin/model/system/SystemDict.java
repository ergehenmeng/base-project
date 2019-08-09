package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class SystemDict implements Serializable {

    private static final long serialVersionUID = 6649578566732081373L;
    /**
     * <br>
     * 表 : system_dict<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 字典中文名称<br>
     * 表 : system_dict<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 数据字典nid(英文名称)<br>
     * 表 : system_dict<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 数据字典隐藏值<br>
     * 表 : system_dict<br>
     * 对应字段 : hidden_value<br>
     */
    private Byte hiddenValue;

    /**
     * 显示值<br>
     * 表 : system_dict<br>
     * 对应字段 : show_value<br>
     */
    private String showValue;

    /**
     * 删除状态 0:正常,1:已删除<br>
     * 表 : system_dict<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 锁定状态(禁止编辑):0:未锁定 1:锁定<br>
     * 表 : system_dict<br>
     * 对应字段 : locked<br>
     */
    private Boolean locked;

    /**
     * 添加时间<br>
     * 表 : system_dict<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 修改时间<br>
     * 表 : system_dict<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注信息<br>
     * 表 : system_dict<br>
     * 对应字段 : remark<br>
     */
    private String remark;

}