package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.HelpCenterMapper;
import com.fanyin.dao.model.business.HelpCenter;
import com.fanyin.model.dto.business.help.HelpAddRequest;
import com.fanyin.model.dto.business.help.HelpEditRequest;
import com.fanyin.model.dto.business.help.HelpQueryRequest;
import com.fanyin.service.common.HelpCenterService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Autowired
    private HelpCenterMapper helpCenterMapper;

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
    public PageInfo<HelpCenter> getByPage(HelpQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<HelpCenter> list = helpCenterMapper.getList(request);
        return new PageInfo<>(list);
    }
}