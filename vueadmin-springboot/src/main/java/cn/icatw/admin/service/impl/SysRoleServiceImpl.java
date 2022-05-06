package cn.icatw.admin.service.impl;

import cn.icatw.admin.domain.SysRole;
import cn.icatw.admin.mapper.SysRoleMapper;
import cn.icatw.admin.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * (SysRole)表服务实现类
 *
 * @author icatw
 * @since 2022-05-04 19:24:32
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public List<SysRole> listRolesByUserId(Serializable id) {
        return this.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id =" + id));
    }
}

