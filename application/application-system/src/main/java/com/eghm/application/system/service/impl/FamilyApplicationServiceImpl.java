package com.eghm.application.system.service.impl;

import com.eghm.application.shared.common.CommonService;
import com.eghm.constants.CommonConstant;
import com.eghm.application.shared.dto.sys.family.FamilyAddRequest;
import com.eghm.application.shared.dto.sys.family.FamilyEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.query.FamilyQueryService;
import com.eghm.application.system.service.FamilyApplicationService;
import com.eghm.domain.system.model.Family;
import com.eghm.domain.system.repository.FamilyRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.TreeUtil;
import com.eghm.application.shared.vo.sys.family.FamilyResponse;
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
public class FamilyApplicationServiceImpl implements FamilyApplicationService {

    private final FamilyRepository familyRepository;

    private final FamilyQueryService familyQueryGateway;

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
        List<FamilyResponse> mapperList = familyQueryGateway.getList();
        List<FamilyResponse> responseList = TreeUtil.tree(mapperList, ancestryId, FamilyResponse::getId, FamilyResponse::getPid, FamilyResponse::setChildren);
        root.setChildren(responseList);
        return root;
    }

    @Override
    public String create(FamilyAddRequest request) {
        if (familyRepository.existsByNameAndId(request.getName(), request.getPid(), false)) {
            log.warn("商户分组名称重复 [{}] [{}]", request.getName(), request.getPid());
            throw new BusinessException(ErrorCode.FAMILY_REDO_ERROR);
        }
        Family family = DataUtil.copy(request, Family.class);
        String maxId = familyRepository.findMaxId(request.getPid());
        String nextId = commonService.generateNextId(maxId, request.getPid(), CommonConstant.STEP_10, ErrorCode.FAMILY_MAX_ERROR);
        family.assignId(nextId);
        familyRepository.save(family);
        return nextId;
    }

    @Override
    public void update(FamilyEditRequest request) {
        if (familyRepository.existsByNameAndId(request.getName(), request.getId(), true)) {
            log.warn("商户分组名称重复 [{}] [{}]", request.getName(), request.getId());
            throw new BusinessException(ErrorCode.FAMILY_REDO_ERROR);
        }
        familyRepository.update(DataUtil.copy(request, Family.class));
    }

    @Override
    public void delete(String id) {
        Family family = new Family();
        family.assertDeletable(familyRepository.hasChildren(id));
        familyRepository.deleteById(id);
    }
}
