package test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.eric.demo.Application;
import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
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

