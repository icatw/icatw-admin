package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysRoleMenu)实体类
 *
 * @author icatw
 * @since 2022-05-05 08:41:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("$tableInfo.comment")
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = -10881617499030964L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "role_id")
    @ApiModelProperty("$column.comment")
    private Long roleId;

    @TableField(value = "menu_id")
    @ApiModelProperty("$column.comment")
    private Long menuId;
}

