package net.j7.ebook.persistence;

import net.j7.ebook.entity.User;
import net.j7.ebook.persistence.mappers.UserRowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	

	Optional<User> findByUserName(String userName);
	
	List<User> findByUserNameAndUserType(String userName, String userType);
	
	Stream<User> findByUserNameStartsWith(String userName);
	
	User findByEmail(String email);
	
	@Query("SELECT * FROM USER u WHERE u.email = :email")
	User findByEmailAddress(@Param("email") String email);
	
	/*
	@Query(value="call search_users_proc(:name);")
	List<User> searchUsersByName(@Param("name") String name); */
	
	// Using Custom RowMapper
	@Query(value="call search_users_proc(:name);", rowMapperClass = UserRowMapper.class)
	List<User> searchUsersByName(@Param("name") String name);
	
	@Query(value="call count_search_users_proc(:name, @total);")
	int countSearchUsersByName(@Param("name") String name);
	
	@Modifying
	@Query("UPDATE USER SET USER_NAME=:name WHERE ID=:id")
	boolean updateUserName(@Param("name") String name, @Param("id") Long id);
	
}
