package cn.icatw.admin.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author icatw
 * @date 2022/05/05
 */
public class CaptchaException extends AuthenticationException {

	public CaptchaException(String msg) {
		super(msg);
	}
}
