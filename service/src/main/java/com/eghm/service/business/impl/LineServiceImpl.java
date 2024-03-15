package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.line.LineAddRequest;
import com.eghm.dto.business.line.LineEditRequest;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LineMapper;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mapper.TravelAgencyMapper;
import com.eghm.model.Line;
import com.eghm.model.LineDayConfig;
import com.eghm.model.TravelAgency;
import com.eghm.service.business.*;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.line.LineDayConfigResponse;
import com.eghm.vo.business.line.LineDetailVO;
import com.eghm.vo.business.line.LineResponse;
import com.eghm.vo.business.line.LineVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.eghm.enums.ErrorCode.LINE_DOWN;
import static com.eghm.enums.ErrorCode.LINE_NULL;

/**
 * @author 二哥很猛
 * @since 2022/8/26
 */
@Service("lineService")
@AllArgsConstructor
@Slf4j
public class LineServiceImpl implements LineService {

    private final LineMapper lineMapper;

    private final CommonService commonService;

    private final SysAreaService sysAreaService;

    private final LineConfigService lineConfigService;

    private final TravelAgencyMapper travelAgencyMapper;

    private final TravelAgencyService travelAgencyService;

    private final LineDayConfigService lineDayConfigService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<LineResponse> getByPage(LineQueryRequest request) {
        return lineMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<LineResponse> getList(LineQueryRequest request) {
        Page<LineResponse> listPage = lineMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public void create(LineAddRequest request) {
        this.titleRedo(request.getTitle(), request.getTravelAgencyId(), null);
        Long merchantId = SecurityHolder.getMerchantId();
        this.checkTravelAgency(request.getTravelAgencyId(), merchantId);

        Line line = DataUtil.copy(request, Line.class);
        line.setMerchantId(merchantId);
        line.setCreateDate(LocalDate.now());
        lineMapper.insert(line);

        lineDayConfigService.insertOrUpdate(line.getId(), request.getConfigList());
    }

    @Override
    public void update(LineEditRequest request) {
        this.titleRedo(request.getTitle(), request.getTravelAgencyId(), request.getId());
        Long merchantId = SecurityHolder.getMerchantId();
        this.checkTravelAgency(request.getTravelAgencyId(), merchantId);

        Line line = DataUtil.copy(request, Line.class);
        lineMapper.updateById(line);
        lineDayConfigService.insertOrUpdate(line.getId(), request.getConfigList());
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Line> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Line::getId, id);
        wrapper.set(Line::getState, state);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Line::getMerchantId, merchantId);
        lineMapper.update(null, wrapper);
    }

    @Override
    public Line selectByIdRequired(Long id) {
        Line select = this.selectById(id);
        if (select == null) {
            log.error("该线路商品不存在 [{}]", id);
            throw new BusinessException(LINE_NULL);
        }
        return select;
    }

    @Override
    public List<LineVO> getByPage(LineQueryDTO dto) {
        Page<LineVO> voPage = lineMapper.getByPage(dto.createPage(false), dto);
        List<LineVO> voList = voPage.getRecords();
        // 转义城市名称
        voList.forEach(vo -> vo.setStartCity(sysAreaService.parseCity(vo.getStartCityId())));
        return voList;
    }

    @Override
    public Line selectByIdShelve(Long id) {
        Line line = this.selectById(id);
        if (line == null || line.getState() != State.SHELVE) {
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
        LambdaUpdateWrapper<Line> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Line::getId, id);
        wrapper.set(Line::getState, State.UN_SHELVE);
        wrapper.set(Line::getDeleted, true);
        wrapper.eq(Line::getMerchantId, SecurityHolder.getMerchantId());
        lineMapper.update(null, wrapper);
    }

    @Override
    public LineDetailVO detailById(Long id) {
        LineDetailVO vo = lineMapper.getById(id);
        if (vo == null) {
            log.error("该线路商品已下架 [{}]", id);
            throw new BusinessException(LINE_DOWN);
        }
        List<LineDayConfig> dayConfigList = lineDayConfigService.getByLineId(id);
        vo.setDayList(DataUtil.copy(dayConfigList, LineDayConfigResponse.class));
        // 出发地格式化
        vo.setStartPoint(sysAreaService.parseCity(vo.getStartCityId()));
        // 最低参考价
        vo.setMinPrice(lineConfigService.getMinPrice(id, LocalDate.now()));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.LINE));
        return vo;
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO storeScore = orderEvaluationMapper.getStoreScore(vo.getStoreId());
        if (storeScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示旅行社评分 [{}]", vo.getStoreId());
            return;
        }

        travelAgencyMapper.updateScore(vo.getStoreId(), DecimalUtil.calcAvgScore(storeScore.getTotalScore(), storeScore.getNum()));

        AvgScoreVO productScore = orderEvaluationMapper.getProductScore(vo.getProductId());
        if (productScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示线路商品评分 [{}]", vo.getProductId());
            return;
        }
        lineMapper.updateScore(vo.getProductId(), DecimalUtil.calcAvgScore(productScore.getTotalScore(), productScore.getNum()));
    }

    @Override
    public Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request) {
        return lineMapper.getProductPage(request.createPage(), request);
    }

    /**
     * 校验线路名称是否被占用, 同一旅行社线路不能重复
     *
     * @param title          名称
     * @param travelAgencyId 旅行社id
     * @param id             id
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

    private void checkTravelAgency(Long travelAgencyId, Long merchantId) {
        TravelAgency travelAgency = travelAgencyService.selectByIdRequired(travelAgencyId);
        if (commonService.checkIsIllegal(travelAgency.getMerchantId(), merchantId)) {
            log.info("选择的旅行社不属于自己的 [{}] [{}]", travelAgencyId, merchantId);
            throw new BusinessException(ErrorCode.TRAVEL_AGENCY_NOT_FOUND);
        }
    }

}
