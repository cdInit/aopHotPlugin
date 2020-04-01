package tuling.tech.spring.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tuling.tech.spring.demo.service.AopPluginTest;
import tuling.tech.spring.demo.service.AopService;
import tuling.tech.spring.demo.service.UserService;

//@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes={DemoApplication.class})
public class PluginConfigTest {

    @Autowired
    private AopPluginTest aopPluginTest;

    @Autowired
    private UserService userService;

    @Autowired
    private AopService aopService;


    @Test
    public void testSpringAop(){
        aopPluginTest.activePlugin("1");
        userService.test();

//        System.out.println(this.getClass().getClassLoader());
    }



    @Test
    public void testSpringAop2(){
        userService.test();
    }


}
