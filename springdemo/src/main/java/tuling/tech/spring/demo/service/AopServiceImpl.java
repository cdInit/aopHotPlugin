package tuling.tech.spring.demo.service;

import org.springframework.stereotype.Service;

@Service
public class AopServiceImpl implements AopService {

    @Override
    public void aop1() {
        System.out.println("aop1 service");
    }


}
