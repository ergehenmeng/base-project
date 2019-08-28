package com.fanyin.model.ext;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/8 11:47
 */
@Getter
@Setter
public class Operation implements Serializable {

    private static final long serialVersionUID = 7114257557281410201L;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人id
     */
    private Integer operatorId;

}
