package com.eghm.service.sys.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.dict.DictAddRequest;
import com.eghm.dto.dict.DictEditRequest;
import com.eghm.dto.dict.DictQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysDictItemMapper;
import com.eghm.mapper.SysDictMapper;
import com.eghm.model.SysDict;
import com.eghm.model.SysDictItem;
import com.eghm.service.business.CommonService;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.DictResponse;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @date 2018/1/12 14:31
 */
@Slf4j
@Service("sysDictService")
@AllArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictMapper sysDictMapper;

    private final CacheProxyService cacheProxyService;

    private final CommonService commonService;

    private final SysDictItemMapper sysDictItemMapper;

    @Override
    public Page<DictResponse> getByPage(DictQueryRequest request) {
        return sysDictMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<SysDictItem> getDictByNid(String nid) {
        return cacheProxyService.getDictByNid(nid);
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
    public String getDictValue(String nid, Integer hiddenValue) {
        List<SysDictItem> dictList = cacheProxyService.getDictByNid(nid);
        for (SysDictItem dict : dictList) {
            if (Objects.equals(dict.getHiddenValue(), hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }

    @Override
    public List<String> getTags(String nid, String tagIds) {
        if (StrUtil.isBlank(tagIds)) {
            log.info("标签id为空,不查询标签字典 [{}]", nid);
            return Lists.newArrayListWithCapacity(4);
        }
        List<SysDictItem> dictList = this.getDictByNid(nid);

        return commonService.parseTags(dictList, tagIds);
    }
}
