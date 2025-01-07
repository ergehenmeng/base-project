package com.eghm.vo.business.order;

import com.eghm.enums.ref.VisitorState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
public class VisitorVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "状态 0:初始化(待支付) 1:已支付,待使用 2:已使用 3:退款中 4:已退款")
    private VisitorState state;

    @Schema(description = "游客姓名")
    private String memberName;

    @Schema(description = "身份证号码")
    private String idCard;
}
