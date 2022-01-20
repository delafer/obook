package net.j7.ebook.persistence;

import net.j7.ebook.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserPaginationAndSortRepository extends PagingAndSortingRepository<User, Long>{
	
	Optional<User> findByUserName(String userName);
	
	List<User> findByUserNameAndUserType(String userName, String userType);
	
	List<User> findByEmail(String email);

	//boolean hasStrongPassword(String name);
	
	//int sampleNamedQuery(Long id);
	
	@Query("SELECT USER_NAME FROM USER WHERE ID=:id")
	String getUserNameById(Long id);
	
	/* query method using pageable */
	List<User> findByUserType(String userType, Pageable pageable);
	
	/* query method using Sort */
	List<User> findByUserType(String userType, Sort sort);
	
}
