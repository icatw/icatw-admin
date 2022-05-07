package cn.icatw.admin.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author icatw
 * @date 2022/5/7
 * @email 762188827@qq.com
 * @apiNote
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 212179718471754666L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("$column.comment")
    private String username;


    @TableField(value = "avatar")
    @ApiModelProperty("$column.comment")
    private String avatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("$column.comment")
    private String email;

    @TableField(value = "city")
    @ApiModelProperty("$column.comment")
    private String city;

    @TableField(value = "phone")
    @ApiModelProperty("$column.comment")
    private String phone;
}
