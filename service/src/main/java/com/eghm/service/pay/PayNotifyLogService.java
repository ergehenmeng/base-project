package com.eghm.service.pay;

import com.eghm.dao.model.PayNotifyLog;

/**
 * @author 二哥很猛
 * @date 2022/7/26
 */
public interface PayNotifyLogService {

    /**
     * 新增回调记录信息
     * @param log 回调记录
     */
    void insert(PayNotifyLog log);

}
