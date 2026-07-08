package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统参数表
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_config")
public class SysConfigPO {

    /** 主键 */
    private Long id;

    /** 参数标示符 */
    private String nid;

    /** 参数名称 */
    private String title;

    /** 参数值 */
    private String content;

    /** 备注信息 */
    private String remark;

    /** 锁定状态(禁止编辑) 0:未锁定,1:锁定 */
    private Boolean locked;

    /** 添加时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
