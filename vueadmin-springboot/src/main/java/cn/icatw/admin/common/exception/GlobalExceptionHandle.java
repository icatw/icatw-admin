package cn.icatw.admin.common.exception;

import cn.icatw.admin.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author icatw
 * @date 2022/4/28
 * @email 762188827@qq.com
 * @apiNote
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public R handleCustomException(CustomException customException) {

        return R.fail(customException.getCode(), customException.getMessage());
    }
}
