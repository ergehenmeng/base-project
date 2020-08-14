package com.eghm.model.dto.sys.log;

import com.eghm.model.ext.PagingQuery;
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
public class OperationQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = 7717698795068820383L;


    /**
     * 操作开始时间
     */
    private Date startTime;

    /**
     * 操作结束时间
     */
    private Date endTime;

}
