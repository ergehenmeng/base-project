package com.eghm.sys.model;

import com.eghm.common.model.BaseEntity;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.stream.LongStream;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlackRoster extends BaseEntity {

    /** 访问ip */
    private Long startIp;

    /** 数字ip */
    private Long endIp;

    /** 备注信息 */
    private String remark;

    /**
     * 校验ip范围是否合法
     */
    public void assertRangeValid() {
        if (startIp > endIp) {
            throw new BusinessException(ErrorCode.IP_RANGE_ILLEGAL);
        }
    }

    /**
     * 转换为缓存中的ip集合
     *
     * @return ip数字字符串集合
     */
    public String[] toCacheValues() {
        return LongStream.range(startIp, endIp + 1).mapToObj(String::valueOf).toArray(String[]::new);
    }
}
