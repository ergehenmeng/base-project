package com.eghm.model.dto.system.roster;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/9/9 13:53
 */
@Data
public class BlackRosterAddRequest implements Serializable {

    private static final long serialVersionUID = 8774607401819334344L;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 黑名单截止时间
     */
    private Date endTime;
}
