package cn.icatw.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.icatw.admin.domain.SysUser;
import cn.icatw.admin.mapper.SysUserMapper;
import cn.icatw.admin.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * (SysUser)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}

