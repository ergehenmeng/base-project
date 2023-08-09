package com.eghm.service.business.impl;

import com.eghm.constant.CommonConstant;
import com.eghm.mapper.ItemTagMapper;
import com.eghm.service.business.ItemTagService;
import com.eghm.vo.business.item.ItemTagResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 零售标签 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-08
 */
@Slf4j
@AllArgsConstructor
@Service("itemTagService")
public class ItemTagServiceImpl implements ItemTagService {

    private final ItemTagMapper itemTagMapper;

    @Override
    public List<ItemTagResponse> getList() {
        List<ItemTagResponse> responseList = itemTagMapper.getList();
        return responseList
                .stream()
                .filter(response -> CommonConstant.PARENT.equals(response.getPid()))
                .peek(response -> this.setChildren(response, responseList))
                .collect(Collectors.toList());
    }

    /**
     * 递归设置子节点信息
     * @param parent 父节点
     * @param responseList 所有节点
     */
    private void setChildren(ItemTagResponse parent, List<ItemTagResponse> responseList) {
        parent.setChildren(responseList.stream()
                .filter(response -> parent.getId().equals(response.getPid()))
                .peek(response -> this.setChildren(response, responseList))
                .collect(Collectors.toList()));
    }

}
