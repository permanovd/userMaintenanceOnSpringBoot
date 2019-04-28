package com.permanovd.user_maintainance;

import com.permanovd.user_maintainance.User.infrastructure.UniqueNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// Spring di container does not support same class names by
// default, so we will use full class name. See https://stackoverflow.com/a/54954500/4082772
@ComponentScan(nameGenerator = UniqueNameGenerator.class)
public class TestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestsApplication.class, args);
    }

}
