package com.fanyin.service.common.impl;

import com.fanyin.model.dto.common.help.HelpAddRequest;
import com.fanyin.model.dto.common.help.HelpEditRequest;
import com.fanyin.model.dto.common.help.HelpQueryRequest;
import com.fanyin.dao.mapper.common.HelpInstructionMapper;
import com.fanyin.dao.model.common.HelpInstruction;
import com.fanyin.service.common.HelpInstructionService;
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
@Service("helpInstructionService")
@Transactional(rollbackFor = RuntimeException.class)
public class HelpInstructionServiceImpl implements HelpInstructionService {

    @Autowired
    private HelpInstructionMapper helpInstructionMapper;


    @Override
    public void addHelpInstruction(HelpAddRequest request) {
        HelpInstruction instruction = DataUtil.copy(request, HelpInstruction.class);
        //默认正常
        instruction.setDeleted(false);
        helpInstructionMapper.insertSelective(instruction);
    }

    @Override
    public void updateHelpInstruction(HelpEditRequest request) {
        HelpInstruction instruction = DataUtil.copy(request, HelpInstruction.class);
        helpInstructionMapper.updateByPrimaryKeySelective(instruction);
    }

    @Override
    public void deleteHelpInstruction(HelpEditRequest request) {
        HelpInstruction instruction = new HelpInstruction();
        instruction.setId(request.getId());
        instruction.setDeleted(true);
        helpInstructionMapper.updateByPrimaryKeySelective(instruction);
    }

    @Override
    public PageInfo<HelpInstruction> getByPage(HelpQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<HelpInstruction> list = helpInstructionMapper.getList(request);
        return new PageInfo<>(list);
    }
}
