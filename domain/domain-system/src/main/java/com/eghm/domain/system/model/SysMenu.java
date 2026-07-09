package com.eghm.domain.system.model;

import com.eghm.domain.shared.enums.DisplayState;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author 二哥很猛
 */
@Data
public class SysMenu {

    /** 根节点 */
    public static final String ROOT = "0";

    /** id主键 */
    private String id;

    /** 菜单名称 */
    private String title;

    /** 菜单标示符 唯一 */
    private String code;

    /** 菜单图标 */
    private String icon;

    /** 父节点ID,一级菜单默认为0 */
    private String pid;

    /** 菜单地址 */
    private String path;

    /** 菜单级别 1:导航菜单 2:按钮菜单 */
    private Integer grade;

    /** 排序规则 小的排在前面 */
    private Integer sort;

    /** 状态:1:正常,0:禁用 */
    private Boolean state;

    /** 备注信息 */
    private String remark;

    /** 该菜单包含的子url以分号做分割 */
    private String subPath;

    /** 菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放) */
    private Integer displayState;

    /** 添加日期 */
    private LocalDateTime createTime;

    /** 更新日期 */
    private LocalDateTime updateTime;

    /**
     * 设置新菜单身份
     *
     * @param id   菜单id
     * @param code 菜单编码
     */
    public void assignIdentity(String id, String code) {
        this.id = id;
        this.code = code;
    }

    /**
     * 重新生成菜单编码
     *
     * @param code 菜单编码
     */
    public void changeCode(String code) {
        this.code = code;
    }

    /**
     * 校验当前菜单是否允许作为子菜单父级
     *
     * @param childDisplayState 子菜单展示状态
     */
    public void assertCanCreateChild(Integer childDisplayState) {
        if (displayState != DisplayState.ALL.getValue() && !displayState.equals(childDisplayState)) {
            throw new BusinessException(ErrorCode.PID_MENU_STATE);
        }
    }

    /**
     * 是否是根节点
     *
     * @param pid 父节点
     * @return true:根节点
     */
    public static boolean isRoot(String pid) {
        return Objects.equals(pid, ROOT);
    }

    public void enable() {
        this.state = true;
    }

    public void disable() {
        this.state = false;
    }

    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.state);
    }

    public boolean isButton() {
        return Integer.valueOf(2).equals(this.grade);
    }

    public boolean isNavigation() {
        return Integer.valueOf(1).equals(this.grade);
    }
}
