package tuling.tech.spring.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

//描述切面类
@Aspect
@Configuration
public class TestAop {
    /*
     * 定义一个切入点
     */
    // @Pointcut("execution (* findById*(..))")
//    @Pointcut("execution(* aop*(..))")
//    public void excudeService(){}
//    /*
//     * 通过连接点切入
//     */
//    @After("excudeService()")
//    public void twiceAsOld1(){
//        System.out.println("切入后置...");
//    }

    @Pointcut("execution(* tuling.tech.spring.demo..*(..))")
    public void initAllAop(){}
    @Before("initAllAop()")
    public void initAllAop1(){}


}