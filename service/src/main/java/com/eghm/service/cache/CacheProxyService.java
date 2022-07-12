package com.eghm.service.cache;

import com.eghm.common.enums.Channel;
import com.eghm.common.enums.EmailType;
import com.eghm.dao.model.*;
import com.eghm.model.dto.banner.BannerAddRequest;
import com.eghm.model.dto.banner.BannerEditRequest;
import com.eghm.model.vo.sys.SysAreaVO;
import com.eghm.service.pay.enums.MerchantType;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface CacheProxyService {

    /**
     * 黑名单列表
     * @return 列表 ip与结束时间
     */
    List<BlackRoster> getBlackRosterList();

    /**
     * 查询子级的地址列表
     * @param pid 当前级地址id
     * @return list
     */
    List<SysArea> getAreaByPid(Long pid);

    /**
     * 主键查询
     * @param id id
     * @return 地区
     */
    SysArea getAreaById(Long id);

    /**
     * 根据客户端类型及模板获取轮播图信息
     * @param channel 客户端类型
     * @param classify banner所属模块,数据字典的值
     * @return 轮播图列表
     */
    List<Banner> getBanner(Channel channel, Byte classify);

    /**
     * 新增轮播图信息
     * @param request 前台参数
     */
    void createBanner(BannerAddRequest request);

    /**
     * 编辑保存轮播图信息
     * @param request 前台参数
     */
    void updateBanner(BannerEditRequest request);

    /**
     * 根据邮件模板code获取
     * @param code 模板code
     * @return 模板信息
     */
    EmailTemplate getEmailTemplate(EmailType code);

    /**
     * 查询站内信模板
     * @param code code
     * @return template
     */
    NoticeTemplate getNoticeTemplate(String code);

    /**
     * 获取推送消息模板
     * @param nid nid
     * @return 模板消息
     */
    PushTemplate getPushTemplate(String nid);

    /**
     * 获取公告前几条标题信息,具体多少条由系统参数notice_limit控制
     * @param limit 显示的条数
     * @return 公告列表
     */
    List<SysNotice> getNoticeList(int limit);

    /**
     * 发布公告
     * @param id id主键
     */
    void publishNotice(Long id);

    /**
     * 取消发布
     * @param id 主键
     */
    void cancelPublishNotice(Long id);

    /**
     * 获取短信发送模板
     * @param nid nid
     * @return 短信内容模板
     */
    String getSmsTemplate(String nid);

    /**
     * 根据nid获取系统配置信息
     * @param nid 唯一nid
     * @return 参数配置独享
     */
    String getConfigByNid(String nid);

    /**
     * 根据nid查询某一类数据字典列表
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    List<SysDict> getDictByNid(String nid);

    /**
     * 根据商户code查询小程序配置消息
     * @param type type
     * @return 配置信息
     */
    AppletPayConfig getPayByNid(MerchantType type);
}
