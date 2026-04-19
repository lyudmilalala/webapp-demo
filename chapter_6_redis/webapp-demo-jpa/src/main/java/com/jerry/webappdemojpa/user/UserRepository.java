package com.jerry.webappdemojpa.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>, JpaSpecificationExecutor<UserEntity> {

	UserEntity findById(long i);
	
	@Query(value = "SELECT u FROM UserEntity u WHERE u.ustatus = 'ACTIVE' AND u.password = ?3 AND (u.uname = ?1 OR u.phone = ?2 OR u.email = ?2)")
	List<UserEntity> findActiveUserByLoginNameAndPassowrd(String name, String info, String password);

	UserEntity findByUnameAndUstatus(String name, String ustatus);

	UserEntity findByUnameAndUstatusNot(String name, String ustatus);

	List<UserEntity> findByUstatus(String ustatus);

	List<UserEntity> findByUstatusNotAndUnameStartsWith(String ustatus, String prefix);

	List<UserEntity> findByUstatusAndUnameStartsWith(String ustatus, String prefix);

	UserEntity findByUnameAndPasswordAndUstatusNot(String name, String password, String ustatus);

	UserEntity findByPhone(String phone);

	List<UserEntity> findByUstatusNotAndIsVirtualAndSignUpTimeGreaterThanEqualAndSignUpTimeLessThan(String ustatus, String isVirtual, Date signUpTime, Date signUpTime2);

	UserEntity findByPhoneAndUstatusNot(String phone, String ustatus);

	UserEntity findByPhoneAndUstatus(String phone, String ustatus);

	UserEntity findByEmail(String email);

	UserEntity findByEmailAndUstatusNot(String email, String ustatus);

	UserEntity findByEmailAndUstatus(String email, String ustatus);
	
	Page<UserEntity> findByUstatusNotOrderBySignUpTimeDesc(String ustatus, Pageable pageable);
	
	// find by the wechat unique openId
	UserEntity findByWechat(String wechat);

	UserEntity findByWechatAndUstatusNot(String wechat, String ustatus);

	@Transactional
	void deleteById(long uid);

	UserEntity findByUnameAndUstatusIn(String uname, Collection<String> ustatuses);
}
