package com.fanyin.dto.system.log;

import com.fanyin.ext.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/1/16 9:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationQueryRequest extends PageQuery implements Serializable {

    private static final long serialVersionUID = 7717698795068820383L;

    /**
     * 操作日志分类
     */
    private Byte classify;

    /**
     * 操作开始时间
     */
    private Date startTime;

    /**
     * 操作结束时间
     */
    private Date endTime;

}
