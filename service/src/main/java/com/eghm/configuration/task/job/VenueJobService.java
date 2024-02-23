package com.eghm.configuration.task.job;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.annotation.CronMark;
import com.eghm.model.VenueSitePrice;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/23
 */

@Slf4j
@AllArgsConstructor
@Component("venueJobService")
public class VenueJobService {

    private final VenueSitePriceService venueSitePriceService;

    /**
     * 删除历史价格,减少查询时间
     */
    @CronMark
    public void deleteSitePrice(String args) {
        LoggerUtil.print("删除场馆历史价格定时任务开始执行 [{}]", args);
        int keepDay = 7;
        if (StrUtil.isNotBlank(args)) {
            keepDay = Integer.parseInt(args);
        }
        LocalDate localDate = LocalDate.now().minusDays(keepDay);
        LambdaUpdateWrapper<VenueSitePrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.lt(VenueSitePrice::getNowDate, localDate);
        venueSitePriceService.remove(wrapper);
        LoggerUtil.print("删除场馆历史价格定时任务执行完毕");
    }
}
