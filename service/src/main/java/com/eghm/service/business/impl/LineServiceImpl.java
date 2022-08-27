package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.LineMapper;
import com.eghm.dao.model.Homestay;
import com.eghm.dao.model.Line;
import com.eghm.model.dto.business.line.LineAddRequest;
import com.eghm.model.dto.business.line.LineEditRequest;
import com.eghm.model.dto.business.line.LineQueryRequest;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineService")
@AllArgsConstructor
@Slf4j
public class LineServiceImpl implements LineService {

    private final LineMapper lineMapper;

    private final LineDayConfigService lineDayConfigService;

    @Override
    public Page<Line> getByPage(LineQueryRequest request) {
        LambdaQueryWrapper<Line> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Line::getState, request.getState());
        wrapper.eq(request.getDuration() != null, Line::getDuration, request.getDuration());
        wrapper.eq(request.getPlatformState() != null, Line::getPlatformState, request.getPlatformState());
        wrapper.eq(request.getTravelAgencyId() != null, Line::getTravelAgencyId, request.getTravelAgencyId());
        wrapper.eq(request.getStartProvinceId() != null, Line::getStartProvinceId, request.getStartProvinceId());
        wrapper.eq(request.getStartCityId() != null, Line::getStartCityId, request.getStartCityId());
        wrapper.like(StrUtil.isNotBlank(request.getTitle()), Line::getTitle, request.getTitle());
        return lineMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(LineAddRequest request) {
        this.checkTitleRedo(request.getTitle(), request.getTravelAgencyId(), null);
        Line line = DataUtil.copy(request, Line.class);
        lineMapper.insert(line);
        lineDayConfigService.insertOrUpdate(line.getId(), request.getConfigList());
    }

    @Override
    public void update(LineEditRequest request) {
        this.checkTitleRedo(request.getTitle(), request.getTravelAgencyId(), request.getId());
        Line line = DataUtil.copy(request, Line.class);
        lineMapper.updateById(line);
        lineDayConfigService.insertOrUpdate(line.getId(), request.getConfigList());
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Line> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Line::getId, id);
        wrapper.set(Line::getState, state);
        lineMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Line> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Line::getId, id);
        wrapper.set(Line::getPlatformState, state);
        lineMapper.update(null, wrapper);
    }

    /**
     * 校验线路名称是否被占用, 同一旅行社线路不能重复
     * @param title 名称
     * @param travelAgencyId 旅行社id
     * @param id    id
     */
    private void checkTitleRedo(String title, Long travelAgencyId, Long id) {
        LambdaQueryWrapper<Line> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Line::getTitle, title);
        wrapper.eq(Line::getTravelAgencyId, travelAgencyId);
        wrapper.ne(id != null, Line::getId, id);
        Integer count = lineMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("线路名称被占用 [{}] [{}]", title, travelAgencyId);
            throw new BusinessException(ErrorCode.LINE_TITLE_REDO);
        }
    }
}
