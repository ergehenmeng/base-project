package com.eghm.service.business.handler.state;

import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.state.machine.ActionHandler;

/**
 * 退款审核拒绝
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface RefundRefuseHandler extends ActionHandler<RefundAuditContext> {

}
