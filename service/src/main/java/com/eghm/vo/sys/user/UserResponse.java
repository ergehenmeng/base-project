package com.eghm.vo.sys.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eghm.enums.DataType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class UserResponse {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户姓名")
    private String nickName;

    @Schema(description = "账户名(登陆账户)")
    private String userName;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "手机号码(登陆账户)")
    private String mobile;

    @Schema(description = "用户类型 1:系统用户 2:商户管理员 3:商户普通用户")
    private Integer userType;

    @Schema(description = "数据权限 只针对系统用户有效")
    private DataType dataType;

    @Schema(description = "用户状态 0:锁定 1:正常")
    private Integer state;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}