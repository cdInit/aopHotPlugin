package tuling.tech.spring.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class RateLimiterExample {
    // 一秒钟允许多少个操作
    private final static RateLimiter limiter = RateLimiter.create(0.5); // 一秒钟允许0.5个操作，2秒钟1次操作
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0,10).forEach( i -> {
            service.submit(RateLimiterExample::testLimiter);
        });
    }
    private static void testLimiter(){
        System.out.println(Thread.currentThread() + "waiting " + limiter.acquire());
    }
}
