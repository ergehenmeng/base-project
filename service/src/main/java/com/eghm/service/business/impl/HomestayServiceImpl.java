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
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.mapper.HomestayMapper;
import com.eghm.model.Homestay;
import com.eghm.model.SysDict;
import com.eghm.model.dto.business.homestay.HomestayAddRequest;
import com.eghm.model.dto.business.homestay.HomestayEditRequest;
import com.eghm.model.dto.business.homestay.HomestayQueryDTO;
import com.eghm.model.dto.business.homestay.HomestayQueryRequest;
import com.eghm.model.vo.business.homestay.HomestayListVO;
import com.eghm.model.vo.business.homestay.HomestayVO;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.service.business.HomestayService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.service.sys.SysDictService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛 2022/6/25
 */
@Service("homestayService")
@AllArgsConstructor
@Slf4j
public class HomestayServiceImpl implements HomestayService {

    private final HomestayMapper homestayMapper;

    private final CommonService commonService;

    private final SysDictService sysDictService;

    private final SysAreaService sysAreaService;

    private final SysConfigApi sysConfigApi;

    private final HomestayRoomConfigService homestayRoomConfigService;

    private final HomestayRoomService homestayRoomService;

    @Override
    public Page<Homestay> getByPage(HomestayQueryRequest request) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Homestay::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Homestay::getTitle, request.getQueryName());
        return homestayMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(HomestayAddRequest request) {
        this.titleRedo(request.getTitle(), null);
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestay.setMerchantId(SecurityHolder.getMerchantId());
        homestayMapper.insert(homestay);
    }

    @Override
    public void update(HomestayEditRequest request) {
        this.titleRedo(request.getTitle(), request.getId());
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestayMapper.updateById(homestay);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        wrapper.set(Homestay::getState, state);
        homestayMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        Homestay homestay = homestayMapper.selectById(id);
        if (homestay.getState() != State.SHELVE) {
            log.info("民宿尚未提交审核 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_NOT_UP);
        }
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        wrapper.set(Homestay::getPlatformState, state);
        homestayMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        homestayMapper.deleteById(id);
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
        Homestay homestay = this.selectByIdRequired(id);
        if (homestay.getPlatformState() != PlatformState.SHELVE) {
            log.error("该民宿已下架 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_DOWN);
        }
        return homestay;
    }

    @Override
    public List<HomestayListVO> getByPage(HomestayQueryDTO dto) {
        if (Boolean.TRUE.equals(dto.getSortByDistance()) && (dto.getLongitude() == null || dto.getLatitude() == null)) {
            log.info("民宿列表未获取到用户经纬度, 无法进行距离排序 [{}] [{}]", dto.getLongitude(), dto.getLatitude());
            throw new BusinessException(ErrorCode.POSITION_NO);
        }
        // 分页查询
        Page<HomestayListVO> page = homestayMapper.getByPage(dto.createPage(false), dto);

        List<HomestayListVO> voList = page.getRecords();
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        List<Long> homestayIds = voList.stream().map(HomestayListVO::getId).collect(Collectors.toList());
        int maxDay = sysConfigApi.getInt(ConfigConstant.HOMESTAY_MAX_RESERVE_DAY, 30);
        LocalDate startDate = LocalDate.now();
        // 查询酒店最近一个月(由系统参数配置)的最低价
        Map<Long, Integer> priceMap = homestayRoomConfigService.getHomestayMinPrice(homestayIds, startDate, startDate.plusDays(maxDay));
        // 查询数据字典,匹配标签列表
        List<SysDict> dictList = sysDictService.getDictByNid(DictConstant.HOMESTAY_TAG);
        // 针对针对标签,位置和最低价进行赋值或解析
        for (HomestayListVO vo : voList) {
            vo.setTagList(commonService.parseTags(dictList, vo.getTagIds()));
            vo.setDetailAddress(sysAreaService.parseArea(vo.getCityId(), vo.getCountyId()) + vo.getDetailAddress());
            vo.setMinPrice(priceMap.getOrDefault(vo.getId(), 0));
        }
        return voList;
    }

    @Override
    public HomestayVO detailById(Long homestayId) {
        Homestay homestay = this.selectByIdShelve(homestayId);
        HomestayVO vo = DataUtil.copy(homestay, HomestayVO.class);
        vo.setDetailAddress(sysAreaService.parseArea(homestay.getCityId(), homestay.getCountyId()) + homestay.getDetailAddress());
        vo.setTagList(sysDictService.getTags(DictConstant.HOMESTAY_TAG, homestay.getTag()));
        vo.setRecommendRoomList(homestayRoomService.getRecommendRoom(homestayId));
        return vo;
    }

    /**
     * 校验民宿名称是否被占用
     * @param title 民宿名称
     * @param id    id
     */
    private void titleRedo(String title, Long id) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Homestay::getTitle, title);
        wrapper.ne(id != null, Homestay::getId, id);
        Integer count = homestayMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("民宿名称被占用 [{}]", title);
            throw new BusinessException(ErrorCode.HOMESTAY_TITLE_REDO);
        }
    }

}
