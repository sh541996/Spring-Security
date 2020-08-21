package security.demo.security;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
public enum ApplicationUserRole {
	
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ,
			ApplicationUserPermission.STUDENT_WRITE,
			ApplicationUserPermission.COURSE_READ,
			ApplicationUserPermission.COURSE_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ,
			ApplicationUserPermission.COURSE_READ));

	private final Set<ApplicationUserPermission> permission;
	
	ApplicationUserRole(HashSet<ApplicationUserPermission> permission) {
		
		this.permission = permission;
		
	}

	public Set<ApplicationUserPermission> getPermission() {
		return permission;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
		Set<SimpleGrantedAuthority> permissions = getPermission().stream()
		.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
		.collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		
		return permissions;
		
	}
	
	

}
