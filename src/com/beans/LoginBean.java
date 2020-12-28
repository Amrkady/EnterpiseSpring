package com.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.entities.Users;
import com.services.UserService;

import common.util.Utils;



@ManagedBean
@SessionScoped
public class LoginBean {
	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;

	private String userName;
	private String password;
	private Users currentUser;
	private boolean flag=true;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		String outcome = "";
		try {
			Authentication result = null;
			if (this.userName.length() == 0 || this.password.length() == 0) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						Utils.loadMessagesFromFile("error.login"), "");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "";
			}
			Users uc = userServiceImpl.loadUser(this.userName.toUpperCase(), this.password);
			Authentication request = new UsernamePasswordAuthenticationToken(uc.getLoginName().toUpperCase(),
					uc.getPassword());
			result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			if (result.isAuthenticated()) {
				
					FacesContext context = FacesContext.getCurrentInstance();
					setCurrentUser(uc);
					context.getExternalContext().getSessionMap().put("user", uc);
					Users uss=(Users)context.getExternalContext().getSessionMap().get("user");
					System.out.println(uss.getRole().getRoleName());
					flag=false;
				outcome = "home";
				
				
			}
		} catch (BadCredentialsException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Utils.loadMessagesFromFile("error.login"),
					"");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return outcome;
	}
	
	public String logout() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		SecurityContextHolder.clearContext();
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		flag=true;
		return "login";
	}
	
	
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public Users getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}
	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}
	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}


}
