package test.dal.mysql.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Sensitive;
import com.ctrip.platform.dal.dao.annotation.Type;

import java.sql.Types;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

@Entity
@Database(name = "fxxpipeDB_W")
@Table(name = "cluster_tbl")
public class ClusterTbl implements DalPojo {

    //primary key
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private BigInteger id;

    @Column(name = "cluster_name")
    @Type(value = Types.VARCHAR)
    private String clusterName;

    //active dc id
    @Column(name = "activedc_id")
    @Type(value = Types.BIGINT)
    private BigInteger activedcId;

    //cluster description
    @Column(name = "cluster_description")
    @Type(value = Types.VARCHAR)
    private String clusterDescription;

    //last modified tag
    @Column(name = "cluster_last_modified_time")
    @Type(value = Types.VARCHAR)
    private String clusterLastModifiedTime;

    //status
    @Column(name = "status")
    @Type(value = Types.VARCHAR)
    private String status;

    //last modified time
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    //deleted or not
    @Column(name = "deleted")
    @Type(value = Types.BIT)
    private Boolean deleted;

    //is xpipe interested
    @Column(name = "is_xpipe_interested")
    @Type(value = Types.BIT)
    private Boolean isXpipeInterested;

    //organization id of cluster
    @Column(name = "cluster_org_id")
    @Type(value = Types.BIGINT)
    private BigInteger clusterOrgId;

    //persons email who in charge of this cluster
    @Column(name = "cluster_admin_emails")
    @Type(value = Types.VARCHAR)
    private String clusterAdminEmails;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

//    public String getClusterName() {
//        return clusterName;
//    }
//
//    public void setClusterName(String clusterName) {
//        this.clusterName = clusterName;
//    }

    public BigInteger getActivedcId() {
        return activedcId;
    }

    public void setActivedcId(BigInteger activedcId) {
        this.activedcId = activedcId;
    }

    public String getClusterDescription() {
        return clusterDescription;
    }

    public void setClusterDescription(String clusterDescription) {
        this.clusterDescription = clusterDescription;
    }

    public String getClusterLastModifiedTime() {
        return clusterLastModifiedTime;
    }

    public void setClusterLastModifiedTime(String clusterLastModifiedTime) {
        this.clusterLastModifiedTime = clusterLastModifiedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Timestamp datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getIsXpipeInterested() {
        return isXpipeInterested;
    }

    public void setIsXpipeInterested(Boolean isXpipeInterested) {
        this.isXpipeInterested = isXpipeInterested;
    }

    public BigInteger getClusterOrgId() {
        return clusterOrgId;
    }

    public void setClusterOrgId(BigInteger clusterOrgId) {
        this.clusterOrgId = clusterOrgId;
    }

    public String getClusterAdminEmails() {
        return clusterAdminEmails;
    }

    public void setClusterAdminEmails(String clusterAdminEmails) {
        this.clusterAdminEmails = clusterAdminEmails;
    }


    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
