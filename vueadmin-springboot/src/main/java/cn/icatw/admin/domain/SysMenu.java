package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表(SysMenu)实体类
 *
 * @author icatw
 * @since 2022-05-05 08:45:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单表")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -97880954287881078L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(value = "parent_id")
    @ApiModelProperty("父菜单ID，一级菜单为0")
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;

    @TableField(value = "name")
    @ApiModelProperty("$column.comment")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单URL
     */
    @TableField(value = "path")
    @ApiModelProperty("菜单URL")
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField(value = "perms")
    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    @NotBlank(message = "菜单授权码不能为空")
    private String perms;

    @TableField(value = "component")
    @ApiModelProperty("$column.comment")
    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField(value = "type")
    @ApiModelProperty("类型     0：目录   1：菜单   2：按钮")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "orderNum")
    @ApiModelProperty("排序")
    private Integer orderNum;

    @TableField(value = "created")
    @ApiModelProperty("$column.comment")
    private LocalDateTime created;

    @TableField(value = "updated")
    @ApiModelProperty("$column.comment")
    private LocalDateTime updated;

    @TableField(value = "statu")
    @ApiModelProperty("$column.comment")
    private Integer statu;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}

