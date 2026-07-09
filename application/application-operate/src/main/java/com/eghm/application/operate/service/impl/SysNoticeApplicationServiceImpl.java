package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.operate.notice.NoticeAddRequest;
import com.eghm.application.shared.dto.operate.notice.NoticeEditRequest;
import com.eghm.application.shared.dto.operate.notice.NoticeQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.SysNotice;
import com.eghm.domain.operate.repository.SysNoticeRepository;
import com.eghm.application.operate.query.SysNoticeQueryService;
import com.eghm.application.operate.service.SysNoticeApplicationService;
import com.eghm.application.system.service.SysDictApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.notice.NoticeDetailVO;
import com.eghm.application.shared.vo.operate.notice.NoticeResponse;
import com.eghm.application.shared.vo.operate.notice.NoticeTopVO;
import com.eghm.application.shared.vo.operate.notice.NoticeVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/22 11:41
 */
@Slf4j
@AllArgsConstructor
@Service("sysNoticeService")
public class SysNoticeApplicationServiceImpl implements SysNoticeApplicationService {

    private final SysConfigApi sysConfigApi;

    private final SysDictApplicationService sysDictService;

    private final SysNoticeRepository sysNoticeRepository;

    private final SysNoticeQueryService sysNoticeQueryGateway;

    private final CacheProxyService cacheProxyService;

    @Override
    public Page<NoticeResponse> getByPage(NoticeQueryRequest request) {
        return sysNoticeQueryGateway.getByPage(request.createPage(), request);
    }

    @Override
    public List<NoticeTopVO> getTop() {
        int noticeLimit = sysConfigApi.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = cacheProxyService.getNoticeList(noticeLimit);
        return DataUtil.copy(noticeList, notice -> {
            NoticeTopVO vo = DataUtil.copy(notice, NoticeTopVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()) + ": " + vo.getTitle());
            return vo;
        });
    }

    @Override
    public List<NoticeVO> getList(PagingQuery query) {
        return sysNoticeQueryGateway.getList(query);
    }

    @Override
    public void create(NoticeAddRequest request) {
        SysNotice notice = new SysNotice();
        notice.initialize(request.getTitle(), request.getNoticeType(), request.getCoverUrl(), request.getContent());
        sysNoticeRepository.save(notice);
    }

    @Override
    public void update(NoticeEditRequest request) {
        SysNotice notice = this.getByIdRequired(request.getId());
        notice.change(request.getTitle(), request.getNoticeType(), request.getCoverUrl(), request.getContent());
        sysNoticeRepository.update(notice);
    }

    @Override
    public void delete(Long id) {
        sysNoticeRepository.deleteById(id);
    }

    @Override
    public NoticeDetailVO detailById(Long id) {
        SysNotice notice = sysNoticeRepository.findById(id);
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
        SysNotice notice = sysNoticeRepository.findById(id);
        if (notice == null) {
            log.info("公告信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        }
        return notice;
    }

    @Override
    public void publish(Long id) {
        SysNotice notice = this.getByIdRequired(id);
        notice.publish();
        sysNoticeRepository.update(notice);
    }

    @Override
    public void cancelPublish(Long id) {
        SysNotice notice = this.getByIdRequired(id);
        notice.unpublish();
        sysNoticeRepository.update(notice);
    }
}
