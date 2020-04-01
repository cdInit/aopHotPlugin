import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class ExceptionHandler implements MethodInterceptor {

    private ApplicationContext applicationContext;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal;
        try{
            System.out.println(mi.getMethod());
            retVal = mi.proceed();
        }catch (Exception e){
            System.out.println("异常捕获，不继续执行后面的方法...直接返回...");
            System.out.println(applicationContext);
            Object userServiceImpl = applicationContext.getBean("userServiceImpl");
            Method test2 = userServiceImpl.getClass().getMethod("test2");
            test2.invoke(userServiceImpl);
            return null;
        }
        return retVal;
    }
}
