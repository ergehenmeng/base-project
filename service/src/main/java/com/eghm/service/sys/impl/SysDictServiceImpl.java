package com.eghm.service.sys.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.SysDictMapper;
import com.eghm.dao.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    public List<SysDict> getDictByNid(String nid) {
        return sysDictMapper.getDictByNid(nid);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public Page<SysDict> getByPage(DictQueryRequest request) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getLocked() != null, SysDict::getLocked, request.getLocked());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysDict::getTitle, request.getQueryName()).or()
                        .like(SysDict::getNid, request.getQueryName()));
        wrapper.orderByDesc(SysDict::getId);
        return sysDictMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addDict(DictAddRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDict.setDeleted(false);
        sysDictMapper.insert(sysDict);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateDict(DictEditRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.updateById(sysDict);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteDict(Long id) {
        SysDict dict = new SysDict();
        dict.setDeleted(true);
        dict.setId(id);
        int i = sysDictMapper.updateByIdSelective(dict);
        if (i != 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    @Override
    public SysDict getById(Long id) {
        return sysDictMapper.selectById(id);
    }

}
