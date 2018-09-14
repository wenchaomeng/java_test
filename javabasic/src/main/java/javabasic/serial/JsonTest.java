package javabasic.serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javabasic.AbstractTest;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author wenchao.meng
 *         <p>
 *         May 15, 2018
 */
public class JsonTest extends AbstractTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testStringDesr() throws IOException {

        String test = "\"nihaoma\"";
//        String test = "\"test\\n\"";
        String object = objectMapper.readValue(test, String.class);
        logger.info("{}", object);
    }

    @Test
    public void testStringSer() throws IOException {
//        String test = "nihaoma\n";
        String test = "\"t,e s\nt1}2345\"";
        String object = objectMapper.writeValueAsString(test);
        logger.info("{}", object);


    }



    @Test
    public void test() throws JsonProcessingException {

        BigDecimal bigNumber = new BigDecimal("89.1234567890123456789");

        Person person = new Person(1, bigNumber);


        String s = objectMapper.writeValueAsString(person);
        logger.info("{}", s);
    }


    public static class Person {

        private int id;
        private BigDecimal bigDecimal;

        public Person(int id, BigDecimal bigDecimal) {
            this.id = id;
            this.bigDecimal = bigDecimal;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
