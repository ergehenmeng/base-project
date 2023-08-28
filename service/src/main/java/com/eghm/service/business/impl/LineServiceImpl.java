package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.line.LineAddRequest;
import com.eghm.dto.business.line.LineEditRequest;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LineMapper;
import com.eghm.model.Line;
import com.eghm.model.LineDayConfig;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.line.LineDayConfigResponse;
import com.eghm.vo.business.line.LineDetailVO;
import com.eghm.vo.business.line.LineResponse;
import com.eghm.vo.business.line.LineVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.eghm.enums.ErrorCode.LINE_DOWN;

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
    public Page<LineResponse> getByPage(LineQueryRequest request) {
        return lineMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(LineAddRequest request) {
        this.titleRedo(request.getTitle(), request.getTravelAgencyId(), null);
        Line line = DataUtil.copy(request, Line.class);
        line.setMerchantId(SecurityHolder.getMerchantId());
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
    public List<LineVO> getByPage(LineQueryDTO dto) {
        Page<LineVO> voPage = lineMapper.getByPage(dto.createPage(false), dto);
        List<LineVO> voList = voPage.getRecords();
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
    public LineDetailVO detailById(Long id) {
        Line line = this.selectByIdShelve(id);
        LineDetailVO vo = DataUtil.copy(line, LineDetailVO.class);

        List<LineDayConfig> dayConfigList = lineDayConfigService.getByLineId(id);
        vo.setDayList(DataUtil.copy(dayConfigList, LineDayConfigResponse.class));
        // 出发地格式化
        vo.setStartPoint(sysAreaService.parseProvinceCity(line.getStartProvinceId(), line.getStartCityId()));
        // 最低参考价
        vo.setMinPrice(lineConfigService.getMinPrice(id, LocalDate.now()));
        return vo;
    }

    @Override
    public void updateScore(Long productId, BigDecimal score) {
        LambdaUpdateWrapper<Line> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Line::getId, productId);
        wrapper.set(Line::getScore, score);
        lineMapper.update(null, wrapper);
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
        Long count = lineMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("线路名称被占用 [{}] [{}]", title, travelAgencyId);
            throw new BusinessException(ErrorCode.LINE_TITLE_REDO);
        }
    }
}
