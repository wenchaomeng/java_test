package test.dal.mybatis.spring;

import com.ctrip.datasource.configure.DalDataSourceFactory;
import com.ctrip.datasource.spring.annotation.EnableDalMybatis;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
@EnableDalMybatis
@Configuration
public class AppConfig {

    private DalDataSourceFactory factory = new DalDataSourceFactory();

    @Bean
    public DataSource dataSource() throws Exception {
        return factory.createDataSource("mysqltitantest04db_W");
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(ResourceLoader resourceLoader) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:test/dal/mybatis/mybatis-config.xml"));

        return sqlSessionFactoryBean;
    }
}