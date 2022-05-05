package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.*;

import java.io.Serializable;
import java.util.Date;

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
    @ApiModelProperty("$column.comment")
    private String name;

    @TableField(value = "code")
    @ApiModelProperty("$column.comment")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty("备注")
    private String remark;

    @TableField(value = "created")
    @ApiModelProperty("$column.comment")
    private Date created;

    @TableField(value = "updated")
    @ApiModelProperty("$column.comment")
    private Date updated;

    @TableField(value = "statu")
    @ApiModelProperty("$column.comment")
    private Integer statu;
}

