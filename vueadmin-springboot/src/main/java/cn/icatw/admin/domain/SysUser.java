package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUser)实体类
 *
 * @author icatw
 * @since 2022-05-05 08:41:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("$tableInfo.comment")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 212179718471754666L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    @ApiModelProperty("$column.comment")
    private String username;

    @TableField(value = "password")
    @ApiModelProperty("$column.comment")
    private String password;

    @TableField(value = "avatar")
    @ApiModelProperty("$column.comment")
    private String avatar;

    @TableField(value = "email")
    @ApiModelProperty("$column.comment")
    private String email;

    @TableField(value = "city")
    @ApiModelProperty("$column.comment")
    private String city;

    @TableField(value = "created")
    @ApiModelProperty("$column.comment")
    private Date created;

    @TableField(value = "updated")
    @ApiModelProperty("$column.comment")
    private Date updated;

    @TableField(value = "last_login")
    @ApiModelProperty("$column.comment")
    private Date lastLogin;

    @TableField(value = "statu")
    @ApiModelProperty("$column.comment")
    private Integer statu;
}

