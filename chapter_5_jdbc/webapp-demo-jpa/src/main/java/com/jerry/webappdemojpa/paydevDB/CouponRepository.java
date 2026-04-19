package com.jerry.webappdemojpa.paydevDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,Long>, JpaSpecificationExecutor<CouponEntity>  {

    Optional<CouponEntity> findByCouponCode(String couponCode);

    List<CouponEntity> findByStatus(String status);

    List<CouponEntity> findByStatusNotAndCouponCodeIn(String status, List<String> couponCodeList);

    List<CouponEntity> findByIdInAndStatusNot(List<Long> couponIdList, String status);

    void deleteAllByIdIn(List<Long> couponIdList);
}
