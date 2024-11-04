package com.eghm.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.cache.CacheProxyService;
import com.eghm.constants.CommonConstant;
import com.eghm.mapper.SysAreaMapper;
import com.eghm.model.SysDictItem;
import com.eghm.common.CommonService;
import com.eghm.vo.sys.SysAreaVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Service("commonService")
@AllArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final SysAreaMapper sysAreaMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public List<String> parseTags(List<SysDictItem> dictList, String tagIds) {
        List<String> tagList = Lists.newArrayListWithCapacity(4);
        if (CollUtil.isEmpty(dictList)) {
            log.error("数据字典为空,不做解析 [{}]", tagIds);
            return tagList;
        }

        String[] split = tagIds.split(",");
        for (String tagId : split) {
            dictList.stream().filter(sysDict -> sysDict.getHiddenValue() == Integer.parseInt(tagId))
                    .map(SysDictItem::getShowValue)
                    .findFirst().ifPresent(tagList::add);
        }
        return tagList;
    }

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

    /**
     * 设置子节点
     *
     * @param pid    父节点
     * @param voList 全部列表
     * @return list
     */
    private List<SysAreaVO> treeBin(Long pid, List<SysAreaVO> voList) {
        List<SysAreaVO> collectList = voList.stream().filter(parent -> pid.equals(parent.getPid())).collect(Collectors.toList());
        collectList.forEach(parent -> parent.setChildren(this.treeBin(parent.getId(), voList)));
        return collectList;
    }
}
