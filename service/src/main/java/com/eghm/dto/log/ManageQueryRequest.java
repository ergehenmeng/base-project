package com.eghm.dto.log;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2019/1/16 9:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ManageQueryRequest extends PagingQuery implements Serializable {

    private static final long serialVersionUID = 7717698795068820383L;

    @ApiModelProperty("开始时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间 yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

}
