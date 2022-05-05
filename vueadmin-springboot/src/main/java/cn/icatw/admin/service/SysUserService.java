package cn.icatw.admin.service;

import cn.icatw.admin.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * (SysUser)表服务接口
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    SysUser getByUsername(String username);
}

