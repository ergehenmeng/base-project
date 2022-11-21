package com.eghm.common.enums.event;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface IEvent {

    List<Integer> from();

    Integer to();
}
