package cn.icatw.admin.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 通过dto
 *
 * @author icatw
 * @date 2022/05/06
 */
@Data
public class PassVo implements Serializable {

	@NotBlank(message = "新密码不能为空")
	private String password;

	@NotBlank(message = "旧密码不能为空")
	private String currentPass;
}
