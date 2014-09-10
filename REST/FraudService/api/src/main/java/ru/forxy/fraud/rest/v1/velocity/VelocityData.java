package ru.forxy.fraud.rest.v1.velocity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Contains raw data set for different velocity metrics
 */
@Table(name = "velocity_data")
public class VelocityData implements Serializable {

    private static final long serialVersionUID = 6850650473701192980L;

    @EmbeddedId
    private VelocityPartitionKey key;
    @Column(name = "metric_type")
    private String metricType;
    @Column(name = "create_date")
    private Date createDate;
    private String value;

    public VelocityPartitionKey getKey() {
        return key;
    }

    public void setKey(VelocityPartitionKey key) {
        this.key = key;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}