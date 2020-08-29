package com.eghm.service.sys.impl;


import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SysDictMapper;
import com.eghm.dao.model.system.SysDict;
import com.eghm.model.dto.sys.dict.DictAddRequest;
import com.eghm.model.dto.sys.dict.DictEditRequest;
import com.eghm.model.dto.sys.dict.DictQueryRequest;
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
@Transactional(rollbackFor = RuntimeException.class)
public class SysDictServiceImpl implements SysDictService {

    private SysDictMapper sysDictMapper;

    @Autowired
    public void setSysDictMapper(SysDictMapper sysDictMapper) {
        this.sysDictMapper = sysDictMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_DICT, key = "#p0", unless = "#result.size() == 0")
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
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
    public void addDict(DictAddRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDict.setDeleted(false);
        sysDictMapper.insertSelective(sysDict);
    }

    @Override
    public void updateDict(DictEditRequest request) {
        SysDict sysDict = DataUtil.copy(request, SysDict.class);
        sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }

    @Override
    public void deleteDict(Integer id) {
        SysDict dict = new SysDict();
        dict.setDeleted(true);
        dict.setId(id);
        int i = sysDictMapper.updateByIdSelective(dict);
        if (i != 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public SysDict getById(Integer id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

}
