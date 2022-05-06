package cn.icatw.admin.common.exception;

import cn.icatw.admin.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 *
 * @author icatw
 * @date 2022/4/28
 * @email 762188827@qq.com
 * @apiNote
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public R handleCustomException(CustomException customException) {

        return R.fail(customException.getCode(), customException.getMessage());
    }

    // 实体校验异常捕获
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();

        log.error("实体校验异常：----------------{}", objectError.getDefaultMessage());
        return R.fail(objectError.getDefaultMessage());
    }
}
