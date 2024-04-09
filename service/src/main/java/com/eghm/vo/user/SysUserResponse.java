package com.eghm.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eghm.enums.DataType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class SysUserResponse {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("用户姓名")
    private String nickName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("手机号码(登陆账户)")
    private String mobile;

    @ApiModelProperty("用户类型 1:系统用户 2:商户管理员 3:商户普通用户")
    private Integer userType;

    @ApiModelProperty("数据权限 只针对系统用户有效")
    private DataType dataType;

    @ApiModelProperty("用户状态 0:锁定 1:正常")
    private Integer state;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}