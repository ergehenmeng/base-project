package com.eghm.repository.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.UserState;
import com.eghm.mapper.SysUserMapper;
import com.eghm.po.SysUserPO;
import com.eghm.sys.model.SysUser;
import com.eghm.sys.repository.SysUserRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * MyBatis系统用户仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysUserRepository implements SysUserRepository {

    private final SysUserMapper sysUserMapper;

    @Override
    public boolean existsUserName(String userName, Long excludeId) {
        LambdaQueryWrapper<SysUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserPO::getUserName, userName);
        if (excludeId != null) {
            wrapper.ne(SysUserPO::getId, excludeId);
        }
        return sysUserMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsMobile(String mobile, Long excludeId) {
        LambdaQueryWrapper<SysUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserPO::getMobile, mobile);
        if (excludeId != null) {
            wrapper.ne(SysUserPO::getId, excludeId);
        }
        return sysUserMapper.selectCount(wrapper) > 0;
    }

    @Override
    public SysUser findById(Long id) {
        return DataUtil.copy(sysUserMapper.selectById(id), SysUser.class);
    }

    @Override
    public SysUser findByMobile(String mobile) {
        return findOne(SysUserPO::getMobile, mobile);
    }

    @Override
    public SysUser findByUserName(String userName) {
        return findOne(SysUserPO::getUserName, userName);
    }

    @Override
    public SysUser findByOpenId(String openId) {
        return findOne(SysUserPO::getOpenId, openId);
    }

    @Override
    public void save(SysUser user) {
        SysUserPO userPO = DataUtil.copy(user, SysUserPO.class);
        sysUserMapper.insert(userPO);
        user.setId(userPO.getId());
    }

    @Override
    public void update(SysUser user) {
        sysUserMapper.updateById(DataUtil.copy(user, SysUserPO.class));
    }

    @Override
    public void updatePassword(Long id, String password, LocalDateTime pwdUpdateTime) {
        SysUserPO user = new SysUserPO();
        user.setId(id);
        user.setPwd(password);
        user.setPwdUpdateTime(pwdUpdateTime);
        sysUserMapper.updateById(user);
    }

    @Override
    public void resetPassword(Long id, String password, LocalDateTime pwdUpdateTime) {
        SysUserPO user = new SysUserPO();
        user.setId(id);
        user.setPwd(password);
        user.setInitPwd(password);
        user.setPwdUpdateTime(pwdUpdateTime);
        sysUserMapper.updateById(user);
    }

    @Override
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public void updateState(Long id, UserState state) {
        SysUserPO user = new SysUserPO();
        user.setId(id);
        user.setState(state);
        sysUserMapper.updateById(user);
    }

    @Override
    public void updateOpenId(Long id, String openId) {
        SysUserPO user = new SysUserPO();
        user.setId(id);
        user.setOpenId(openId);
        sysUserMapper.updateById(user);
    }

    @Override
    public void clearOpenId(Long id) {
        LambdaUpdateWrapper<SysUserPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUserPO::getId, id);
        wrapper.set(SysUserPO::getOpenId, null);
        sysUserMapper.update(null, wrapper);
    }

    @Override
    public void updateTotpSecret(Long id, String secret) {
        SysUserPO user = new SysUserPO();
        user.setId(id);
        user.setTotpSecret(secret);
        sysUserMapper.updateById(user);
    }

    @Override
    public void clearTotpSecret(Long id) {
        LambdaUpdateWrapper<SysUserPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUserPO::getId, id);
        wrapper.set(SysUserPO::getTotpSecret, null);
        sysUserMapper.update(null, wrapper);
    }

    @Override
    public void updateAvatar(Long id, String avatar) {
        LambdaUpdateWrapper<SysUserPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUserPO::getId, id);
        wrapper.set(SysUserPO::getAvatar, avatar);
        sysUserMapper.update(null, wrapper);
    }

    @Override
    public void updateProfile(Long id, String nickName, String mobile) {
        LambdaUpdateWrapper<SysUserPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUserPO::getId, id);
        wrapper.set(SysUserPO::getNickName, nickName);
        wrapper.set(SysUserPO::getMobile, mobile);
        sysUserMapper.update(null, wrapper);
    }

    private <T> SysUser findOne(com.baomidou.mybatisplus.core.toolkit.support.SFunction<SysUserPO, T> column, T value) {
        LambdaQueryWrapper<SysUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(column, value);
        wrapper.last("limit 1");
        return DataUtil.copy(sysUserMapper.selectOne(wrapper), SysUser.class);
    }
}
