package com.eghm.application.system.service.impl;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.sys.dict.DictAddRequest;
import com.eghm.application.shared.dto.sys.dict.DictEditRequest;
import com.eghm.application.shared.dto.sys.dict.DictItemAddRequest;
import com.eghm.application.shared.dto.sys.dict.DictItemEditRequest;
import com.eghm.application.shared.dto.sys.dict.DictQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.query.SysDictQueryService;
import com.eghm.application.system.service.SysDictApplicationService;
import com.eghm.domain.system.model.SysDict;
import com.eghm.domain.system.model.SysDictItem;
import com.eghm.domain.system.repository.SysDictItemRepository;
import com.eghm.domain.system.repository.SysDictRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.sys.dict.DictResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @since 2018/1/12 14:31
 */
@Slf4j
@AllArgsConstructor
@Service("sysDictService")
public class SysDictApplicationServiceImpl implements SysDictApplicationService {

    private final SysDictRepository sysDictRepository;

    private final SysDictItemRepository sysDictItemRepository;

    private final SysDictQueryService sysDictQueryService;

    private final CacheProxyService cacheProxyService;

    @Override
    public List<DictResponse> getList(DictQueryRequest request) {
        return sysDictQueryService.getList(request);
    }

    @Override
    public List<SysDictItem> getDictByNid(String nid) {
        return cacheProxyService.getDictByNid(nid);
    }

    @Override
    public void create(DictAddRequest request) {
        if (sysDictRepository.existsTitle(request.getTitle(), null)) {
            log.warn("数据字典名称重复 [{}]", request.getTitle());
            throw new BusinessException(ErrorCode.DICT_REPEAT_ERROR);
        }
        if (sysDictRepository.existsNid(request.getNid())) {
            log.warn("数据字典编号重复 [{}]", request.getNid());
            throw new BusinessException(ErrorCode.DICT_NID_REPEAT_ERROR);
        }
        sysDictRepository.save(DataUtil.copy(request, SysDict.class));
    }

    @Override
    public void update(DictEditRequest request) {
        if (sysDictRepository.existsTitle(request.getTitle(), request.getId())) {
            log.warn("数据字典名称重复 [{}] [{}]", request.getId(), request.getTitle());
            throw new BusinessException(ErrorCode.DICT_REPEAT_ERROR);
        }
        sysDictRepository.update(DataUtil.copy(request, SysDict.class));
    }

    @Override
    public void delete(Long id) {
        SysDict sysDict = sysDictRepository.findById(id);
        if (sysDict == null) {
            return;
        }
        if (sysDict.getDictType() == 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    @Override
    public void itemCreate(DictItemAddRequest request) {
        if (sysDictItemRepository.existsShowValue(request.getNid(), request.getShowValue(), null)) {
            log.warn("数据字典显示值重复 [{}] [{}]", request.getNid(), request.getShowValue());
            throw new BusinessException(ErrorCode.DICT_SHOW_REPEAT_ERROR);
        }
        if (sysDictItemRepository.existsHiddenValue(request.getNid(), request.getHiddenValue(), null)) {
            log.warn("数据字典隐藏值重复 [{}] [{}]", request.getNid(), request.getHiddenValue());
            throw new BusinessException(ErrorCode.DICT_HIDDEN_REPEAT_ERROR);
        }
        sysDictItemRepository.save(DataUtil.copy(request, SysDictItem.class));
    }

    @Override
    public void itemUpdate(DictItemEditRequest request) {
        if (sysDictItemRepository.existsShowValue(request.getNid(), request.getShowValue(), request.getId())) {
            log.warn("数据字典显示值重复 [{}] [{}]", request.getNid(), request.getShowValue());
            throw new BusinessException(ErrorCode.DICT_SHOW_REPEAT_ERROR);
        }
        if (sysDictItemRepository.existsHiddenValue(request.getNid(), request.getHiddenValue(), request.getId())) {
            log.warn("数据字典隐藏值重复 [{}] [{}]", request.getNid(), request.getHiddenValue());
            throw new BusinessException(ErrorCode.DICT_HIDDEN_REPEAT_ERROR);
        }
        sysDictItemRepository.update(DataUtil.copy(request, SysDictItem.class));
    }

    @Override
    public void itemDelete(Long id) {
        sysDictItemRepository.deleteById(id);
    }

    @Override
    public String getDictValue(String nid, Integer hiddenValue) {
        List<SysDictItem> dictList = cacheProxyService.getDictByNid(nid);
        for (SysDictItem dict : dictList) {
            if (Objects.equals(dict.getHiddenValue(), hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }
}
