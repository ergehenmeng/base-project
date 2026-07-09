package com.eghm.domain.member.model;

import com.eghm.domain.shared.model.BaseEntity;
import com.eghm.domain.shared.enums.DirectionType;
import com.eghm.enums.ErrorCode;
import com.eghm.domain.shared.enums.MemberState;
import com.eghm.domain.shared.enums.ScoreType;
import com.eghm.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 用户信息表
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseEntity {

    /** 昵称 */
    private String nickName;

    /** 手机号码 */
    private String mobile;

    /** 账号 */
    private String account;

    /** 微信小程序openId */
    private String maOpenId;

    /** 微信公众号openId */
    private String mpOpenId;

    /** 微信unionId */
    private String unionId;

    /** 电子邮箱 */
    private String email;

    /** 登陆密码 */
    private String pwd;

    /** 状态 0:冻结 1:正常 */
    private MemberState state;

    /** 总积分数 */
    private Integer score;

    /** 邀请码 */
    private String inviteCode;

    /** 真实姓名 */
    private String realName;

    /** 身份证号码,前6位加密 */
    private String idCard;

    /** 生日yyyyMMdd */
    private String birthday;

    /** 性别 性别 0:未知 1:男 2:女  */
    private Integer sex;

    /** 注册渠道 PC,ANDROID,IOS,H5,OTHER */
    private String channel;

    /** 头像 */
    private String avatar;

    /** 注册地址 */
    private Long registerIp;

    /** 注册日期 */
    private LocalDate createDate;

    /** 创建月份 */
    private String createMonth;

    public void initializeRegistration(Long id, String inviteCode, String nickName, LocalDate createDate, String createMonth) {
        setId(id);
        this.inviteCode = inviteCode;
        this.nickName = nickName;
        this.createDate = createDate;
        this.createMonth = createMonth;
    }

    public void assertCanLogin() {
        if (state == MemberState.FREEZE) {
            throw new BusinessException(ErrorCode.MEMBER_LOGIN_FORBID);
        }
    }

    public void changeState(MemberState state) {
        this.state = state;
    }

    public void bindEmail(String email) {
        if (this.email != null && !this.email.isBlank()) {
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
        this.email = email;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.pwd = password;
    }

    public int calculateScoreSurplus(ScoreType scoreType, Integer score) {
        int changedScore = resolveScoreChange(scoreType, score);
        int surplus = this.score + changedScore;
        if (surplus < 0) {
            throw new BusinessException(ErrorCode.MEMBER_SCORE_ERROR);
        }
        return surplus;
    }

    public int resolveScoreChange(ScoreType scoreType, Integer score) {
        int changedScore = Math.abs(score);
        if (scoreType.getDirection() == DirectionType.DISBURSE) {
            changedScore = -changedScore;
        }
        return changedScore;
    }

    public MemberScoreLog changeScore(ScoreType scoreType, Integer score, String remark) {
        int changedScore = resolveScoreChange(scoreType, score);
        int surplus = this.score + changedScore;
        if (surplus < 0) {
            throw new BusinessException(ErrorCode.MEMBER_SCORE_ERROR);
        }
        this.score = surplus;

        MemberScoreLog scoreLog = new MemberScoreLog();
        scoreLog.setScore(changedScore);
        scoreLog.setRemark(remark);
        scoreLog.setMemberId(getId());
        scoreLog.setSurplusScore(surplus);
        scoreLog.setType(scoreType.getValue());
        return scoreLog;
    }
}
