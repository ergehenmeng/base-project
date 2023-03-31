package com.eghm.model.dto.operator;

import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/26 17:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperatorQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = 6710255160163962722L;

    @ApiModelProperty("锁定状态 0: 锁定 1:正常")
    @OptionInt(value = {0, 1}, required = false)
    private Integer state;


}
