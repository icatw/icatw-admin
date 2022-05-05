package cn.icatw.admin.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.icatw.admin.common.Const;
import cn.icatw.admin.common.R;
import cn.icatw.admin.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 身份验证控制器
 *
 * @author icatw
 * @date 2022/5/4
 * @email 762188827@qq.com
 * @apiNote
 */
@Slf4j
@RestController
@Api("验证码模块")
public class AuthController {
    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 图片验证码
     */
    @ApiOperation("图片验证码")
    @GetMapping("/captcha")
    public R captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = producer.createText();
        String key = UUID.randomUUID().toString();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // 存储到redis中
        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
        log.info("验证码 -- {} - {}", key, code);
        return R.ok(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64Img)
                        .build()
        );
    }
}
