package org.springyoung.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联表
 */
@Data
@TableName("young_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -3166012934498268403L;

    private Long userId;

    private Long roleId;

}