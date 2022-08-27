package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dao.mapper.LineDayConfigMapper;
import com.eghm.dao.model.LineDayConfig;
import com.eghm.model.dto.business.line.LineDayConfigRequest;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineDayConfigService")
@AllArgsConstructor
@Slf4j
public class LineDayConfigServiceImpl implements LineDayConfigService {

    private final LineDayConfigMapper lineDayConfigMapper;

    @Override
    public void insertOrUpdate(Long lineId, List<LineDayConfigRequest> configList) {
        this.deleteNotIn(lineId, configList);
        for (LineDayConfigRequest request : configList) {
            LineDayConfig config = DataUtil.copy(request, LineDayConfig.class);
            config.setLineId(lineId);
            config.setId(IdWorker.getId());
            lineDayConfigMapper.insertOrUpdate(config);
        }
    }

    /**
     * 删除多余的配置信息
     * @param lineId 线路id
     * @param configList 配置信息
     */
    private void deleteNotIn(Long lineId, List<LineDayConfigRequest> configList) {
        List<Integer> indexList = configList.stream().map(LineDayConfigRequest::getRouteIndex).collect(Collectors.toList());
        LambdaUpdateWrapper<LineDayConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LineDayConfig::getLineId, lineId);
        wrapper.notIn(LineDayConfig::getRouteIndex, indexList);
        lineDayConfigMapper.delete(wrapper);
    }
}
