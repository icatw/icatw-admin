package cn.icatw.admin.mapper;

import cn.icatw.admin.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author icatw
 * @since 2022-05-04 19:24:33
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * ids得到导航菜单
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> getNavMenuIds(@Param("userId") Long userId);

    /**
     * 通过菜单id列表
     *
     * @param menuId 菜单id
     * @return {@link List}<{@link SysUser}>
     */
    List<SysUser> listByMenuId(@Param("menuId") Long menuId);
}

