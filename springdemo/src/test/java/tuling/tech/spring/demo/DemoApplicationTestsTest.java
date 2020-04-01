//package tuling.tech.spring.demo;
//
//import org.junit.Test;
//import org.springframework.beans.factory.support.AbstractBeanDefinition;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import tuling.teche.spirng.demo.service.Impl.UserDao;
//import tuling.teche.spirng.demo.service.Impl.UserServiceImpl;
//
//import static org.junit.Assert.*;
//
//@SpringBootTest
//public class DemoApplicationTestsTest {
//    @Test //spring中bean的创建
//    public void creatBeanTest(){
//        DefaultListableBeanFactory factory =  new DefaultListableBeanFactory();
//        UserServiceImpl bean = factory.createBean(UserServiceImpl.class);
//        System.out.println(bean);
//    }
//
//    @Test//spring中bean的存储 获取
//    public void saveBeanTest(){
//        DefaultListableBeanFactory factory =  new DefaultListableBeanFactory();
//        factory.registerSingleton("dao", new UserServiceImpl());
//        Object dao = factory.getBean("dao");
//        System.out.println(dao);
//    }
//
//    @Test//spring中bean的依赖
//    public void dependcsBeanTest(){
//        DefaultListableBeanFactory factory =  new DefaultListableBeanFactory();
//        factory.registerSingleton("userServiceImpl", new UserServiceImpl());
////        UserServiceImpl dao = (UserServiceImpl) factory.getBean("dao");
//
//        factory.registerSingleton("userDao", new UserDao());
//        UserServiceImpl dao = (UserServiceImpl) factory.getBean("userServiceImpl",AbstractBeanDefinition.AUTOWIRE_BY_TYPE,true);
//        System.out.println(dao);
//    }
//
//
//}