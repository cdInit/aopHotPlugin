package tuling.tech.spring.demo;

import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import tuling.tech.spring.demo.service.UserService;
import tuling.tech.spring.demo.service.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
public class JavaProxyTest {
    @Test
    public void testProxy(){
        //java 动态代理
        //loader
        //invoicationhand

        UserServiceImpl userService = new UserServiceImpl();
        userService.test();
        System.out.println("---------------------------");
        UserService userService1 = (UserService) Proxy.newProxyInstance(JavaProxyTest.class.getClassLoader(), new Class[]{UserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                try{
                    method.invoke(userService,args);
                }catch (Exception e){
                    System.out.println("exception...");
                }
                System.out.println("after...");
                return null;
            }
        });

        userService1.test();
        System.out.println("---------------------------");
        userService1.test2();
    }

    @Test
    public void testCGlib(){
        UserServiceImpl userService = new UserServiceImpl();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                Object res = null;
                try{
                    res = methodProxy.invoke(userService,objects);
                }catch (Exception e){
                    System.out.println("exception...");
                }
                System.out.println("after...");
                return res;
            }
        });
        UserServiceImpl proxUserServiceImpl = (UserServiceImpl) enhancer.create();
        proxUserServiceImpl.test();
        proxUserServiceImpl.test2();
    }

    @Test
    public void testSpringAop(){
        Object target = new UserServiceImpl();
        ProxyFactory pf = new ProxyFactory(target);
        pf.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] objects, Object o) throws Throwable {
                System.out.println("调用之前...");
            }
        });

        UserService proxy = (UserService) pf.getProxy();
        proxy.test();
    }

    @Test
    public void testSpringAopAspectJ(){
        Object target = new UserServiceImpl();
        AspectJProxyFactory pf = new AspectJProxyFactory(target); // Advised

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(* *.test(..))");
        MethodBeforeAdvice beforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] objects, Object o) throws Throwable {
                System.out.println("调用之前...");
            }
        };
        advisor.setAdvice(beforeAdvice);
        pf.addAdvisor(advisor);
        UserService proxy = pf.getProxy();
        proxy.test();
        proxy.test2();
    }
}
