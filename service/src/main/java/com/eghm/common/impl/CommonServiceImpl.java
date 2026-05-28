package com.eghm.common.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.eghm.cache.CacheProxyService;
import com.eghm.cache.CacheService;
import com.eghm.common.CommonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysAreaMapper;
import com.eghm.vo.sys.ext.SysAreaVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.eghm.utils.TreeUtil;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Slf4j
@AllArgsConstructor
@Service("commonService")
public class CommonServiceImpl implements CommonService {
    
    private volatile RSA rsaInstance;

    private final CacheService cacheService;

    private final SysAreaMapper sysAreaMapper;
    
    private final SystemProperties systemProperties;

    private final CacheProxyService cacheProxyService;

    @Override
    public List<SysAreaVO> getTreeAreaList() {
        List<SysAreaVO> areaList = cacheProxyService.getAreaList();
        return TreeUtil.tree(areaList, CommonConstant.ROOT, SysAreaVO::getId, SysAreaVO::getPid, SysAreaVO::setChildren);
    }

    @Override
    public List<SysAreaVO> getTreeAreaList(List<Integer> gradeList) {
        List<SysAreaVO> areaList = sysAreaMapper.getList(gradeList);
        return TreeUtil.tree(areaList, CommonConstant.ROOT, SysAreaVO::getId, SysAreaVO::getPid, SysAreaVO::setChildren);
    }

    @Override
    public void praise(String key, String hashKey, Consumer<Boolean> consumer) {
        boolean praise = cacheService.getHashValue(key, hashKey) == null;
        if (praise) {
            cacheService.setHashValue(key, hashKey, CacheConstant.PLACE_HOLDER);
        } else {
            cacheService.deleteHashKey(key, hashKey);
        }
        consumer.accept(praise);
    }

    @Override
    public String rsaDecrypt(String rsaStr) {
        return getRsaInstance().decryptStr(rsaStr, KeyType.PrivateKey);
    }

    @Override
    public void savePermission(String token, List<String> permList) {
        cacheService.setValue(CacheConstant.USER_PERMISSION + token, permList, systemProperties.getManage().getToken().getExpire());
    }

    @Override
    public List<String> getPermission(String token) {
        return cacheService.getList(CacheConstant.USER_PERMISSION + token, String.class);
    }

    @Override
    public void clearPermission(String token) {
        cacheService.delete(CacheConstant.USER_PERMISSION + token);
    }

    @Override
    public String generateNextId(String maxId, String pid, int step, ErrorCode errorCode) {
        // 空表示当前菜单没有子菜单,直接生成第一个子菜单
        if (isBlank(maxId)) {
            return pid + step;
        }
        // 如果最后三位是99这表示,已经最大了,再+1会进位,因此不能超过99
        String lastMember = maxId.substring(maxId.length() - 2);
        if (Integer.parseInt(lastMember) >= this.getMax(step)) {
            log.error("generateNextId已超过最大值 [{}] [{}]", pid, maxId);
            throw new BusinessException(errorCode);
        }
        BigInteger max = new BigInteger(maxId);
        return max.add(BigInteger.valueOf(1L)).toString();
    }
    
    /**
     * 获取 rsa 实例
     *
     * @return rsa 实例
     */
    private RSA getRsaInstance() {
        if (rsaInstance == null) {
            synchronized (this) {
                if (rsaInstance == null) {
                    rsaInstance = SecureUtil.rsa(
                            systemProperties.getPrivateKey(),
                            systemProperties.getPublicKey()
                    );
                }
            }
        }
        return rsaInstance;
    }
    
    /**
     * 获取最大值 10 100 1000 10000 100000 -> 99 999 9999 99999 9999999
     *
     * @param step 步长
     * @return 最大值
     */
    private int getMax(int step) {
        return Integer.parseInt("9".repeat(String.valueOf(step).length()));
    }
}
