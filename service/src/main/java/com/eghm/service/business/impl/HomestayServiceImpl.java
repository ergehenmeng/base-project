package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.GeoService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.homestay.*;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.CollectType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleType;
import com.eghm.enums.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.HomestayMapper;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.model.Homestay;
import com.eghm.model.Merchant;
import com.eghm.model.SysDictItem;
import com.eghm.service.business.*;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.homestay.BaseHomestayResponse;
import com.eghm.vo.business.homestay.HomestayDetailVO;
import com.eghm.vo.business.homestay.HomestayResponse;
import com.eghm.vo.business.homestay.HomestayVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.eghm.enums.ErrorCode.HOMESTAY_NOT_COMPLETE;
import static com.eghm.enums.ErrorCode.HOMESTAY_SEARCH_MAX;

/**
 * @author 二哥很猛 2022/6/25
 */
@Service("homestayService")
@AllArgsConstructor
@Slf4j
public class HomestayServiceImpl implements HomestayService, MerchantInitService {

    private final GeoService geoService;

    private final SysConfigApi sysConfigApi;

    private final CommonService commonService;

    private final HomestayMapper homestayMapper;

    private final SysDictService sysDictService;

    private final SysAreaService sysAreaService;

    private final HomestayRoomService homestayRoomService;

    private final MemberCollectService memberCollectService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    @Override
    public Page<HomestayResponse> getByPage(HomestayQueryRequest request) {
        Page<HomestayResponse> listPage = homestayMapper.listPage(request.createPage(), request);
        this.parseAddress(listPage);
        return listPage;
    }

    @Override
    public List<BaseHomestayResponse> getList() {
        return homestayMapper.getBaseList(SecurityHolder.getMerchantId());
    }

    @Override
    public List<HomestayResponse> getList(HomestayQueryRequest request) {
        Page<HomestayResponse> listPage = homestayMapper.listPage(request.createNullPage(), request);
        this.parseAddress(listPage);
        return listPage.getRecords();
    }

    @Override
    public void create(HomestayAddRequest request) {
        this.titleRedo(request.getTitle(), null);
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestay.setState(State.UN_SHELVE);
        homestay.setCoverUrl(CollUtil.join(request.getCoverList(), ","));
        homestay.setKeyService(CollUtil.join(request.getServiceList(), ","));
        homestayMapper.insert(homestay);
        geoService.addPoint(CacheConstant.GEO_POINT_HOMESTAY, homestay.getId().toString(), request.getLongitude().doubleValue(), request.getLatitude().doubleValue());
    }

    @Override
    public void update(HomestayEditRequest request) {
        this.titleRedo(request.getTitle(), request.getId());
        Homestay required = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(required.getMerchantId());
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestay.setCoverUrl(CollUtil.join(request.getCoverList(), ","));
        homestay.setKeyService(CollUtil.join(request.getServiceList(), ","));
        homestayMapper.updateById(homestay);
        geoService.addPoint(CacheConstant.GEO_POINT_HOMESTAY, homestay.getId().toString(), request.getLongitude().doubleValue(), request.getLatitude().doubleValue());
    }

