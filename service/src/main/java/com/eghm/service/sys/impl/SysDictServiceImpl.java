package com.eghm.service.sys.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.cache.CacheProxyService;
import com.eghm.dto.sys.dict.*;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysDictItemMapper;
import com.eghm.mapper.SysDictMapper;
import com.eghm.model.SysDict;
import com.eghm.model.SysDictItem;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.dict.DictResponse;
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
@Service("sysDictService")
@AllArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictMapper sysDictMapper;

    private final CacheProxyService cacheProxyService;

    private final SysDictItemMapper sysDictItemMapper;

    @Override
    public List<DictResponse> getList(DictQueryRequest request) {
        return sysDictMapper.getList(request);
    }

    @Override
    public List<SysDictItem> getDictByNid(String nid) {
        return cacheProxyService.getDictByNid(nid);
    }

    @Override
    public void create(DictAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        this.redoNid(request.getNid());
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void update(DictEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.updateById(sysDict);
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
        this.redoItemShow(request.getShowValue(), request.getNid(), null);
        this.redoItemHidden(request.getHiddenValue(), request.getNid(), null);
        SysDictItem dictItem = DataUtil.copy(request, SysDictItem.class);
        sysDictItemMapper.insert(dictItem);
    }

    @Override
    public void itemUpdate(DictItemEditRequest request) {
        this.redoItemShow(request.getShowValue(), request.getNid(), request.getId());
        this.redoItemHidden(request.getHiddenValue(), request.getNid(), request.getId());
        SysDictItem dictItem = DataUtil.copy(request, SysDictItem.class);
        sysDictItemMapper.updateById(dictItem);
    }

    @Override
    public void itemDelete(Long id) {
        sysDictItemMapper.deleteById(id);
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

    /**
     * 校验数据字典是否重复
     *
     * @param title 名称
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getTitle, title);
        wrapper.ne(id != null, SysDict::getId, id);
        if (sysDictMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DICT_REPEAT_ERROR);
        }
    }

    /**
     * 校验数据字典编码是否重复
     *
     * @param nid nid
     */
    private void redoNid(String nid) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getNid, nid);
        if (sysDictMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DICT_NID_REPEAT_ERROR);
        }
    }

    /**
     * 校验数据字典子项显示值是否重复
     *
     * @param showValue 显示值
     * @param nid       字典编码
     * @param id        id 编辑时不能为空
     */
    private void redoItemShow(String showValue, String nid, Long id) {
        LambdaQueryWrapper<SysDictItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictItem::getShowValue, showValue);
        wrapper.eq(SysDictItem::getNid, nid);
        wrapper.ne(id != null, SysDictItem::getId, id);
        if (sysDictItemMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DICT_SHOW_REPEAT_ERROR);
        }
    }

    /**
     * 校验数据字典子项value是否重复
     *
     * @param hiddenValue 隐藏值
     * @param nid         字典编码
     * @param id          id 编辑时不能为空
     */
    private void redoItemHidden(Integer hiddenValue, String nid, Long id) {
        LambdaQueryWrapper<SysDictItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictItem::getHiddenValue, hiddenValue);
        wrapper.eq(SysDictItem::getNid, nid);
        wrapper.ne(id != null, SysDictItem::getId, id);
        if (sysDictItemMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DICT_HIDDEN_REPEAT_ERROR);
        }
    }
}
