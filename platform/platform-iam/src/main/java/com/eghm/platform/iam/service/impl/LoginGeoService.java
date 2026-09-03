package com.eghm.platform.iam.service.impl;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.web.utility.IpRegionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 登录地域检查服务
 * 通过对比用户本次登录IP的地域与上次登录IP的地域, 检测异地登录行为
 * 地域信息基于IpRegionService离线IP库解析, 历史登录IP/地域存储在Redis中
 *
 * @author wyb-eghm
 * @since 2026/9/3
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginGeoService {
    
    private final CacheService cacheService;
    
    private final ApplicationProperties applicationProperties;
    
    private static final String LOGIN_IP_HISTORY_KEY = "login:geo:history:";
    
    private static final String LOGIN_REGION_HISTORY_KEY = "login:geo:region:";
    
    /**
     * 检查用户登录地域是否发生变化
     * 判断逻辑:
     * 1. 首次登录(无历史IP): 记录当前IP/地域, 返回安全
     * 2. 同一IP登录: 返回安全
     * 3. IP变化但地域相同: 记录新IP/地域, 返回IP变化(不阻断)
     * 4. IP变化且地域不同: 返回地域变化(需阻断, 触发异地登录验证)
     *
     * @param userId    用户ID
     * @param currentIp 当前登录IP
     * @return 地域检查结果
     */
    public GeoCheckResult checkGeoChange(Long userId, String currentIp) {
        ApplicationProperties.LoginSecurityProperties config = applicationProperties.getManage().getLoginSecurity();
        if (!config.isEnabled() || !config.isGeoCheckEnabled()) {
            return GeoCheckResult.ok();
        }
        String ipKey = LOGIN_IP_HISTORY_KEY + userId;
        String regionKey = LOGIN_REGION_HISTORY_KEY + userId;
        String lastIp = cacheService.getValue(ipKey);
        String lastRegion = cacheService.getValue(regionKey);
        String currentRegion = IpRegionUtil.getRegion(currentIp);
        if (lastIp == null) {
            this.recordLoginGeo(userId, currentIp, currentRegion);
            return GeoCheckResult.ok();
        }
        if (currentIp.equals(lastIp)) {
            return GeoCheckResult.ok();
        }
        if (lastRegion != null && currentRegion != null && !currentRegion.equals(lastRegion)) {
            log.warn("检测到异地登录 [userId:{}] [上次IP:{}] [上次地域:{}] [当前IP:{}] [当前地域:{}]", userId, lastIp, lastRegion, currentIp, currentRegion);
            return GeoCheckResult.geoChanged(lastIp, lastRegion, currentIp, currentRegion);
        }
        this.recordLoginGeo(userId, currentIp, currentRegion);
        return GeoCheckResult.ipChanged(lastIp, currentIp);
    }
    
    /**
     * 记录用户登录IP和地域到Redis, 用于后续异地登录检测
     * 过期时间90天, 超过90天未登录的用户视为首次登录
     *
     * @param userId 用户ID
     * @param ip     登录IP
     * @param region 登录地域(省份-城市)
     */
    public void recordLoginGeo(Long userId, String ip, String region) {
        String ipKey = LOGIN_IP_HISTORY_KEY + userId;
        String regionKey = LOGIN_REGION_HISTORY_KEY + userId;
        cacheService.setValue(ipKey, ip, 90, TimeUnit.DAYS);
        if (region != null) {
            cacheService.setValue(regionKey, region, 90, TimeUnit.DAYS);
        }
    }
    
    /**
     * 地域检查结果
     * safe=true 表示安全(允许登录), safe=false 表示需要阻断
     * geoChanged=true 表示地域发生变化(异地登录), ipChanged=true 表示仅IP变化(同地域)
     */
    @Data
    @AllArgsConstructor(staticName = "of")
    public static class GeoCheckResult {
        
        private boolean safe;
        
        private boolean geoChanged;
        
        private boolean ipChanged;
        
        private String lastIp;
        
        private String lastRegion;
        
        private String currentIp;
        
        private String currentRegion;
        
        public static GeoCheckResult ok() {
            return of(true, false, false, null, null, null, null);
        }
        
        public static GeoCheckResult geoChanged(String lastIp, String lastRegion, String currentIp, String currentRegion) {
            return of(false, true, false, lastIp, lastRegion, currentIp, currentRegion);
        }
        
        public static GeoCheckResult ipChanged(String lastIp, String currentIp) {
            return of(true, false, true, lastIp, null, currentIp, null);
        }
    }
}