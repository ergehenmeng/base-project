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
import com.eghm.dto.business.scenic.*;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ScenicMapper;
import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.ActivityService;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.business.ScenicService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.activity.ActivityBaseDTO;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.scenic.BaseScenicResponse;
import com.eghm.vo.business.scenic.ScenicDetailVO;
import com.eghm.vo.business.scenic.ScenicResponse;
import com.eghm.vo.business.scenic.ScenicVO;
import com.eghm.vo.business.scenic.ticket.TicketBaseVO;
import com.eghm.vo.business.scenic.ticket.TicketPriceVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/14
 */
@Service("scenicService")
@AllArgsConstructor
@Slf4j
public class ScenicServiceImpl implements ScenicService {

    private final GeoService geoService;

    private final ScenicMapper scenicMapper;

    private final SysConfigApi sysConfigApi;

    private final CommonService commonService;

    private final SysDictService sysDictService;

    private final SysAreaService sysAreaService;

    private final ActivityService activityService;

    private final ScenicTicketMapper scenicTicketMapper;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<ScenicResponse> getByPage(ScenicQueryRequest request) {
        return scenicMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<ScenicResponse> getList(ScenicQueryRequest request) {
        return scenicMapper.listPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public List<BaseScenicResponse> getList(Long merchantId) {
        return scenicMapper.getBaseList(merchantId);
    }

    @Override
    public void createScenic(ScenicAddRequest request) {
        this.redoTitle(request.getScenicName(), null);
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenic.setState(State.UN_SHELVE);
        scenic.setMerchantId(SecurityHolder.getMerchantId());
        scenic.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        scenicMapper.insert(scenic);
    }

    @Override
    public void updateScenic(ScenicEditRequest request) {
        this.redoTitle(request.getScenicName(), request.getId());
        Scenic select = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(select.getMerchantId());
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenic.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        scenicMapper.updateById(scenic);
    }

    @Override
    public Scenic selectById(Long id) {
        return scenicMapper.selectById(id);
    }

    @Override
    public Scenic selectByIdShelve(Long id) {
        Scenic scenic = scenicMapper.selectById(id);
        if (scenic == null || scenic.getState() != State.SHELVE) {
            log.warn("查询景区详情失败, 景区可能已下架 [{}]", id);
            throw new BusinessException(ErrorCode.SCENIC_DOWN);
        }
        return scenic;
    }

    @Override
    public Scenic selectByIdRequired(Long id) {
        Scenic scenic = scenicMapper.selectById(id);
        if (scenic == null) {
            log.warn("查询景区详情失败, 景区可能已删除 [{}]", id);
            throw new BusinessException(ErrorCode.SCENIC_DELETE);
        }
        return scenic;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Scenic::getMerchantId, merchantId);
        wrapper.set(Scenic::getState, state);
        scenicMapper.update(null, wrapper);
    }

    @Override
    public List<ScenicVO> getByPage(ScenicQueryDTO dto) {
        Page<ScenicVO> byPage = scenicMapper.getByPage(dto.createPage(false), dto);
        List<ScenicVO> voList = byPage.getRecords();
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        // 由于距离计算比较耗时, 因此按需决定是否要支持距离
        boolean containDistance = sysConfigApi.getBoolean(ConfigConstant.SCENIC_CONTAIN_DISTANCE);
        LinkedHashMap<String, Double> hashMap = new LinkedHashMap<>();
        containDistance = containDistance && dto.getLongitude() != null && dto.getLatitude() != null;
        if (containDistance) {
            hashMap = geoService.radius(CacheConstant.GEO_SCENIC_DISTANCE, dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue(), 10);
        }
        for (ScenicVO vo : voList) {
            // 封面图默认取第一张
            vo.setCoverUrl(vo.getCoverUrl().split(CommonConstant.COMMA)[0]);
            vo.setDistance(containDistance ? BigDecimal.valueOf(hashMap.get(String.valueOf(vo.getId()))) : null);
        }
        return voList;
    }

    @Override
    public ScenicDetailVO detailById(ScenicDetailDTO dto) {
        Scenic scenic = this.selectByIdShelve(dto.getScenicId());
        ScenicDetailVO vo = DataUtil.copy(scenic, ScenicDetailVO.class, "tag");
        // 用户未开启定位, 不查询距离
        if (dto.getLongitude() != null && dto.getLatitude() != null) {
            double distance = geoService.distance(CacheConstant.GEO_SCENIC_DISTANCE, String.valueOf(dto.getScenicId()), dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue());
            vo.setDistance(BigDecimal.valueOf(distance));
        }
        // 景区地址
        vo.setDetailAddress(sysAreaService.parseArea(scenic.getProvinceId(), scenic.getCityId(), scenic.getCountyId(), scenic.getDetailAddress()) );
        // 景区标签
        vo.setTagList(sysDictService.getTags(DictConstant.SCENIC_TAG, scenic.getTag()));
        // 景区门票
        List<TicketBaseVO> ticketList = scenicTicketMapper.getTicketList(dto.getScenicId());
        vo.setTicketList(ticketList);
        // 景区关联的活动
        List<ActivityBaseDTO> activityList = activityService.scenicActivityList(dto.getScenicId());
        vo.setActivityList(activityList);
        // 是否加入收藏
        vo.setCollect(memberCollectService.checkCollect(dto.getScenicId(), CollectType.SCENIC));
        return vo;
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.eq(Scenic::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(Scenic::getState, State.UN_SHELVE);
        wrapper.set(Scenic::getDeleted, true);
        scenicMapper.update(null, wrapper);
    }

    @Override
    public void updatePrice(Long id) {
        TicketPriceVO vo = scenicTicketMapper.calcPrice(id);
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.set(Scenic::getMinPrice, vo.getMinPrice());
        wrapper.set(Scenic::getMaxPrice, vo.getMaxPrice());
        scenicMapper.update(null, wrapper);
    }

    @Override
    public Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request) {
        return scenicMapper.getStorePage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getMerchantId, merchantId);
        wrapper.set(Scenic::getState, State.FORCE_UN_SHELVE);
        scenicMapper.update(null, wrapper);
        LambdaUpdateWrapper<ScenicTicket> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(ScenicTicket::getMerchantId, merchantId);
        updateWrapper.set(ScenicTicket::getState, State.FORCE_UN_SHELVE);
        scenicTicketMapper.update(null, updateWrapper);
    }

    /**
     * 新增编辑时判断景区名称是否重复
     *
     * @param title 景区名称
     * @param id    id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Scenic::getScenicName, title);
        wrapper.ne(id != null, Scenic::getId, id);
        Long count = scenicMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.SCENIC_REDO);
        }
    }
}
