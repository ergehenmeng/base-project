package com.eghm.business.operation.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.business.operation.support.dto.HelpAddRequest;
import com.eghm.business.operation.support.dto.HelpEditRequest;
import com.eghm.business.operation.support.dto.HelpQueryDTO;
import com.eghm.business.operation.support.dto.HelpQueryRequest;
import com.eghm.business.operation.support.mapper.HelpCenterMapper;
import com.eghm.business.operation.support.entity.HelpCenter;
import com.eghm.business.operation.support.service.HelpCenterService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.business.operation.support.vo.HelpCenterVO;
import com.eghm.business.operation.support.vo.HelpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:20
 */
@AllArgsConstructor
@Service("helpCenterService")
public class HelpCenterServiceImpl implements HelpCenterService {

    private final HelpCenterMapper helpCenterMapper;

    @Override
    public Page<HelpResponse> getByPage(HelpQueryRequest request) {
        return helpCenterMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(HelpAddRequest request) {
        DataUtil.copy(request, HelpCenter.class, helpCenterMapper::insert);
    }

    @Override
    public void update(HelpEditRequest request) {
        DataUtil.copy(request, HelpCenter.class, helpCenterMapper::updateById);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<HelpCenter> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(HelpCenter::getId, id);
        wrapper.set(HelpCenter::getSort, sortBy);
        helpCenterMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        helpCenterMapper.deleteById(id);
    }

    @Override
    public HelpCenter selectById(Long id) {
        return helpCenterMapper.selectById(id);
    }

    @Override
    public List<HelpCenterVO> list(HelpQueryDTO dto) {
        List<HelpCenter> list = helpCenterMapper.getList(dto.getHelpType(), dto.getQueryName());
        return DataUtil.copy(list, HelpCenterVO.class);
    }
}
