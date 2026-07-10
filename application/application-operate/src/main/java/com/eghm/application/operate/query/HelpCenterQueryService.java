package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.help.HelpQueryDTO;
import com.eghm.application.shared.dto.operate.help.HelpQueryRequest;
import com.eghm.application.shared.vo.operate.help.HelpCenterVO;
import com.eghm.application.shared.vo.operate.help.HelpResponse;

import java.util.List;

/**
 * 帮助中心查询服务
 *
 * @author 二哥很猛
 */
public interface HelpCenterQueryService {

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param request 查询条件
     * @return list
     */
    Page<HelpResponse> getByPage(Page<HelpResponse> page, HelpQueryRequest request);

    /**
     * 按分类查询帮助信息
     *
     * @param dto 查询条件
     * @return 不分页
     */
    List<HelpCenterVO> list(HelpQueryDTO dto);
}
