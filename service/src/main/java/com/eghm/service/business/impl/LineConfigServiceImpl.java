package com.eghm.service.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.line.config.LineConfigOneRequest;
import com.eghm.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.dto.business.line.config.LineConfigRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LineConfigMapper;
import com.eghm.mapper.LineMapper;
import com.eghm.model.Line;
import com.eghm.model.LineConfig;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.LineConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.vo.business.line.config.LineConfigResponse;
import com.eghm.vo.business.line.config.LineConfigVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eghm.enums.ErrorCode.LINE_DELETE;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
@Service("lineConfigService")
@AllArgsConstructor
@Slf4j
public class LineConfigServiceImpl implements LineConfigService {

    private final LineMapper lineMapper;

    private final SysConfigApi sysConfigApi;

    private final CommonService commonService;

    private final LineConfigMapper lineConfigMapper;

    @Override
    public void setup(LineConfigRequest request) {
        this.checkLine(request.getLineId());
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
        this.checkLine(request.getLineId());
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
    public List<LineConfigVO> getPriceList(Long lineId) {
        Line line = lineMapper.selectById(lineId);
        long max = sysConfigApi.getLong(ConfigConstant.LINE_MAX_DAY, 60);
        LocalDate now = LocalDate.now();
        // 假如今天是12.12, 线路需要提前3天预约,则线路配置列表 12.12 12.13,12.14这几日均无法预约,
        LambdaQueryWrapper<LineConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.between(LineConfig::getConfigDate, now, now.plusDays(max));
        wrapper.eq(LineConfig::getLineId, lineId);

        List<LineConfig> configList = lineConfigMapper.selectList(wrapper);
        List<LineConfigVO> responseList = new ArrayList<>();

        if (CollUtil.isEmpty(configList)) {
            return responseList;
        }

        for (int i = 0; i < max; i++) {
            LocalDate configDate = now.plusDays(i);

            LineConfigVO vo = new LineConfigVO();
            vo.setConfigDate(configDate);
            // 如果线路设置了提前日期,在提前日期之前默认无法预约
            if (i < line.getAdvanceDay()) {
                vo.setState(0);
            } else {
                Optional<LineConfig> optional = configList.stream().filter(config -> configDate.isEqual(config.getConfigDate())).findFirst();
                if (optional.isPresent()) {
                    BeanUtil.copyProperties(optional.get(), vo);
                } else {
                    vo.setState(0);
                }

            }
            responseList.add(vo);
        }
        return responseList;
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

    @Override
    public Integer getMinPrice(Long lineId, LocalDate startDate) {
        Integer minPrice = lineConfigMapper.getMinPrice(lineId, startDate);
        return minPrice != null ? minPrice : 0;
    }

    /**
     * 获取某一月配置信息
     *
     * @param month  月份
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

    /**
     * 校验线路是否合法
     *
     * @param lineId 线路Id
     */
    private void checkLine(Long lineId) {
        Line select = lineMapper.selectById(lineId);
        if (select == null) {
            log.error("该线路商品不存在 [{}]", lineId);
            throw new BusinessException(LINE_DELETE);
        }
        if (commonService.checkIsIllegal(select.getMerchantId())) {
            log.info("线路不属于当前操作人 [{}] [{}]", select.getMerchantId(), SecurityHolder.getMerchantId());
            throw new BusinessException(ErrorCode.LINE_DELETE);
        }
    }
}
