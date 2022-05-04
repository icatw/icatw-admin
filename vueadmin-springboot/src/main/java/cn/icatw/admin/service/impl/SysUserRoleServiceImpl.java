package cn.icatw.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icatw.admin.domain.SysUserRole;
import cn.icatw.admin.mapper.SysUserRoleMapper;
import cn.icatw.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * (SysUserRole)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}

