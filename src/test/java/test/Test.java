package test;

import com.eric.demo.Application;
import com.eric.demo.commons.util.HttpClientUtil;
import org.jasypt.encryption.StringEncryptor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.internet.MimeMessage;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration
public class Test {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringEncryptor stringEncryptor;

   /* @Autowired
    private UserService userService;

    @org.junit.Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUsername("eric7");
            user.setPassword("tthhjhjhjhjho");
            System.out.println(userService.save(user));
        }
    }
*/

    @org.junit.Test
    public void test1() throws Exception {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000000; i++) {
                        String s = HttpClientUtil.httpPostRequest("http://127.0.0.1:8080/api/users");
                        System.out.println("-----------------------" + s + "---------------------------------------------------------");
                    }
                }
            }).start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @org.junit.Test
    public void test2() throws Exception {
        //true表示需要创建一个multipart message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("liumingyue0203@163.com");
        helper.setTo("407231704@qq.com");
        helper.setSubject("111");
        //html 加如参数 true
        helper.setText("1111");

        mailSender.send(message);

    }

    @org.junit.Test
    public void test3(){
        System.out.println(stringEncryptor.encrypt("jdbc:mysql://60.205.229.68:1306/bill_test?autoReconnect=true&useUnicode=true&characterEncoding=utf-8"));
        //System.out.println(stringEncryptor.decrypt("dQ6DsHsFvZtA0yIFHHzE7sQ/6mzewvDMre7V+TnH/iosHUyNoF1ClBm20/C96kgnJF+EZ3kOG/RCD3c4CxoEqIUlldpagn1ONCKjrjCNtlg4gGW6fP80A1WRiTT8uZ9JwC/emcobaHV+U1shEfrCDQ=="));
    }
}

