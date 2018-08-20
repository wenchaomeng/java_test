package test.dal.mybatis.spring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
@SpringBootApplication
public class MybatisSpringBoot {


    public static void main(String []argc){

        new SpringApplicationBuilder(MybatisSpringBoot.class).run();
    }
}
