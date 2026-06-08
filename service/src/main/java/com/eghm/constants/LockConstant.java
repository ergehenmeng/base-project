package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2024/9/13
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockConstant {
    
    /**
     * 所有锁前缀
     */
    public static final String PREFIX_LOCK = "lock:";

    /**
     * 互斥锁
     */
    public static final String MUTEX_LOCK = "mutex:";
    
    /**
     * 菜单锁
     */
    public static final String MENU_LOCK = "menu";
    
}
