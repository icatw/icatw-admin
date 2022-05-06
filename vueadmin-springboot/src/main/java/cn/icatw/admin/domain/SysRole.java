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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * (SysRole)实体类
 *
 * @author icatw
 * @since 2022-05-05 08:41:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("$tableInfo.comment")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 570460188903351930L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("$column.comment")
    private String name;

    @TableField(value = "code")
    @ApiModelProperty("$column.comment")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty("备注")
    private String remark;

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
    private List<Long> menuIds = new ArrayList<Long>();

}

