package com.eghm.application.shared.cache;

import com.eghm.domain.shared.enums.Channel;
import com.eghm.enums.EmailType;
import com.eghm.domain.operate.model.EmailTemplate;
import com.eghm.domain.operate.model.SysNotice;
import com.eghm.domain.system.model.SysArea;
import com.eghm.domain.system.model.SysDictItem;
import com.eghm.application.shared.vo.operate.auth.AuthConfigVO;
import com.eghm.application.shared.vo.operate.banner.BannerVO;
import com.eghm.application.shared.vo.operate.template.NoticeTemplateResponse;
import com.eghm.application.shared.vo.sys.ext.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
public interface CacheProxyService {

    /**
     * 查询地址列表
     *
     * @return list
     */
    List<SysAreaVO> getAreaList();

    /**
     * 主键查询
     *
     * @param id id
     * @return 地区
     */
    SysArea getAreaById(Long id);

    /**
     * 根据客户端类型及模板获取轮播图信息
     *
     * @param channel    客户端类型
     * @param bannerType banner所属模块,数据字典的值
     * @return 轮播图列表
     */
    List<BannerVO> getBanner(Channel channel, Integer bannerType);

    /**
     * 根据邮件模板code获取
     *
     * @param code 模板code
     * @return 模板信息
     */
    EmailTemplate getEmailTemplate(EmailType code);

    /**
     * 查询站内信模板
     *
     * @param code code
     * @return template
     */
    NoticeTemplateResponse getNoticeTemplate(String code);

    /**
     * 获取公告前几条标题信息,具体多少条由系统参数notice_limit控制
     *
     * @param limit 显示的条数
     * @return 公告列表
     */
    List<SysNotice> getNoticeList(int limit);

    /**
     * 根据nid获取系统配置信息
     *
     * @param nid 唯一nid
     * @return 参数配置独享
     */
    String getConfigByNid(String nid);

    /**
     * 根据nid查询某一类数据字典列表
     *
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    List<SysDictItem> getDictByNid(String nid);

    /**
     * 根据appId查询第三方配置信息
     *
     * @param appId appId
     * @return 配置信息
     */
    AuthConfigVO getByAppId(String appId);

}
