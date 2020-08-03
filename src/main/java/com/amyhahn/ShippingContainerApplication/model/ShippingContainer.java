package com.amyhahn.ShippingContainerApplication.model;

import com.amyhahn.ShippingContainerApplication.util.ShippingContainerUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "shippingcontainers")
public class ShippingContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long containerId;

    private long containerOwnerId;
    private long customerId;
    private String status = ShippingContainer.statusAvailable;
    private String statusTimestamp = ShippingContainerUtils.getCurrentTimestampAsString();

    public static final String statusAvailable = "AVAILABLE";
    public static final String statusUnavailable = "UNAVAILABLE";

    public ShippingContainer() {

    }

    public ShippingContainer(long containerOwnerId, long customerId) {
        this.containerOwnerId = containerOwnerId;
        this.customerId = customerId;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        statusTimestamp = dtf.format(now);
    }

    public long getContainerId() {
        return containerId;
    }
    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }

    @Column(name = "status_timestamp", nullable = false)
    public String getStatusTimestamp() {
        return statusTimestamp;
    }
    public void setStatusTimestamp(String statusTimestamp) {
        this.statusTimestamp = statusTimestamp;
    }

    @Column(name = "container_owner_id", nullable = false)
    public long getContainerOwnerId() {
        return containerOwnerId;
    }
    public void setContainerOwnerId(long containerOwnerId) {
        this.containerOwnerId = containerOwnerId;
    }

    @Column(name = "customer_id", nullable = false)
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
                String statusString = true ? ShippingContainer.statusAvailable : ShippingContainer.statusUnavailable;
        return "Shipping Container [container Id=" + containerId + ", container OwnerId=" + containerOwnerId + ", container CustomerId=" + customerId + ", status =" + status + ", status timestamp=" + statusTimestamp + "]";
    }
}
