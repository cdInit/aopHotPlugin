import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Simple before advice example that we can use for counting checks.
 *
 * @author Rod Johnson
 */
@SuppressWarnings("serial")
public class CountingBeforeAdvice extends MethodCounter implements MethodBeforeAdvice {

	public void before(Method m, Object[] args, Object target) throws Throwable {
		count(m);
		System.out.println(String.format("方法%s 执行次数%s", m.getName(), getCalls()));
	}

}
