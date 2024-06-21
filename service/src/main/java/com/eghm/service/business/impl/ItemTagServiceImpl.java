package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.item.ItemTagAddRequest;
import com.eghm.dto.business.item.ItemTagEditRequest;
import com.eghm.dto.business.item.ItemTagQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemMapper;
import com.eghm.mapper.ItemTagMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemTag;
import com.eghm.service.business.ItemTagService;
import com.eghm.utils.DataUtil;
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

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";
    /**
     * 最大层级是5级, 每一级三位长度的数字
     */
    private static final int MAX_TAG_DEPTH = 15;
    private final ItemTagMapper itemTagMapper;
    private final ItemMapper itemMapper;

    @Override
    public void create(ItemTagAddRequest request) {
        String nextId = this.getNextId(request.getPid());
        if (nextId.length() > MAX_TAG_DEPTH) {
            log.warn("标签层级太深,无法继续创建 [{}] [{}]", nextId, MAX_TAG_DEPTH);
            throw new BusinessException(ErrorCode.TAG_DEPTH);
        }
        ItemTag tag = DataUtil.copy(request, ItemTag.class);
        tag.setId(nextId);
        itemTagMapper.insert(tag);
    }

    @Override
    public void update(ItemTagEditRequest request) {
        ItemTag tag = DataUtil.copy(request, ItemTag.class);
        itemTagMapper.updateById(tag);
    }

    @Override
    public void deleteById(Long id) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Item::getTagId, id);
        Long count = itemMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("标签已被零售商品占用, 无法直接删除 [{}]", id);
            throw new BusinessException(ErrorCode.TAG_USED);
        }
        itemTagMapper.deleteById(id);
    }

    @Override
    public void sortBy(String id, Integer sortBy) {
        LambdaUpdateWrapper<ItemTag> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemTag::getId, id);
        wrapper.set(ItemTag::getSort, sortBy);
        itemTagMapper.update(null, wrapper);
    }

    @Override
    public List<ItemTagResponse> getList(ItemTagQueryRequest request) {
        List<ItemTagResponse> responseList = itemTagMapper.getList(request);
        return this.treeBin(CommonConstant.ROOT_NODE, responseList);
    }

    /**
     * 根据列表计算出子级编号
     * 初始编号默认100,后面依次累计+1
     *
     * @param pid 节点id
     * @return 下一个编号
     */
    private String getNextId(String pid) {
        String maxCode = itemTagMapper.getChildMaxId(pid);
        if (maxCode == null) {
            return CommonConstant.ROOT_NODE.equals(pid) ? STEP : pid + STEP;
        }
        // 不能超过900个标签
        try {
            return String.valueOf(Long.parseLong(maxCode) + 1);
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.ITEM_TAG_NULL);
        }
    }

    /**
     * 递归设置子节点信息
     *
     * @param pid          pid
     * @param responseList 所有节点
     */
    private List<ItemTagResponse> treeBin(String pid, List<ItemTagResponse> responseList) {
        return responseList.stream()
                .filter(response -> pid.equals(response.getPid()))
                .peek(response -> response.setChildren(this.treeBin(response.getId(), responseList)))
                .collect(Collectors.toList());
    }

}
