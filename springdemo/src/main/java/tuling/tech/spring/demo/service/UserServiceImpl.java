package tuling.tech.spring.demo.service;

import org.springframework.stereotype.Service;
import tuling.tech.spring.demo.po.Student;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void test() {
        Student student = new Student("1", 1);
        System.out.println(student);

        System.out.println("test service");
        System.out.println(1/0);
        System.out.println("test service end...");
    }


    @Override
    public void test2() {
        System.out.println("test2 service");
    }
}
