package com.eghm.configuration.task.job;

import com.eghm.annotation.CronMark;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ExchangeQueue;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mq.service.MessageService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/7/29
 */
@AllArgsConstructor
@Service("orderEvaluationJobService")
public class OrderEvaluationJobService {

    private final MessageService messageService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    @CronMark
    public void evaluation() {
        LoggerUtil.print("商品评分定时任务开始执行");
        LocalDate endDate = LocalDate.now();
        List<CalcStatistics> statisticsedList = orderEvaluationMapper.statisticsList(endDate.minusDays(1), endDate);
        for (CalcStatistics statistics : statisticsedList) {
            messageService.send(ExchangeQueue.PRODUCT_SCORE, statistics);
        }
        LoggerUtil.print("商品评分定时任务执行完毕");
    }
}
