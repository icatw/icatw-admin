package cn.icatw.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysRole)实体类
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    private static final long serialVersionUID = 591317477826076416L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "updated")
    private Date updated;

    @TableField(value = "statu")
    private Integer statu;
}

