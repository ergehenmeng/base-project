package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.UserNotice;

/**
 * @author 二哥很猛
 */
public interface UserNoticeMapper extends BaseMapper<UserNotice> {

    /**
     * 更新 delete read字段
     * @param notice  不为空更新
     * @return
     */
    int updateNotice(UserNotice notice);

}