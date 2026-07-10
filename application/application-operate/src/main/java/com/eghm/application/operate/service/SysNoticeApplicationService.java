package com.eghm.application.operate.service;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.application.shared.dto.operate.notice.NoticeAddRequest;
import com.eghm.application.shared.dto.operate.notice.NoticeEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.operate.notice.NoticeDetailVO;
import com.eghm.application.shared.vo.operate.notice.NoticeTopVO;
import com.eghm.application.system.service.SysDictApplicationService;
import com.eghm.constants.ConfigConstant;
import com.eghm.constants.DictConstant;
import com.eghm.domain.operate.model.SysNotice;
import com.eghm.domain.operate.repository.SysNoticeRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:11
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysNoticeApplicationService {

    private final SysConfigService sysConfigService;

    private final SysDictApplicationService sysDictService;

    private final SysNoticeRepository sysNoticeRepository;

    private final CacheProxyService cacheProxyService;

    /**
     * 获取公告前几条标题信息,具体多少条由系统参数notice_limit控制
     *
     * @return 公告列表
     */
    public List<NoticeTopVO> getTop() {
        int noticeLimit = sysConfigService.getInt(ConfigConstant.NOTICE_LIMIT);
        List<SysNotice> noticeList = cacheProxyService.getNoticeList(noticeLimit);
        return DataUtil.copy(noticeList, notice -> {
            NoticeTopVO vo = DataUtil.copy(notice, NoticeTopVO.class);
            // 将公告类型包含到标题中 例如 紧急通知: 中印发生小规模冲突
            vo.setTitle(sysDictService.getDictValue(DictConstant.NOTICE_TYPE, notice.getNoticeType()) + ": " + vo.getTitle());
            return vo;
        });
    }

    /**
     * 添加公告
     *
     * @param request 前台参数
     */
    public void create(NoticeAddRequest request) {
        SysNotice notice = new SysNotice();
        notice.initialize(request.getTitle(), request.getNoticeType(), request.getCoverUrl(), request.getContent());
        sysNoticeRepository.save(notice);
    }

    /**
     * 更新公告
     *
     * @param request 前台参数
     */
    public void update(NoticeEditRequest request) {
        SysNotice notice = this.getByIdRequired(request.getId());
        notice.change(request.getTitle(), request.getNoticeType(), request.getCoverUrl(), request.getContent());
        sysNoticeRepository.update(notice);
    }

    /**
     * 删除公告
     *
     * @param id 公告id
     */
    public void delete(Long id) {
        sysNoticeRepository.deleteById(id);
    }

    /**
     * 查询公告详情
     *
     * @param id id
     * @return 详细信息
     */
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

    /**
     * 主键查询公告信息
     *
     * @param id id
     * @return 公告信息
     */
    public SysNotice getByIdRequired(Long id) {
        SysNotice notice = sysNoticeRepository.findById(id);
        if (notice == null) {
            log.info("公告信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        }
        return notice;
    }

    /**
     * 发布公告
     *
     * @param id id主键
     */
    public void publish(Long id) {
        SysNotice notice = this.getByIdRequired(id);
        notice.publish();
        sysNoticeRepository.update(notice);
    }

    /**
     * 取消发布
     *
     * @param id 主键
     */
    public void cancelPublish(Long id) {
        SysNotice notice = this.getByIdRequired(id);
        notice.unpublish();
        sysNoticeRepository.update(notice);
    }
}
