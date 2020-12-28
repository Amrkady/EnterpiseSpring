package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Users;

public class UserServiceImpl implements UserService {
	private CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public boolean addUser(Users user) {
		return commonDao.saveObject(user);

	}

	@Override
	public boolean updateUser(Users user) {
		return commonDao.updateObject(user);
	}

	@Override
	public boolean deleteUser(Users user) {
		return commonDao.deleteObject(user);
	}

	@Override
	public List<Users> getAllUser() {
		List usrs = commonDao.findAll(Users.class);
		return usrs;
	}
	
	@Override
	public Users loadUser(String username, String passWord) {
		return commonDao.loadUser(username, passWord);
	}

	@Override
	public Users findUserById(Integer usrId) {
		return (Users)commonDao.findEntityById(Users.class, usrId);

	}

}
