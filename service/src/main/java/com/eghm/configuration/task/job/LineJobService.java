package com.eghm.configuration.task.job;

import com.eghm.annotation.CronMark;
import com.eghm.service.business.LineConfigService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2025/5/20
 */

@Slf4j
@AllArgsConstructor
@Component("lineJobService")
public class LineJobService {

    private final LineConfigService lineConfigService;

    /**
     * 删除历史价格配置信息,减少查询时间
     */
    @CronMark
    public void deleteDayPrice(String args) {
        LoggerUtil.print(String.format("删除线路历史价格定时任务开始执行 [%s]", args));
        int keepDay = 7;
        if (isNotBlank(args)) {
            keepDay = Integer.parseInt(args);
        }
        lineConfigService.deletePrice(keepDay);
        LoggerUtil.print("删除线路历史价格定时任务执行完毕");
    }
}
