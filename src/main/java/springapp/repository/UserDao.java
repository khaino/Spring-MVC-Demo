package springapp.repository;

import springapp.domain.UserInfo;

public interface UserDao {
	
	public UserInfo getUser(String username);

}
