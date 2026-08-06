package com.eghm.platform.config.service.impl;


import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.DictOptionProvider;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.platform.config.dto.DictAddRequest;
import com.eghm.platform.config.dto.DictEditRequest;
import com.eghm.platform.config.dto.DictItemAddRequest;
import com.eghm.platform.config.dto.DictItemEditRequest;
import com.eghm.platform.config.dto.DictQueryRequest;
import com.eghm.platform.config.entity.SysDict;
import com.eghm.platform.config.entity.SysDictItem;
import com.eghm.platform.config.mapper.SysDictItemMapper;
import com.eghm.platform.config.mapper.SysDictMapper;
import com.eghm.platform.config.service.ConfigCacheService;
import com.eghm.platform.config.service.SysDictService;
import com.eghm.platform.config.vo.DictResponse;
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
@Service
@AllArgsConstructor
public class SysDictServiceImpl implements SysDictService, DictOptionProvider {

    private final SysDictMapper sysDictMapper;

    private final SysDictItemMapper sysDictItemMapper;
    
    private final ConfigCacheService configCacheService;

    @Override
    public List<DictResponse> getList(DictQueryRequest request) {
        return sysDictMapper.getList(request);
    }

    @Override
    public List<SysDictItem> getDictByNid(String nid) {
        return configCacheService.getDictByNid(nid);
    }

    @Override
    public void create(DictAddRequest request) {
        ValidationUtil.redoCheck(sysDictMapper, SysDict::getTitle, request.getTitle(), null, null, ErrorCode.DICT_REPEAT_ERROR, "数据字典名称重复 [{}] [{}]");
        ValidationUtil.redoCheck(sysDictMapper, SysDict::getNid, request.getNid(), null, null, ErrorCode.DICT_NID_REPEAT_ERROR, "数据字典编号重复 [{}] [{}]");
        DataUtil.copy(request, SysDict.class, sysDictMapper::insert);
    }

    @Override
    public void update(DictEditRequest request) {
        ValidationUtil.redoCheck(sysDictMapper, SysDict::getTitle, request.getTitle(), SysDict::getId, request.getId(), ErrorCode.DICT_REPEAT_ERROR, "数据字典名称重复 [{}] [{}]");
        DataUtil.copy(request, SysDict.class, sysDictMapper::updateById);
    }

    @Override
    public void delete(Long id) {
        SysDict sysDict = sysDictMapper.selectById(id);
        if (sysDict == null) {
            return;
        }
        if (sysDict.getDictType() == 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    @Override
    public void itemCreate(DictItemAddRequest request) {
        ValidationUtil.redoCheck(sysDictItemMapper, SysDictItem::getShowValue, request.getShowValue(), wrapper -> wrapper.eq(SysDictItem::getNid, request.getNid()), null, null, ErrorCode.DICT_SHOW_REPEAT_ERROR, "数据字典显示值重复 [{}] [{}]");
        ValidationUtil.redoCheck(sysDictItemMapper, SysDictItem::getHiddenValue, request.getHiddenValue(), wrapper -> wrapper.eq(SysDictItem::getNid, request.getNid()), null, null, ErrorCode.DICT_HIDDEN_REPEAT_ERROR, "数据字典隐藏值重复 [{}] [{}]");
        DataUtil.copy(request, SysDictItem.class, sysDictItemMapper::insert);
    }

    @Override
    public void itemUpdate(DictItemEditRequest request) {
        ValidationUtil.redoCheck(sysDictItemMapper, SysDictItem::getShowValue, request.getShowValue(), wrapper -> wrapper.eq(SysDictItem::getNid, request.getNid()), SysDictItem::getId, request.getId(), ErrorCode.DICT_SHOW_REPEAT_ERROR, "数据字典显示值重复 [{}] [{}]");
        ValidationUtil.redoCheck(sysDictItemMapper, SysDictItem::getHiddenValue, request.getHiddenValue(), wrapper -> wrapper.eq(SysDictItem::getNid, request.getNid()), SysDictItem::getId, request.getId(), ErrorCode.DICT_HIDDEN_REPEAT_ERROR, "数据字典隐藏值重复 [{}] [{}]");
        DataUtil.copy(request, SysDictItem.class, sysDictItemMapper::updateById);
    }

    @Override
    public void itemDelete(Long id) {
        sysDictItemMapper.deleteById(id);
    }

    @Override
    public String getDictValue(String nid, Integer hiddenValue) {
        List<SysDictItem> dictList = configCacheService.getDictByNid(nid);
        for (SysDictItem dict : dictList) {
            if (Objects.equals(dict.getHiddenValue(), hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }
    
    @Override
    public String[] getOptions(String key) {
        return this.getDictByNid(key).stream().map(SysDictItem::getShowValue).toArray(String[]::new);
    }
}
