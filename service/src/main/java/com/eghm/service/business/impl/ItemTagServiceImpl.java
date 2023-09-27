package com.eghm.service.business.impl;

import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.item.ItemTagAddRequest;
import com.eghm.dto.business.item.ItemTagEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemTagMapper;
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

    private final ItemTagMapper itemTagMapper;

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";

    @Override
    public void create(ItemTagAddRequest request) {
        ItemTag tag = DataUtil.copy(request, ItemTag.class);
        tag.setId(this.getNextId(request.getPid()));
        itemTagMapper.insert(tag);
    }

    @Override
    public void update(ItemTagEditRequest request) {
        ItemTag tag = DataUtil.copy(request, ItemTag.class);
        itemTagMapper.updateById(tag);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ItemTagResponse> getList() {
        List<ItemTagResponse> responseList = itemTagMapper.getList();
        return responseList
                .stream()
                .filter(response -> CommonConstant.ROOT_NODE.equals(response.getPid()))
                .peek(response -> this.setChildren(response, responseList))
                .collect(Collectors.toList());
    }

    /**
     * 根据列表计算出子级编号
     * 初始编号默认100,后面依次累计+1
     * @param pid 节点id
     * @return 下一个编号
     */
    private String getNextId(String pid) {
        String maxCode = itemTagMapper.getChildMaxId(pid);
        if (maxCode == null) {
            return CommonConstant.ROOT_NODE.equals(pid) ? STEP :  pid + STEP;
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
