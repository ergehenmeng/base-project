package com.eghm.service.sys.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.SysDictMapper;
import com.eghm.dao.model.SysDict;
import com.eghm.model.dto.dict.DictAddRequest;
import com.eghm.model.dto.dict.DictEditRequest;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysDictServiceImpl implements SysDictService {

    private SysDictMapper sysDictMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setSysDictMapper(SysDictMapper sysDictMapper) {
        this.sysDictMapper = sysDictMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    public List<SysDict> getDictByNid(String nid) {
        return sysDictMapper.getDictByNid(nid);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public PageInfo<SysDict> getByPage(DictQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysDict> list = sysDictMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addDict(DictAddRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDict.setDeleted(false);
        sysDict.setId(keyGenerator.generateKey());
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
