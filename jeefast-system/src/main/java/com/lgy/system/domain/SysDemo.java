package com.lgy.system.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.lgy.common.core.domain.BaseEntity;

/**
 * 用户演示表 sys_demo
 *
 * @author lgy
 * @date 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_demo")
public class SysDemo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    /** 登录账号 */
    private String loginName;
    /** 用户昵称 */
    private String userName;
    /** 删除标志（0代表存在 2代表删除） */
    //private String delFlag;
    /** 创建者 */
    //private String createBy;
    /** 创建时间 */
    //private Date createTime;
    /** 更新者 */
    //private String updateBy;
    /** 更新时间 */
    //private Date updateTime;
    /** 备注 */
    private String remark;
}
