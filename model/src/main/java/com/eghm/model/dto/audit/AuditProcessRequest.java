package com.eghm.model.dto.audit;

import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.AuditType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2020/8/27
 */
@Data
@ApiModel
public class AuditProcessRequest {

    /**
     * 审核记录id
     */
    @ApiModelProperty(required = true, value = "审批记录id")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 审核类型 TODO 待确认json转换方式
     */
    @ApiModelProperty(required = true, value = "审批类型")
    @NotNull(message = "审批类型不能为空")
    private AuditType auditType;

    /**
     * 审核状态
     */
    @ApiModelProperty(required = true, value = "审批状态 0:待审批, 1:审批成功 2:审批拒绝")
    @NotNull(message = "审批状态不能为空")
    private AuditState state;

    /**
     * 审核意见
     */
    @ApiModelProperty(required = true, value = "审批意见")
    @NotNull(message = "审批意见不能为空")
    private String opinion;

}
