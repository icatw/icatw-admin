package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysMenu)实体类
 *
 * @author icatw
 * @since 2022-05-04 19:24:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -98053044352944224L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "name")
    private String name;

    /**
     * 菜单URL
     */
    @TableField(value = "path")
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField(value = "perms")
    private String perms;

    @TableField(value = "component")
    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "orderNum")
    private Integer ordernum;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "updated")
    private Date updated;

    @TableField(value = "statu")
    private Integer statu;
}

