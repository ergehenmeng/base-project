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

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人id
     */
    private String operatorId;

}
