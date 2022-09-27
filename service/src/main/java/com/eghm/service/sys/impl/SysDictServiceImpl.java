package com.eghm.service.sys.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.SysDictMapper;
import com.eghm.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @date 2018/1/12 14:31
 */
@Service("sysDictService")
@AllArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictMapper sysDictMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public List<SysDict> getDictByNid(String nid) {
        return cacheProxyService.getDictByNid(nid);
    }

    @Override
    public Page<SysDict> getByPage(DictQueryRequest request) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getLocked() != null, SysDict::getLocked, request.getLocked());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysDict::getTitle, request.getQueryName()).or()
                        .like(SysDict::getNid, request.getQueryName()));
        wrapper.last("order by id desc ");
        return sysDictMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(DictAddRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void update(DictEditRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<SysDict> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysDict::getId, id);
        wrapper.eq(SysDict::getLocked, false);
        int i = sysDictMapper.delete(wrapper);
        if (i != 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    @Override
    public SysDict getById(Long id) {
        return sysDictMapper.selectById(id);
    }

    @Override
    public String getDictValue(String nid, Byte hiddenValue) {
        List<SysDict> dictList = cacheProxyService.getDictByNid(nid);
        for (SysDict dict : dictList) {
            if (dict.getHiddenValue().equals(hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }
}
