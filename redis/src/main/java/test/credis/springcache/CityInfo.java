package test.credis.springcache;

import java.io.Serializable;

/**
 * @author wenchao.meng
 *         <p>
 *         Jun 13, 2018
 */
public class CityInfo implements Serializable {

    private int id;
    private String city;

    public CityInfo(int id, String city) {
        this.id = id;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
