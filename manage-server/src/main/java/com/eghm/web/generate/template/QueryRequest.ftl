package ${template.packageName};

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author 二哥很猛
* @since ${.now?string('yyyy-MM-dd')}
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class ${template.fileName}QueryRequest extends PagingQuery {

}
