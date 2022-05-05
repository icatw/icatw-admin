package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.*;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysUserRole)实体类
 *
 * @author icatw
 * @since 2022-05-05 08:41:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("$tableInfo.comment")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 622882538640159263L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    @ApiModelProperty("$column.comment")
    private Long userId;

    @TableField(value = "role_id")
    @ApiModelProperty("$column.comment")
    private Long roleId;
}

