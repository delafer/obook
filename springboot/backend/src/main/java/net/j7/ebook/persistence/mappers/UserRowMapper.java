package net.j7.ebook.persistence.mappers;

import net.j7.ebook.entity.User;
import net.j7.ebook.entity.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User user = new User();
		user.setId(rs.getLong("ID"));
		user.setUserName(rs.getString("USER_NAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setEmail(rs.getString("EMAIL"));
		user.setCreatedTime(rs.getDate("CREATED_TIME"));
		user.setUpdatedTime(rs.getDate("UPDATED_TIME"));
		user.setUserType(UserType.valueOf(rs.getString("USER_TYPE")));
		user.setDateofBirth(rs.getDate("DOB"));

		return user;
	}
}