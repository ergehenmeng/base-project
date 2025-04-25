package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.line.LineConfigDayRequest;
import com.eghm.mapper.LineConfigDayMapper;
import com.eghm.model.LineConfigDay;
import com.eghm.service.business.LineConfigDayService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
@Service("lineConfigDayService")
@AllArgsConstructor
@Slf4j
public class LineConfigDayServiceImpl implements LineConfigDayService {

    private final LineConfigDayMapper lineConfigDayMapper;

    @Override
    public void insertOrUpdate(Long lineId, List<LineConfigDayRequest> configList) {
        this.deleteNotIn(lineId, configList);
        for (LineConfigDayRequest request : configList) {
            LineConfigDay config = DataUtil.copy(request, LineConfigDay.class);
            config.setLineId(lineId);
            config.setId(IdWorker.getId());
            if (CollUtil.isNotEmpty(request.getRepastList())) {
                config.setRepast(request.getRepastList().stream().mapToInt(Integer::intValue).sum());
            }
            lineConfigDayMapper.insertOrUpdate(config);
        }
    }

    @Override
    public List<LineConfigDay> getByLineId(Long lineId) {
        LambdaUpdateWrapper<LineConfigDay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LineConfigDay::getLineId, lineId);
        wrapper.last(" order by route_index asc ");
        return lineConfigDayMapper.selectList(wrapper);
    }

    /**
     * 删除多余的配置信息
     *
     * @param lineId     线路id
     * @param configList 配置信息
     */
    private void deleteNotIn(Long lineId, List<LineConfigDayRequest> configList) {
        List<Integer> indexList = configList.stream().map(LineConfigDayRequest::getRouteIndex).collect(Collectors.toList());
        LambdaUpdateWrapper<LineConfigDay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LineConfigDay::getLineId, lineId);
        wrapper.notIn(LineConfigDay::getRouteIndex, indexList);
        lineConfigDayMapper.delete(wrapper);
    }
}
