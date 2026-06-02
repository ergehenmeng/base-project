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
import com.eghm.utils.TreeUtil;
import com.eghm.utils.ValidationUtil;
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
        List<FamilyResponse> responseList = TreeUtil.tree(mapperList, ancestryId, FamilyResponse::getId, FamilyResponse::getPid, FamilyResponse::setChildren);
        root.setChildren(responseList);
        return root;
    }

    @Override
    public String create(FamilyAddRequest request) {
        ValidationUtil.redoCheck(familyMapper, Family::getName, request.getName(), Family::getId, request.getPid(), ErrorCode.FAMILY_REDO_ERROR, "商户分组名称重复 [{}] [{}]");
        Family family = DataUtil.copy(request, Family.class);
        String maxId = familyMapper.getMaxId(request.getPid());
        String nextId = commonService.generateNextId(maxId, request.getPid(), CommonConstant.STEP_10, ErrorCode.FAMILY_MAX_ERROR);
        family.setId(nextId);
        familyMapper.insert(family);
        return nextId;
    }

    @Override
    public void update(FamilyEditRequest request) {
        ValidationUtil.redoCheck(familyMapper, Family::getName, request.getName(), Family::getId, request.getId(), ErrorCode.FAMILY_REDO_ERROR, "商户分组名称重复 [{}] [{}]");
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

}
