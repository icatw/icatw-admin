package cn.icatw.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icatw.admin.domain.SysMenu;
import cn.icatw.admin.mapper.SysMenuMapper;
import cn.icatw.admin.service.SysMenuService;
import org.springframework.stereotype.Service;

/**
 * (SysMenu)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:31
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}

