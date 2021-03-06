package com.eghm.model.dto.audit;

import com.eghm.common.enums.AuditState;
import com.eghm.model.annotation.BackstageTag;
import com.eghm.model.dto.ext.PagingQuery;
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
    private Byte state;

    /**
     * 用户id
     */
    @BackstageTag
    private Long operatorId;

    /**
     * 角色列表
     */
    @BackstageTag
    private List<String> roleList;
}
