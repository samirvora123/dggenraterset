package com.bonrix.dggenraterset.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bonrix.dggenraterset.Model.User;

@Repository
public interface UserRepository extends BaseRepository<User, String>{
	@Query("from User where username=:username")
	public User findByUserName(@Param("username") String username);
}
