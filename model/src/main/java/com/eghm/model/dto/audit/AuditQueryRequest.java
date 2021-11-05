package com.eghm.model.dto.audit;

import com.eghm.common.enums.AuditState;
import com.eghm.model.annotation.Sign;
import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/26
 */
@Setter
@Getter
@ToString(callSuper = true)
public class AuditQueryRequest extends PagingQuery {

    /**
     * 审批状态 与 {@link AuditState}保持一致
     */
    @ApiModelProperty(value = "审批管理状态", required = true)
    @OptionByte(value = {0, 1, 2})
    private Byte state;

    /**
     * 用户id
     */
    @Sign
    @ApiModelProperty(hidden = true)
    private Long operatorId;

    /**
     * 角色列表
     */
    @Sign
    @ApiModelProperty(hidden = true)
    private List<String> roleList;
}
