package com.eghm.dto.ext;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 包含操作人或操作姓名均可以继承此类
 *
 * @author 二哥很猛
 * @since 2019/8/8 11:47
 */
@Getter
@Setter
public class ActionRecord {

    @Assign
    @Schema(description = "操作人姓名", hidden = true)
    private String userName;

    @Assign
    @Schema(description = "操作人id", hidden = true)
    private Long userId;

}
