package com.eghm.service.common;

import com.eghm.dao.model.business.HelpCenter;
import com.eghm.model.dto.business.help.HelpAddRequest;
import com.eghm.model.dto.business.help.HelpEditRequest;
import com.eghm.model.dto.business.help.HelpQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 帮助说明
 * @date 2018/11/20 20:20
 */
public interface HelpCenterService {

    /**
     * 添加帮助说明
     * @param request 前台参数
     */
    void addHelpCenter(HelpAddRequest request);

    /**
     * 更新帮助说明
     * @param request 前台参数
     */
    void updateHelpCenter(HelpEditRequest request);

    /**
     * 删除帮助说明
     * @param request 前台参数 id
     */
    void deleteHelpCenter(HelpEditRequest request);

    /**
     * 分页获取帮助说明
     * @param request 前台参数
     * @return 分页列表
     */
    PageInfo<HelpCenter> getByPage(HelpQueryRequest request);
}

