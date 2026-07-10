package com.eghm.application.system.service;

import com.eghm.application.shared.cache.CacheProxyService;
import com.eghm.application.shared.dto.sys.dict.DictAddRequest;
import com.eghm.application.shared.dto.sys.dict.DictEditRequest;
import com.eghm.application.shared.dto.sys.dict.DictItemAddRequest;
import com.eghm.application.shared.dto.sys.dict.DictItemEditRequest;
import com.eghm.application.shared.dto.sys.dict.DictQueryRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.query.SysDictQueryService;
import com.eghm.domain.system.model.SysDict;
import com.eghm.domain.system.model.SysDictItem;
import com.eghm.domain.system.repository.SysDictItemRepository;
import com.eghm.domain.system.repository.SysDictRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.sys.dict.BaseDictResponse;
import com.eghm.application.shared.vo.sys.dict.BaseItemVO;
import com.eghm.application.shared.vo.sys.dict.DictResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 数据字典服务类
 *
 * @author 二哥很猛
 * @since 2018/1/12 14:31
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysDictApplicationService {

    private final SysDictRepository sysDictRepository;

    private final SysDictItemRepository sysDictItemRepository;

    private final SysDictQueryService sysDictQueryService;

    private final CacheProxyService cacheProxyService;

    /**
     * 根据条件查询数据字典信息(不分页)
     *
     * @param request 前台条件
     * @return 列表
     */
    public List<DictResponse> getList(DictQueryRequest request) {
        return sysDictQueryService.getList(request);
    }

    /**
     * 根据nid查询某一类数据字典列表
     *
     * @param nid 某一类数据字典key
     * @return 属于该nid的列表
     */
    public List<SysDictItem> getDictByNid(String nid) {
        return cacheProxyService.getDictByNid(nid);
    }

    /**
     * 根据nid列表查询基础字典响应.
     *
     * @param nidList 字典编码列表
     * @return 字典响应列表
     */
    public List<BaseDictResponse> getBaseDictList(List<String> nidList) {
        return nidList.stream().map(nid -> {
            List<SysDictItem> dictList = this.getDictByNid(nid);
            List<BaseItemVO> itemList = DataUtil.copy(dictList, BaseItemVO.class);
            BaseDictResponse response = new BaseDictResponse();
            response.setItemList(itemList);
            response.setNid(nid);
            return response;
        }).toList();
    }

    /**
     * 添加数据字典
     *
     * @param request 前台参数
     */
    public void create(DictAddRequest request) {
        if (sysDictRepository.existsTitle(request.getTitle(), null)) {
            log.warn("数据字典名称重复 [{}]", request.getTitle());
            throw new BusinessException(ErrorCode.DICT_REPEAT_ERROR);
        }
        if (sysDictRepository.existsNid(request.getNid())) {
            log.warn("数据字典编号重复 [{}]", request.getNid());
            throw new BusinessException(ErrorCode.DICT_NID_REPEAT_ERROR);
        }
        sysDictRepository.save(DataUtil.copy(request, SysDict.class));
    }

    /**
     * 编辑数据字典
     *
     * @param request 前台参数
     */
    public void update(DictEditRequest request) {
        if (sysDictRepository.existsTitle(request.getTitle(), request.getId())) {
            log.warn("数据字典名称重复 [{}] [{}]", request.getId(), request.getTitle());
            throw new BusinessException(ErrorCode.DICT_REPEAT_ERROR);
        }
        sysDictRepository.update(DataUtil.copy(request, SysDict.class));
    }

    /**
     * 删除数据字典
     *
     * @param id 主键
     */
    public void delete(Long id) {
        SysDict sysDict = sysDictRepository.findById(id);
        if (sysDict == null) {
            return;
        }
        if (sysDict.getDictType() == 1) {
            throw new BusinessException(ErrorCode.DICT_LOCKED_ERROR);
        }
    }

    /**
     * 添加数据字典子选项
     *
     * @param request 前台参数
     */
    public void itemCreate(DictItemAddRequest request) {
        if (sysDictItemRepository.existsShowValue(request.getNid(), request.getShowValue(), null)) {
            log.warn("数据字典显示值重复 [{}] [{}]", request.getNid(), request.getShowValue());
            throw new BusinessException(ErrorCode.DICT_SHOW_REPEAT_ERROR);
        }
        if (sysDictItemRepository.existsHiddenValue(request.getNid(), request.getHiddenValue(), null)) {
            log.warn("数据字典隐藏值重复 [{}] [{}]", request.getNid(), request.getHiddenValue());
            throw new BusinessException(ErrorCode.DICT_HIDDEN_REPEAT_ERROR);
        }
        sysDictItemRepository.save(DataUtil.copy(request, SysDictItem.class));
    }

    /**
     * 编辑数据字典子选项
     *
     * @param request 前台参数
     */
    public void itemUpdate(DictItemEditRequest request) {
        if (sysDictItemRepository.existsShowValue(request.getNid(), request.getShowValue(), request.getId())) {
            log.warn("数据字典显示值重复 [{}] [{}]", request.getNid(), request.getShowValue());
            throw new BusinessException(ErrorCode.DICT_SHOW_REPEAT_ERROR);
        }
        if (sysDictItemRepository.existsHiddenValue(request.getNid(), request.getHiddenValue(), request.getId())) {
            log.warn("数据字典隐藏值重复 [{}] [{}]", request.getNid(), request.getHiddenValue());
            throw new BusinessException(ErrorCode.DICT_HIDDEN_REPEAT_ERROR);
        }
        sysDictItemRepository.update(DataUtil.copy(request, SysDictItem.class));
    }

    /**
     * 删除数据字典子选项
     *
     * @param id 主键
     */
    public void itemDelete(Long id) {
        sysDictItemRepository.deleteById(id);
    }

    /**
     * 根据nid与隐藏值获取显示信息 数据字典格式化数据
     *
     * @param nid         nid
     * @param hiddenValue 隐藏值
     * @return 显示值
     */
    public String getDictValue(String nid, Integer hiddenValue) {
        List<SysDictItem> dictList = cacheProxyService.getDictByNid(nid);
        for (SysDictItem dict : dictList) {
            if (Objects.equals(dict.getHiddenValue(), hiddenValue)) {
                return dict.getShowValue();
            }
        }
        return null;
    }
}
