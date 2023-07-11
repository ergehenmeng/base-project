package com.eghm.enums;

import com.eghm.enums.ref.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户与角色映射枚举
 *
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum MerchantRoleMapping {

    /**
     * 景区
     */
    SCENIC(1, RoleType.SCENIC),

    /**
     * 民宿
     */
    HOMESTAY(2, RoleType.HOMESTAY),

    /**
     * 餐饮
     */
    RESTAURANT(4, RoleType.RESTAURANT),

    /**
     * 特产
     */
    SPECIALTY(8, RoleType.SPECIALTY),

    /**
     * 线路
     */
    LINE(16, RoleType.LINE),

    ;

    /**
     * 商户类型
     */
    private final int merchantType;

    /**
     * 商户对应的角色
     */
    private final RoleType roleType;

    /**
     * 根据商户类型查询商户拥有的角色code
     * @param merchantType 商户类型,
     * @return 角色列表
     */
    public static List<RoleType> parseRoleType(Integer merchantType) {
        return Arrays.stream(MerchantRoleMapping.values())
                .filter(map -> (map.getMerchantType() & merchantType) == map.getMerchantType())
                .map(MerchantRoleMapping::getRoleType).collect(Collectors.toList());
    }

}
