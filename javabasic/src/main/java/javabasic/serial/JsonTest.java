package javabasic.serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javabasic.AbstractTest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author wenchao.meng
 *         <p>
 *         May 15, 2018
 */
public class JsonTest extends AbstractTest {

    @Test
    public void test() throws JsonProcessingException {

        BigDecimal bigNumber = new BigDecimal("89.1234567890123456789");

        Person person = new Person(1, bigNumber);

        ObjectMapper objectMapper = new ObjectMapper();

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
