package spring.boot.transaction;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author wenchao.meng
 *         <p>
 *         Jun 06, 2018
 */
@Configuration
public class ConfigBean {

    @Bean(name = "datasource1")
    public DataSource dataSource1() {

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/test");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("root");
        p.setInitialSize(1);
        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);
        return datasource;
    }

    @Bean(name = "datasource2")
    public DataSource dataSource2() {

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/test2");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("root");
        p.setInitialSize(1);
        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);
        return datasource;
    }

    @Bean(name = "tx1")
    public DataSourceTransactionManager dataSourceTransactionManager1(@Qualifier("datasource1") DataSource dataSource) {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }

    @Bean(name = "tx2")
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("datasource2") DataSource dataSource) {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }

}
