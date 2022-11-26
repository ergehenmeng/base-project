package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.ScenicMapper;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.model.dto.business.scenic.ScenicAddRequest;
import com.eghm.model.dto.business.scenic.ScenicEditRequest;
import com.eghm.model.dto.business.scenic.ScenicQueryDTO;
import com.eghm.model.dto.business.scenic.ScenicQueryRequest;
import com.eghm.model.vo.scenic.ScenicListVO;
import com.eghm.model.vo.scenic.ScenicVO;
import com.eghm.model.vo.scenic.ticket.TicketBaseVO;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.sys.GeoService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/6/14
 */
@Service("scenicService")
@AllArgsConstructor
@Slf4j
public class ScenicServiceImpl implements ScenicService {

    private final ScenicMapper scenicMapper;

    private final SysConfigApi sysConfigApi;

    private final GeoService geoService;

    private final SysAreaService sysAreaService;

    private final ScenicTicketService scenicTicketService;

    @Override
    public Page<Scenic> getByPage(ScenicQueryRequest request) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getMerchantId() != null, Scenic::getMerchantId, request.getMerchantId());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Scenic::getScenicName, request.getQueryName());
        wrapper.eq(request.getState() != null, Scenic::getState, request.getState());
        wrapper.last(" order by sort asc, id desc ");
        return scenicMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void createScenic(ScenicAddRequest request) {
        this.redoTitle(request.getScenicName(), null);
        // TODO 商户id添加
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenicMapper.insert(scenic);
    }

    @Override
    public void updateScenic(ScenicEditRequest request) {
        this.redoTitle(request.getScenicName(), request.getId());
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenicMapper.updateById(scenic);
    }

    @Override
    public Scenic getByMerchantId(Long merchantId) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Scenic::getMerchantId, merchantId);
        return scenicMapper.selectOne(wrapper);
    }

    @Override
    public Scenic selectById(Long id) {
        return scenicMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.set(Scenic::getState, state);
        scenicMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        Scenic scenic = scenicMapper.selectById(id);
        if (scenic.getState() != State.SHELVE) {
            log.info("景区商户尚未提交");
            throw new BusinessException(ErrorCode.SCENIC_NOT_UP);
        }
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.set(Scenic::getPlatformState, state);
        scenicMapper.update(null, wrapper);
    }

    @Override
    public List<ScenicListVO> getByPage(ScenicQueryDTO dto) {
        Page<ScenicListVO> byPage = scenicMapper.getByPage(dto.createPage(false), dto);
        List<ScenicListVO> voList = byPage.getRecords();
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        // 由于距离计算比较耗时, 因此按需决定是否要支持距离

        boolean flag = sysConfigApi.getBoolean(ConfigConstant.SCENIC_CONTAIN_DISTANCE);
        LinkedHashMap<String, Double> hashMap = new LinkedHashMap<>();
        if (flag && dto.getLongitude() != null && dto.getLatitude() != null) {
            hashMap = geoService.radius(CacheConstant.GEO_SCENIC_DISTANCE, dto.getLongitude().doubleValue(), dto.getLatitude().doubleValue(), 10);
        }
        for (ScenicListVO vo : voList) {
            // 封面图默认取第一张
            vo.setCoverUrl(vo.getCoverUrl().split(CommonConstant.SPLIT)[0]);
            vo.setDistance(flag ? BigDecimal.valueOf(hashMap.get(String.valueOf(vo.getId()))): null);
        }
        return voList;
    }

    @Override
    public ScenicVO detailById(Long id, Double longitude, Double latitude) {
        Scenic scenic = scenicMapper.selectById(id);
        if (scenic == null || scenic.getPlatformState() != PlatformState.SHELVE) {
            log.warn("查询景区详情失败, 景区可能已下架 [{}]", id);
            throw new BusinessException(ErrorCode.SCENIC_DOWN);
        }
        ScenicVO vo = DataUtil.copy(scenic, ScenicVO.class);
        // 用户未开启定位, 不查询距离
        if (longitude != null && latitude != null) {
            double distance = geoService.distance(CacheConstant.GEO_SCENIC_DISTANCE, String.valueOf(id), longitude, latitude);
            vo.setDistance(BigDecimal.valueOf(distance));
        }
        vo.setDetailAddress(sysAreaService.parseArea(scenic.getProvinceId(), scenic.getCityId(), scenic.getCountyId()) + scenic.getDetailAddress());

        List<TicketBaseVO> ticketList = scenicTicketService.getTicketList(id);
        vo.setTicketList(ticketList);
        return vo;
    }

    @Override
    public void deleteById(Long id) {
        scenicMapper.deleteById(id);
    }

    /**
     * 新增编辑时判断景区名称是否重复
     * @param title 景区名称
     * @param id id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Scenic::getScenicName, title);
        wrapper.ne(id != null, Scenic::getId, id);
        Integer count = scenicMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.SCENIC_REDO);
        }
    }
}
