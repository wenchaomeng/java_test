package test.dal.mybatis;

/**
 * @author wenchao.meng
 *         <p>
 *         Jan 22, 2018
 */
public class TestPojo {

    private long id;
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%d %s", id, name);
    }
}
