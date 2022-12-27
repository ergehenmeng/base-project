package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.LineConfigMapper;
import com.eghm.model.LineConfig;
import com.eghm.model.dto.business.line.config.LineConfigOneRequest;
import com.eghm.model.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.model.dto.business.line.config.LineConfigRequest;
import com.eghm.model.vo.business.line.config.LineConfigResponse;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.LineConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineConfigService")
@AllArgsConstructor
@Slf4j
public class LineConfigServiceImpl implements LineConfigService {

    private final LineConfigMapper lineConfigMapper;

    private final CommonService commonService;

    @Override
    public void setup(LineConfigRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        commonService.checkMaxDay(ConfigConstant.LINE_CONFIG_MAX_DAY, between);

        List<Integer> week = request.getWeek();
        for (int i = 0; i <= between; i++) {
            LocalDate localDate = request.getStartDate().plusDays(i);
            if (!week.contains(localDate.getDayOfWeek().getValue())) {
                continue;
            }
            LineConfig config = DataUtil.copy(request, LineConfig.class);
            config.setId(IdWorker.getId());
            config.setConfigDate(localDate);
            lineConfigMapper.insertOrUpdate(config);
        }
    }

    @Override
    public void setDay(LineConfigOneRequest request) {
        LineConfig config = this.getConfig(request.getLineId(), request.getConfigDate());
        if (config == null) {
            config = DataUtil.copy(request, LineConfig.class);
            lineConfigMapper.insert(config);
        } else {
            config.setLinePrice(request.getLinePrice());
            config.setSalePrice(request.getSalePrice());
            config.setStock(request.getStock());
            config.setState(request.getState());
            lineConfigMapper.updateById(config);
        }
    }

    @Override
    public List<LineConfigResponse> getMonthList(LineConfigQueryRequest request) {
        LocalDate month = DateUtil.parseFirstDayOfMonth(request.getMonth());
        List<LineConfig> configList = this.getMonthConfig(month, request.getLineId());
        return DataUtil.paddingMonth(configList, (lineConfig, localDate) -> lineConfig.getConfigDate().equals(localDate), LineConfigResponse::new, month);
    }


    @Override
    public LineConfig getConfig(Long lineId, LocalDate localDate) {
        LambdaQueryWrapper<LineConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LineConfig::getLineId, lineId);
        wrapper.eq(LineConfig::getConfigDate, localDate);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return lineConfigMapper.selectOne(wrapper);
    }
    
    @Override
    public void updateStock(Long id, Integer num) {
        int stock = lineConfigMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("更新线路库存失败 [{}] [{}] [{}]", id, num, stock);
            throw new BusinessException(ErrorCode.LINE_STOCK);
        }
    }
    
    /**
     * 获取某一月配置信息
     * @param month 月份
     * @param lineId 线路ID
     * @return 线路价格配置
     */
    private List<LineConfig> getMonthConfig(LocalDate month, Long lineId) {
        LambdaQueryWrapper<LineConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LineConfig::getLineId, lineId);
        LocalDate endDate = month.plusMonths(1);
        wrapper.ge(LineConfig::getConfigDate, month);
        wrapper.lt(LineConfig::getConfigDate, endDate);
        return lineConfigMapper.selectList(wrapper);
    }
}
