package tuling.tech.spring.demo;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class FunctionExample {
    public static void main(String[] args) throws Exception {
        // 创建自动start的计时器
        Stopwatch watch = Stopwatch.createStarted();
        Thread.sleep(1000L);
        long time = watch.elapsed(TimeUnit.MILLISECONDS);
        // 结果1003
        System.out.println("代码执行时长：" + time);

        // 创建非自动start的计时器
        // 累计了start到stop的时间
        Stopwatch watch1 = Stopwatch.createUnstarted();
        Thread.sleep(1000L);
        watch1.start();
        Thread.sleep(1000L);
        watch1.stop();
        watch1.start();
        Thread.sleep(500L);
        time = watch1.elapsed(TimeUnit.MILLISECONDS);
        // 结果1500
        System.out.println("代码执行时长：" + time);

        Stopwatch watch2 = Stopwatch.createUnstarted();
        watch2.start();
        Thread.sleep(1000L);
        time = watch2.elapsed(TimeUnit.MILLISECONDS);
        // 结果1000
        System.out.println("代码执行时长：" + time);
    }
}
