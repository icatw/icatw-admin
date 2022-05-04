package cn.icatw.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.mapper.SysRoleMapper;
import cn.icatw.admin.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * (SysRole)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}

