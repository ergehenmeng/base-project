package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("black_roster")
@EqualsAndHashCode(callSuper = true)
public class BlackRosterPO extends BaseEntityPO {

    /** 访问ip */
    private Long startIp;

    /** 数字ip */
    private Long endIp;

    /** 备注信息 */
    private String remark;
}


