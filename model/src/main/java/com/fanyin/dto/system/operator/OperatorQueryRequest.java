package com.fanyin.dto.system.operator;

import com.fanyin.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperatorQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = 6710255160163962722L;

    /**
     * 状态 0:锁定 1:正常
     */
    private Byte state;


}
