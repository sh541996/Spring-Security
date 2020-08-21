package auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import security.demo.security.ApplicationUserRole;

@Repository("fake")
public class FakeApplicationUserService implements ApplicationUserDao{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserService(PasswordEncoder passwordEncoder) {
		
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		
		return getApplicationUsers()
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
				
	}

	private List<ApplicationUser> getApplicationUsers() {
		
		List<ApplicationUser> applicationUser = Lists.newArrayList(
				new ApplicationUser(
						"shubham", 
						passwordEncoder.encode("123"),
						ApplicationUserRole.STUDENT.getGrantedAuthorities(), 
						true, 
						true,
						true,
						true),
				new ApplicationUser(
						"linda", 
						passwordEncoder.encode("lily"),
						ApplicationUserRole.ADMIN.getGrantedAuthorities(), 
						true, 
						true,
						true,
						true),
				new ApplicationUser(
						"TOM", 
						passwordEncoder.encode("TOM123"),
						ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(), 
						true, 
						true,
						true,
						true)
				);
		
		return applicationUser;
	}
}
