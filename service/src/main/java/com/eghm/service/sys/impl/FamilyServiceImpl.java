package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.CommonService;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.sys.family.FamilyAddRequest;
import com.eghm.dto.sys.family.FamilyEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.FamilyMapper;
import com.eghm.model.Family;
import com.eghm.service.sys.FamilyService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.family.FamilyResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/12/16
 */
@Slf4j
@Service
@AllArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyMapper familyMapper;

    private final CommonService commonService;

    private static final String ROOT = "0";

    @Override
    public FamilyResponse getList() {
        FamilyResponse root = new FamilyResponse();
        String ancestryId = String.valueOf(CommonConstant.STEP_10);
        root.setId(ancestryId);
        root.setPid(ROOT);
        root.setName("大王庄家谱");
        root.setState(false);
        List<FamilyResponse> mapperList = familyMapper.getList();
        List<FamilyResponse> responseList = this.treeBin(ancestryId, mapperList);
        root.setChildren(responseList);
        return root;
    }

    @Override
    public String create(FamilyAddRequest request) {
        this.redoName(request.getName(), request.getPid(), null);
        Family family = DataUtil.copy(request, Family.class);
        String maxId = familyMapper.getMaxId(request.getPid());
        String nextId = commonService.generateNextId(maxId, request.getPid(), CommonConstant.STEP_10, ErrorCode.FAMILY_MAX_ERROR);
        family.setId(nextId);
        familyMapper.insert(family);
        return nextId;
    }

    @Override
    public void update(FamilyEditRequest request) {
        this.redoName(request.getName(), request.getPid(), request.getId());
        DataUtil.copy(request, Family.class, familyMapper::updateById);
    }

    @Override
    public void delete(String id) {
        LambdaQueryWrapper<Family> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Family::getPid, id);
        if (familyMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.FAMILY_NEXT_ERROR);
        }
        familyMapper.deleteById(id);
    }

    /**
     * 重置名称
     *
     * @param name 名称
     * @param id id
     */
    private void redoName(String name, String pid, String id) {
        LambdaQueryWrapper<Family> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(id != null, Family::getId, id);
        wrapper.eq(Family::getPid, pid);
        wrapper.eq(Family::getName, name);
        Long count = familyMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.FAMILY_REDO_ERROR);
        }
    }

    /**
     * 设置子节点
     *
     * @param pid    父节点
     * @param voList 全部列表
     * @return list
     */
    private List<FamilyResponse> treeBin(String pid, List<FamilyResponse> voList) {
        List<FamilyResponse> collectList = voList.stream().filter(parent -> pid.equals(parent.getPid())).toList();
        collectList.forEach(parent -> parent.setChildren(this.treeBin(parent.getId(), voList)));
        return collectList;
    }
}
