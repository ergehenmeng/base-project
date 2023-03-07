package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.LineMapper;
import com.eghm.model.Line;
import com.eghm.model.LineDayConfig;
import com.eghm.model.dto.business.line.LineAddRequest;
import com.eghm.model.dto.business.line.LineEditRequest;
import com.eghm.model.dto.business.line.LineQueryDTO;
import com.eghm.model.dto.business.line.LineQueryRequest;
import com.eghm.model.vo.business.line.LineDayConfigResponse;
import com.eghm.model.vo.business.line.LineListVO;
import com.eghm.model.vo.business.line.LineVO;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.eghm.common.enums.ErrorCode.LINE_DOWN;

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

    private final LineConfigService lineConfigService;

    private final SysAreaService sysAreaService;

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
        this.titleRedo(request.getTitle(), request.getTravelAgencyId(), null);
        Line line = DataUtil.copy(request, Line.class);
        lineMapper.insert(line);
        lineDayConfigService.insertOrUpdate(line.getId(), request.getConfigList());
    }

    @Override
    public void update(LineEditRequest request) {
        this.titleRedo(request.getTitle(), request.getTravelAgencyId(), request.getId());
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

    @Override
    public Line selectByIdRequired(Long id) {
        Line select = this.selectById(id);
        if (select == null) {
            log.error("该线路商品不存在 [{}]", id);
            throw new BusinessException(LINE_DOWN);
        }
        return select;
    }

    @Override
    public List<LineListVO> getByPage(LineQueryDTO dto) {
        Page<LineListVO> voPage = lineMapper.getByPage(dto.createPage(false), dto);
        List<LineListVO> voList = voPage.getRecords();
        // 转义城市名称
        if (CollUtil.isNotEmpty(voList)) {
            voList.forEach(vo -> vo.setStartCity(sysAreaService.getById(vo.getStartCityId()).getTitle()));
        }
        return voList;
    }

    @Override
    public Line selectByIdShelve(Long id) {
        Line line = this.selectByIdRequired(id);
        if (line == null) {
            log.error("该线路商品已下架 [{}]", id);
            throw new BusinessException(LINE_DOWN);
        }
        return line;
    }

    @Override
    public Line selectById(Long id) {
        return lineMapper.selectById(id);
    }

    @Override
    public void deleteById(Long id) {
        lineMapper.deleteById(id);
    }

    @Override
    public LineVO detailById(Long id) {
        Line line = this.selectByIdShelve(id);
        LineVO vo = DataUtil.copy(line, LineVO.class);

        List<LineDayConfig> dayConfigList = lineDayConfigService.getByLineId(id);
        vo.setDayList(DataUtil.copy(dayConfigList, LineDayConfigResponse.class));
        // 出发地格式化
        vo.setStartPoint(sysAreaService.parseProvinceCity(line.getStartProvinceId(), line.getStartCityId()));
        // 最低参考价
        vo.setMinPrice(lineConfigService.getMinPrice(id, LocalDate.now()));
        return vo;
    }

    /**
     * 校验线路名称是否被占用, 同一旅行社线路不能重复
     * @param title 名称
     * @param travelAgencyId 旅行社id
     * @param id    id
     */
    private void titleRedo(String title, Long travelAgencyId, Long id) {
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
