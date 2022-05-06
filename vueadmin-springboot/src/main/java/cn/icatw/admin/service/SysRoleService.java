package cn.icatw.admin.service;

import cn.icatw.admin.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * (SysRole)表服务接口
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户id得到角色list
     *
     * @param id id
     * @return {@link List}<{@link SysRole}>
     */
    List<SysRole> listRolesByUserId(Serializable id);
}

