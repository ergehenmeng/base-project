package ${template.implPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${template.requestPackage}.${template.selectRequest};
import ${template.requestPackage}.${template.createRequest};
import ${template.requestPackage}.${template.updateRequest};

import ${template.fileFullName};


/**
* @author 二哥很猛
* @since ${.now?string('yyyy-MM-dd')}
*/
public interface ${template.fileName}Service {

    /**
    * 分页查询记录
    * @param request 查询条件
    * @return 列表
    */
    Page<${template.fileName}> getByPage(${template.selectRequest} request);

    /**
    * 新增
    *
    * @param request 信息
    */
    void create(${template.createRequest} request);

    /**
    * 更新
    *
    * @param request 商品信息
    */
    void update(${template.updateRequest} request);

    /**
    * 删除
    *
    * @param id id
    */
    void delete(Long id);
}