package springapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import springapp.domain.UserInfo;

public class JdbcUserDao extends SimpleJdbcDaoSupport implements UserDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	public UserInfo getUser(String username) {
		logger.info("Getting gser!");
		UserInfo user = getSimpleJdbcTemplate().queryForObject(
				"select username, password, role from user_table where username=?", 
				new UserMapper(),
				new Object[] { username });

		return user;
	}

	private static class UserMapper implements ParameterizedRowMapper<UserInfo> {

		public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserInfo user = new UserInfo();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			return user;
		}

	}

}