package com.eghm.configuration.task.job;

import cn.hutool.core.util.StrUtil;
import com.eghm.configuration.annotation.CronMark;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @since 2024/2/23
 */

@Slf4j
@AllArgsConstructor
@Component("homestayJobService")
public class HomestayJobService {

    private final HomestayRoomConfigService homestayRoomConfigService;

    /**
     * 删除历史价格配置信息,减少查询时间
     */
    @CronMark
    public void deleteDayPrice(String args) {
        LoggerUtil.print("删除民宿历史价格定时任务开始执行 [{}]", args);
        int keepDay = 7;
        if (StrUtil.isNotBlank(args)) {
            keepDay = Integer.parseInt(args);
        }
        homestayRoomConfigService.deletePrice(keepDay);
        LoggerUtil.print("删除民宿历史价格定时任务执行完毕");
    }
}
