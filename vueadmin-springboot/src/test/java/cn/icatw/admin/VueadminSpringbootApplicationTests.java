package cn.icatw.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class VueadminSpringbootApplicationTests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void contextLoads() {
        String encode = bCryptPasswordEncoder.encode("111111");
        boolean b = bCryptPasswordEncoder.matches("111111", encode);
        System.out.println("密码匹配结果为：" + b);
        System.out.println("加密后的密码为:" + encode);
    }

    @Test
    void testOss() {

    }
}
