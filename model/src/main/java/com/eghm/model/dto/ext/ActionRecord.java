package com.eghm.model.dto.ext;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 包含操作人或操作姓名均可以继承此类
 * @author 二哥很猛
 * @date 2019/8/8 11:47
 */
@Getter
@Setter
public class ActionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人id
     */
    private Integer operatorId;

}
