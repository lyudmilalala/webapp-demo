package com.jerry.webappdemojpa.paydevDB;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "coupon", uniqueConstraints=@UniqueConstraint(name="unique_coupon_idx", columnNames={"coupon_code"}))
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @Column(name = "pay_account_id", nullable = false)
    private Long payAccountId;

    @Column(name = "assigner", nullable = false)
    private String assigner;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "create_time", nullable = false,  columnDefinition = "datetime(3)")
    private Date createTime;

    @Column(name = "latest_modification_time", nullable = false, columnDefinition = "datetime(3)")
    private Date latestModificationTime;

    @Column(name = "used", nullable = false, length=1)
    private String used;

    @Column(name = "detail")
    private String detail; // if type = cash, include how much has been used

    @Column(name = "inactivate_time", columnDefinition = "datetime(3)")
    private Date inactivateTime;

    public CouponEntity() {}

    public CouponEntity(String couponCode, Long payAccountId, String assigner) {
        this.couponCode = couponCode;
        this.payAccountId = payAccountId;
        this.assigner = assigner;
        this.createTime = new Date();
        this.latestModificationTime = new Date();
        // not store inactive or outdated in db, get these status by status and startDay/endDay in business logic
        this.status = "ACTIVE";
        used = "N";
    }

    public boolean getUsed() {
        return used.equals("Y");
    }

    public void setUsed(boolean isUsed) {
        used = isUsed? "Y":"N";
    }


    @PreUpdate
    private void doPreUpdate() {
        latestModificationTime = new Date();
    }

}
