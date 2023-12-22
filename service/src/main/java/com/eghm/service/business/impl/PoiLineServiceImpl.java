package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.PoiLineMapper;
import com.eghm.model.PoiLine;
import com.eghm.service.business.PoiLineService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.poi.PoiLineResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * poi线路表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */

@Slf4j
@AllArgsConstructor
@Service("poiLineService")
public class PoiLineServiceImpl implements PoiLineService {

    private final PoiLineMapper poiLineMapper;

    @Override
    public Page<PoiLineResponse> getByPage(PoiLineQueryRequest request) {
        return poiLineMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(PoiLineAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        PoiLine poiLine = DataUtil.copy(request, PoiLine.class);
        poiLineMapper.insert(poiLine);
    }

    @Override
    public void update(PoiLineEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        PoiLine poiLine = DataUtil.copy(request, PoiLine.class);
        poiLineMapper.updateById(poiLine);
    }

    @Override
    public void deleteById(Long id) {
        poiLineMapper.deleteById(id);
    }

    /**
     * 校验线路是否重复
     *
     * @param title 线路名称
     * @param id 线路id
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<PoiLine> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PoiLine::getTitle, title);
        wrapper.ne(id != null, PoiLine::getId, id);
        Long count = poiLineMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.POI_LINE_REDO);
        }
    }
}
