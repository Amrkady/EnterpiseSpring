package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.common.Constant;
import com.entities.Users;
import com.services.DepartmentService;
import com.services.UserService;

import common.util.Utils;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Users> users;
	private Users user;
	private Users currUser;
	private Users usr = new Users();

	private boolean MNG = false;

	@PostConstruct
	public void init() {
		user = new Users();

		users = userServiceImpl.getAllUser();
		currUser = Utils.findCurrentUser();
	}

	public String addUser() {
		try {
			if (MNG) {
				usr.setManager(1);
				usr.setRoleId(Constant.ROLE_ADMIN);
			} else {
				usr.setManager(0);
				usr.setRoleId(Constant.ROLE_USER);
			}
			usr.setLoginName(usr.getLoginName().toUpperCase());
			userServiceImpl.addUser(usr);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("success.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			users = userServiceImpl.getAllUser();
			Utils.closeDialog("whsdlAdd");
			usr = new Users();
			init();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("error.operation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}
		return "";
	}

	public String deleteUser(Users userD) {
		if (userD != null) {
			try {
				userServiceImpl.deleteUser(userD);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						Utils.loadMessagesFromFile("success.delete"), "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				users = userServiceImpl.getAllUser();

			} catch (Exception e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Utils.loadMessagesFromFile("error.delete"), "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				e.printStackTrace();
			}
		}
		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			user = (Users) event.getObject();
			user.setLoginName(user.getLoginName().toUpperCase());
			userServiceImpl.updateUser(user);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("success.update"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// users = userServiceImpl.getAllUser();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error.update"),
					"");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			e.printStackTrace();
		}

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Utils.loadMessagesFromFile("error.update"),
				"");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void showdlAdd() {
		Utils.openDialog("whsdlAdd");

	}

	public void update() {
		init();
		usr = new Users();
	}

	public void editUser() {
		if (currUser.getPhone().length() == 10) {
			userServiceImpl.updateUser(currUser);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					Utils.loadMessagesFromFile("success.update"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Utils.loadMessagesFromFile("phone.validation"), "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public boolean isMNG() {
		return MNG;
	}

	public void setMNG(boolean mNG) {
		MNG = mNG;
	}

	public Users getUsr() {
		return usr;
	}

	public void setUsr(Users usr) {
		this.usr = usr;
	}

	public Users getCurrUser() {
		return currUser;
	}

	public void setCurrUser(Users currUser) {
		this.currUser = currUser;
	}

}
