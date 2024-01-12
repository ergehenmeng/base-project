package com.eghm.service.business.impl;

import com.eghm.mapper.CommentMapper;
import com.eghm.service.business.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */

@Slf4j
@AllArgsConstructor
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
}
