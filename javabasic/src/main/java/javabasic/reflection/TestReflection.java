package javabasic.reflection;

import javabasic.AbstractTest;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wenchao.meng
 *         <p>
 *         Apr 17, 2018
 */
public class TestReflection extends AbstractTest {

    @Test
    public void test() {

        List<Field> fields = new LinkedList<>();
        getAllFileds(Child.class, fields);

        logger.info("{}", fields);

        Child child = new Child("c1", "p1");

        fields.forEach(field -> {
            try {
                Object o = field.get(child);
                logger.info("{}", o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });

    }


    public void getAllFileds(Class clazz, List<Field> fields) {

        if(clazz == null){
            return;
        }

        System.out.println(clazz);

        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            fields.add(field);
        }

        getAllFileds(clazz.getSuperclass(), fields);
    }
}
