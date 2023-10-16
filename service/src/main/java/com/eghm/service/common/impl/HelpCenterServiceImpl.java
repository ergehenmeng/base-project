package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.help.HelpAddRequest;
import com.eghm.dto.help.HelpEditRequest;
import com.eghm.dto.help.HelpQueryDTO;
import com.eghm.dto.help.HelpQueryRequest;
import com.eghm.mapper.HelpCenterMapper;
import com.eghm.model.HelpCenter;
import com.eghm.service.common.HelpCenterService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.help.HelpCenterVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/20 20:20
 */
@Service("helpCenterService")
@AllArgsConstructor
public class HelpCenterServiceImpl implements HelpCenterService {

    private final HelpCenterMapper helpCenterMapper;

    @Override
    public Page<HelpCenter> getByPage(HelpQueryRequest request) {
        LambdaQueryWrapper<HelpCenter> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), HelpCenter::getAsk, request.getQueryName());
        wrapper.eq(request.getState() != null, HelpCenter::getState, request.getState());
        wrapper.eq(request.getClassify() != null, HelpCenter::getClassify, request.getClassify());
        return helpCenterMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(HelpAddRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterMapper.insert(helpCenter);
    }

    @Override
    public void update(HelpEditRequest request) {
        HelpCenter helpCenter = DataUtil.copy(request, HelpCenter.class);
        helpCenterMapper.updateById(helpCenter);
    }

    @Override
    public void delete(Long id) {
        helpCenterMapper.deleteById(id);
    }

    @Override
    public List<HelpCenterVO> list(HelpQueryDTO dto) {
        List<HelpCenter> list = helpCenterMapper.getListSorted(dto.getClassify(), dto.getQueryName());
        return DataUtil.copy(list, HelpCenterVO.class);
    }
}
