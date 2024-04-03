package ${template.implPackage}.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${template.implPackage}.${template.fileName}Service;
import ${template.requestPackage}.${template.selectRequest};
import ${template.requestPackage}.${template.createRequest};
import ${template.requestPackage}.${template.updateRequest};

import com.eghm.mapper.${template.fileName}Mapper;
import ${template.fileFullName};

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.service.business.CommonService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 二哥很猛
* @since ${.now?string('yyyy-MM-dd')}
*/
@Slf4j
@Service("${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Service")
@AllArgsConstructor
public class ${template.fileName}ServiceImpl implements ${template.fileName}Service {

    private final ${template.fileName}Mapper ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper;

    private final CommonService commonService;

    @Override
    public Page<${template.fileName}> getByPage(${template.selectRequest} request) {
        LambdaQueryWrapper<${template.fileName}> wrapper = Wrappers.lambdaQuery();
        return ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(${template.createRequest} request) {
        this.redoTitle(request.getTitle(), null);
        ${template.fileName} data = DataUtil.copy(request, ${template.fileName}.class);
        data.setMerchantId(SecurityHolder.getMerchantId());
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.insert(data);
    }

    @Override
    public void update(${template.updateRequest} request) {
        this.redoTitle(request.getTitle(), request.getId());
        ${template.fileName} select = ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.selectById(request.getId());
        commonService.checkIllegal(select.getMerchantId());

        ${template.fileName} data = DataUtil.copy(request, ${template.fileName}.class);
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.updateById(data);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<${template.fileName}> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(${template.fileName}::getId, id);
        wrapper.set(${template.fileName}::getDeleted, true);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, ${template.fileName}::getMerchantId, merchantId);
        ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.update(null, wrapper);
    }

    /**
    * 校验名称是否重复
    *
    * @param title 名称
    * @param id  编辑时不能为空
    */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<${template.fileName}> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(${template.fileName}::getTitle, title);
        wrapper.ne(id != null, ${template.fileName}::getId, id);
        Long count = ${(template.fileName?substring(0, 1))?lower_case}${template.fileName?substring(1)}Mapper.selectCount(wrapper);
        if (count > 0) {
            log.info("名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.SHOP_TITLE_REDO);
        }
    }

}