    @Override
    public void updateState(Long id, State state) {
        if (state == State.SHELVE) {
            Homestay homestay = this.selectByIdRequired(id);
            BeanValidator.validate(homestay, HOMESTAY_NOT_COMPLETE);
        }
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Homestay::getMerchantId, merchantId);
        wrapper.set(Homestay::getState, state);
        homestayMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Homestay::getMerchantId, merchantId);
        wrapper.set(Homestay::getState, State.UN_SHELVE);
        wrapper.set(Homestay::getDeleted, true);
        homestayMapper.update(null, wrapper);
    }

    @Override
    public Homestay selectByIdRequired(Long id) {
        Homestay homestay = homestayMapper.selectById(id);
        if (homestay == null) {
            log.error("民宿未查询到, 可能已被删除 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_NULL);
        }
        return homestay;
    }

    @Override
    public Homestay selectByIdShelve(Long id) {
        Homestay homestay = homestayMapper.selectById(id);
        if (homestay == null || homestay.getState() != State.SHELVE) {
            log.error("该民宿已下架 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_DOWN);
        }
        return homestay;
    }

    @Override
    public List<HomestayVO> getByPage(HomestayQueryDTO dto) {
        boolean getDistance = dto.getSortByDistance() != null && (dto.getLongitude() == null || dto.getLatitude() == null);
        if (getDistance) {
            log.info("民宿列表未获取到用户经纬度, 无法进行距离排序 [{}] [{}]", dto.getLongitude(), dto.getLatitude());
            throw new BusinessException(ErrorCode.POSITION_NO);
        }
        int maxDay = sysConfigApi.getInt(ConfigConstant.HOMESTAY_MAX_RESERVE_DAY, 30);
        long stayNum = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());
        if (stayNum > maxDay) {
            log.info("民宿预定日期搜索超过最大预定天数 [{}]", stayNum);
            throw new BusinessException(HOMESTAY_SEARCH_MAX, maxDay);
        }
        // 入住天数
        dto.setStayNum(stayNum);
        // 离店日期不含当天
        dto.setEndDate(dto.getEndDate().minusDays(1));
        // 分页查询
        Page<HomestayVO> page = homestayMapper.getByPage(dto.createPage(false), dto);
        List<HomestayVO> voList = page.getRecords();
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        // 标签/地址等字段填充
        // 查询数据字典,匹配标签列表
        List<SysDictItem> dictList = sysDictService.getDictByNid(DictConstant.HOMESTAY_TAG);
        // 针对针对标签,位置和最低价进行赋值或解析
        for (HomestayVO vo : voList) {
            vo.setTagList(commonService.parseTags(dictList, vo.getTagIds()));
            vo.setDetailAddress(sysAreaService.parseArea(vo.getCityId(), vo.getCountyId(), vo.getDetailAddress()) );
        }
        return voList;
    }

    @Override
    public HomestayDetailVO detailById(HomestayDTO dto) {
        Homestay homestay = this.selectByIdShelve(dto.getId());
        HomestayDetailVO vo = DataUtil.copy(homestay, HomestayDetailVO.class);
        vo.setDetailAddress(sysAreaService.parseArea(homestay.getCityId(), homestay.getCountyId(), homestay.getDetailAddress()));
        vo.setTagList(sysDictService.getTags(DictConstant.HOMESTAY_TAG, homestay.getTag()));
        vo.setRecommendRoomList(homestayRoomService.getRecommendRoom(dto.getId()));
        vo.setCollect(memberCollectService.checkCollect(dto.getId(), CollectType.HOMESTAY));
        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            vo.setDistance((int) geoService.distance(CacheConstant.GEO_POINT_HOMESTAY, String.valueOf(dto.getId()), dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue()));
        }
        return vo;
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO storeScore = orderEvaluationMapper.getStoreScore(vo.getStoreId());
        if (storeScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示民宿评分 [{}]", vo.getStoreId());
            return;
        }
        homestayMapper.updateScore(vo.getStoreId(), DecimalUtil.calcAvgScore(storeScore.getTotalScore(), storeScore.getNum()));
    }

    @Override
    public Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request) {
        return homestayMapper.getStorePage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Homestay::getState, State.FORCE_UN_SHELVE);
        wrapper.eq(Homestay::getMerchantId, merchantId);
        homestayMapper.update(null, wrapper);
        homestayRoomService.logout(merchantId);
    }

    @Override
    public void init(Merchant merchant) {
        Homestay homestay = new Homestay();
        homestay.setMerchantId(merchant.getId());
        homestay.setState(State.UN_SHELVE);
        homestayMapper.insert(homestay);
    }

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.HOMESTAY);
    }

    /**
     * 校验民宿名称是否被占用
     *
     * @param title 民宿名称
     * @param id    id
     */
    private void titleRedo(String title, Long id) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Homestay::getTitle, title);
        wrapper.ne(id != null, Homestay::getId, id);
        Long count = homestayMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("民宿名称被占用 [{}]", title);
            throw new BusinessException(ErrorCode.HOMESTAY_TITLE_REDO);
        }
    }

    /**
     * 解析并格式化地址
     *
     * @param listPage 列表
     */
    private void parseAddress(Page<HomestayResponse> listPage) {
        if (CollUtil.isNotEmpty(listPage.getRecords())) {
            listPage.getRecords().forEach(response -> response.setDetailAddress(sysAreaService.parseArea(response.getCityId(), response.getCountyId(), response.getDetailAddress())));
        }
    }
}
