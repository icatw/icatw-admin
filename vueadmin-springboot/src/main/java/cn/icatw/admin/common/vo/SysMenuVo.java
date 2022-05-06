package cn.icatw.admin.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单dto
 * {
 * name: 'SysUser',
 * title: '用户管理',
 * icon: 'el-icon-s-custom',
 * path: '/sys/users',
 * component: 'sys/User',
 * children: []
 * },
 *
 * @author icatw
 * @date 2022/05/06
 */
@Data
public class SysMenuVo implements Serializable {

	private Long id;
	private String name;
	private String title;
	private String icon;
	private String path;
	private String component;
	private List<SysMenuVo> children = new ArrayList<>();

}
