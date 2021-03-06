package com.eghm.service.common;

import com.eghm.dao.model.HelpCenter;
import com.eghm.model.dto.help.HelpAddRequest;
import com.eghm.model.dto.help.HelpEditRequest;
import com.eghm.model.dto.help.HelpQueryDTO;
import com.eghm.model.dto.help.HelpQueryRequest;
import com.eghm.model.vo.help.HelpCenterVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

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

    /**
     * 按分类查询帮助信息
     * @param dto 查询条件
     * @return 不分页
     */
    List<HelpCenterVO> list(HelpQueryDTO dto);
}

