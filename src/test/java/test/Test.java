package test;

import com.eric.demo.Application;
import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes=Application.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration
public class Test {
	
	@Autowired
	private UserService userService;
	
	@org.junit.Test
	public void test(){
		User user = new User();
		user.setUsername("eric7");
		user.setPassword("1234567");
		System.out.println(userService.save(user));
	}
}

