package cn.icatw.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icatw.admin.domain.SysRoleMenu;
import cn.icatw.admin.mapper.SysRoleMenuMapper;
import cn.icatw.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * (SysRoleMenu)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
}

