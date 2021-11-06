package com.eghm.service.common.impl;

import com.eghm.dao.mapper.HelpCenterMapper;
import com.eghm.dao.model.HelpCenter;
import com.eghm.model.dto.help.HelpAddRequest;
import com.eghm.model.dto.help.HelpEditRequest;
import com.eghm.model.dto.help.HelpQueryDTO;
import com.eghm.model.dto.help.HelpQueryRequest;
import com.eghm.model.vo.help.HelpCenterVO;
import com.eghm.service.common.HelpCenterService;
import com.eghm.service.common.KeyGenerator;
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
public class HelpCenterServiceImpl implements HelpCenterService {

    private HelpCenterMapper helpCenterMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setHelpCenterMapper(HelpCenterMapper helpCenterMapper) {
        this.helpCenterMapper = helpCenterMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addHelpCenter(HelpAddRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenter.setId(keyGenerator.generateKey());
        helpCenterMapper.insert(helpCenter);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateHelpCenter(HelpEditRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterMapper.updateById(helpCenter);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteHelpCenter(HelpEditRequest request) {
        HelpCenter helpCenter = new HelpCenter();
        helpCenter.setId(request.getId());
        helpCenter.setDeleted(true);
        helpCenterMapper.updateById(helpCenter);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<HelpCenter> getByPage(HelpQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<HelpCenter> list = helpCenterMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<HelpCenterVO> list(HelpQueryDTO dto) {
        List<HelpCenter> list = helpCenterMapper.getListSorted(dto.getClassify(), dto.getQueryName());
        return DataUtil.convert(list, helpCenter -> DataUtil.copy(helpCenter, HelpCenterVO.class));
    }
}
