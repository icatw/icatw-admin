package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("$column.comment")
    private String username;

    @JsonIgnore
    @TableField(value = "password")
    @ApiModelProperty("$column.comment")
    private String password;

    @TableField(value = "avatar")
    @ApiModelProperty("$column.comment")
    private String avatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不不正确")
    @TableField(value = "email")
    @ApiModelProperty("$column.comment")
    private String email;

    @TableField(value = "city")
    @ApiModelProperty("$column.comment")
    private String city;

    @TableField(value = "phone")
    @ApiModelProperty("$column.comment")
    private String phone;

    @TableField(value = "created")
    @ApiModelProperty("$column.comment")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime created;

    @TableField(value = "updated")
    @ApiModelProperty("$column.comment")
    private LocalDateTime updated;

    @TableField(value = "last_login")
    @ApiModelProperty("$column.comment")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime lastLogin;

    @TableField(value = "statu")
    @ApiModelProperty("$column.comment")
    private Integer statu;

    @TableField(exist = false)
    private List<SysRole> roles;
}

