package com.eghm.service.operate.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheProxyService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.notice.NoticeAddRequest;
import com.eghm.dto.operate.notice.NoticeEditRequest;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysNoticeMapper;
import com.eghm.model.SysNotice;
import com.eghm.service.operate.SysNoticeService;
import com.eghm.service.sys.SysDictService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.notice.NoticeDetailVO;
import com.eghm.vo.operate.notice.NoticeResponse;
import com.eghm.vo.operate.notice.NoticeVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/22 11:41
 */
@Service("sysNoticeService")
@AllArgsConstructor
@Slf4j
public class SysNoticeServiceImpl implements SysNoticeService {

    private final SysConfigApi sysConfigApi;

    private final SysDictService sysDictService;

    private final SysNoticeMapper sysNoticeMapper;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<NoticeResponse> getByPage(NoticeQueryRequest request) {
        return sysNoticeMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<NoticeVO> getList() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = cacheProxyService.getNoticeList(noticeLimit);
        return DataUtil.copy(noticeList, notice -> {
            NoticeVO vo = DataUtil.copy(notice, NoticeVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    public List<NoticeVO> getList(PagingQuery query) {
        LambdaQueryWrapper<SysNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysNotice::getId, SysNotice::getTitle);
        wrapper.eq(SysNotice::getState, true);
        wrapper.orderByDesc(SysNotice::getId);
        Page<SysNotice> selectedPage = sysNoticeMapper.selectPage(query.createPage(false), wrapper);
        return DataUtil.copy(selectedPage.getRecords(), NoticeVO.class);
    }

    @Override
    public void create(NoticeAddRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.insert(notice);
    }

    @Override
    public void update(NoticeEditRequest request) {
        SysNotice notice = DataUtil.copy(request, SysNotice.class);
        sysNoticeMapper.updateById(notice);
    }

    @Override
    public void delete(Long id) {
        sysNoticeMapper.deleteById(id);
    }

    @Override
    public NoticeDetailVO detailById(Long id) {
        SysNotice notice = sysNoticeMapper.selectById(id);
        if (notice == null) {
            log.info("公告信息已删除 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_NULL);
        }
        NoticeDetailVO vo = DataUtil.copy(notice, NoticeDetailVO.class);
        vo.setNoticeType(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()));
        return vo;
    }

    @Override
    public SysNotice getByIdRequired(Long id) {
        SysNotice notice = sysNoticeMapper.selectById(id);
        if (notice == null) {
            log.info("公告信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        }
        return notice;
    }

    @Override
    public void publish(Long id) {
        SysNotice notice = new SysNotice();
        notice.setState(1);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }

    @Override
    public void cancelPublish(Long id) {
        SysNotice notice = new SysNotice();
        notice.setState(SysNotice.STATE_0);
        notice.setId(id);
        sysNoticeMapper.updateById(notice);
    }
}
