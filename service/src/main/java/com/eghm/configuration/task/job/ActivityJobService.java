package com.eghm.configuration.task.job;

import com.eghm.annotation.CronMark;
import com.eghm.service.business.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @since 2025/6/9
 */

@Slf4j
@AllArgsConstructor
@Component("activityJobService")
public class ActivityJobService {

    private final ItemService itemService;

    @CronMark
    public void clearExpiredActivity() {
        itemService.clearExpiredActivity();
    }
}
