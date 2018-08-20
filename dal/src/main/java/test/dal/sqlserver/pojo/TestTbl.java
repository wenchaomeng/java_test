package test.dal.sqlserver.pojo;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Types;

/**
 * @author wenchao.meng
 *         <p>
 *         Dec 26, 2017
 */
@Entity
@Database(name = "testSqlServer")
@Table(name = "test")
public class TestTbl implements DalPojo {

    //primary key
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private BigInteger id;

    //cluster name
    @Column(name = "name")
    @Type(value = Types.VARCHAR)
    private String name;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}