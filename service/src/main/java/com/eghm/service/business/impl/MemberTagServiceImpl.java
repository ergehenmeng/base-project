package com.eghm.service.business.impl;

import com.eghm.mapper.MemberTagMapper;
import com.eghm.service.business.MemberTagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员标签 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-06
 */

@Slf4j
@AllArgsConstructor
@Service("memberTagService")
public class MemberTagServiceImpl implements MemberTagService {

    private final MemberTagMapper memberTagMapper;
}
