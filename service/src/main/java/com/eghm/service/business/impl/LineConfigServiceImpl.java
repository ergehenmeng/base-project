package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.utils.DateUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.LineConfigMapper;
import com.eghm.dao.model.LineConfig;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getStartDate());
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
    public List<LineConfigResponse> getList(LineConfigQueryRequest request) {
        LocalDate month = DateUtil.parseFirstDayOfMonth(request.getMonth());
        List<LineConfig> configList = this.getMonthConfig(month, request.getLineId());
        int ofMonth = month.lengthOfMonth();
        List<LineConfigResponse> responseList = new ArrayList<>(45);
        // 月初到月末进行拼装
        for (int i = 0; i < ofMonth; i++) {
            LocalDate localDate = month.plusDays(i);
            Optional<LineConfig> optional = configList.stream().filter(config -> config.getConfigDate().isEqual(localDate)).findFirst();
            // 当天已经设置过金额
            if (optional.isPresent()) {
                LineConfigResponse response = DataUtil.copy(optional.get(), LineConfigResponse.class);
                response.setHasSet(true);
                responseList.add(response);
            } else {
                // 当天没有设置过
                responseList.add(new LineConfigResponse(false, localDate));
            }
        }
        return responseList;
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
