package com.eghm.common.impl;

import com.eghm.cache.CacheProxyService;
import com.eghm.cache.CacheService;
import com.eghm.common.CommonService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.mapper.SysAreaMapper;
import com.eghm.vo.sys.ext.SysAreaVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Service("commonService")
@AllArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CacheService cacheService;

    private final SysAreaMapper sysAreaMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public List<SysAreaVO> getTreeAreaList() {
        List<SysAreaVO> areaList = cacheProxyService.getAreaList();
        return this.treeBin(CommonConstant.ROOT, areaList);
    }

    @Override
    public List<SysAreaVO> getTreeAreaList(List<Integer> gradeList) {
        List<SysAreaVO> areaList = sysAreaMapper.getList(gradeList);
        return this.treeBin(CommonConstant.ROOT, areaList);
    }

    @Override
    public void praise(String key, String hashKey, Consumer<Boolean> consumer) {
        boolean praise = cacheService.getHashValue(key, hashKey) == null;
        if (praise) {
            cacheService.setHashValue(key, hashKey, CacheConstant.PLACE_HOLDER);
        } else {
            cacheService.deleteHashKey(key, hashKey);
        }
        consumer.accept(praise);
    }

    /**
     * 设置子节点
     *
     * @param pid    父节点
     * @param voList 全部列表
     * @return list
     */
    private List<SysAreaVO> treeBin(Long pid, List<SysAreaVO> voList) {
        List<SysAreaVO> collectList = voList.stream().filter(parent -> pid.equals(parent.getPid())).toList();
        collectList.forEach(parent -> parent.setChildren(this.treeBin(parent.getId(), voList)));
        return collectList;
    }
}
