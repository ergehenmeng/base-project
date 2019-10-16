package com.fanyin.model.vo.mapper;

import com.fanyin.model.vo.login.LoginToken;
import com.fanyin.model.vo.login.LoginTokenPo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author 二哥很猛
 * @date 2019/10/15 9:48
 */
@Mapper
public interface CarMapper {

    @Mapping(source = "accessKey",target = "accessKey")
    @Mapping(source = "accessToken",target = "accessToken")
    @BeanMapping(ignoreByDefault = true)
    LoginToken transfer(LoginTokenPo tokenPo);
}
