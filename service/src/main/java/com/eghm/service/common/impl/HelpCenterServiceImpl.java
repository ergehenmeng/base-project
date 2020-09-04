package com.eghm.service.common.impl;

import com.eghm.dao.mapper.HelpCenterMapper;
import com.eghm.dao.model.HelpCenter;
import com.eghm.model.dto.help.HelpAddRequest;
import com.eghm.model.dto.help.HelpEditRequest;
import com.eghm.model.dto.help.HelpQueryRequest;
import com.eghm.service.common.HelpCenterService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:20
 */
@Service("helpCenterService")
@Transactional(rollbackFor = RuntimeException.class)
public class HelpCenterServiceImpl implements HelpCenterService {

    private HelpCenterMapper helpCenterMapper;

    @Autowired
    public void setHelpCenterMapper(HelpCenterMapper helpCenterMapper) {
        this.helpCenterMapper = helpCenterMapper;
    }

    @Override
    public void addHelpCenter(HelpAddRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterMapper.insertSelective(helpCenter);
    }

    @Override
    public void updateHelpCenter(HelpEditRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterMapper.updateByPrimaryKeySelective(helpCenter);
    }

    @Override
    public void deleteHelpCenter(HelpEditRequest request) {
        HelpCenter helpCenter = new HelpCenter();
        helpCenter.setId(request.getId());
        helpCenter.setDeleted(true);
        helpCenterMapper.updateByPrimaryKeySelective(helpCenter);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<HelpCenter> getByPage(HelpQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<HelpCenter> list = helpCenterMapper.getList(request);
        return new PageInfo<>(list);
    }
}
