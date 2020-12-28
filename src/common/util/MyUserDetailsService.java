package common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.entities.Roles;
import com.entities.Users;
import com.services.UserService;


public class MyUserDetailsService implements UserDetailsService {
	@Inject
	private UserService userServiceImpl;

	@PostConstruct
	public void init() {

	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Users user = userServiceImpl.loadUser(username.toUpperCase(), null);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());

		return buildUserForAuthentication(user, authorities);
	}

	// Converts user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
		
		return new User(user.getLoginName(), user.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Roles role) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

//		// Build user's authorities
		
			setAuths.add(new SimpleGrantedAuthority(role.getRoleName()));
		

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	

}
