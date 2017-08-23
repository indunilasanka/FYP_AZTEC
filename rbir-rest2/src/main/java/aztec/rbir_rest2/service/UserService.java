package aztec.rbir_rest2.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import aztec.rbir_database.Entities.User;
import aztec.rbir_database.service.UserDataService;

@Component
public class UserService implements UserDetailsService {

	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		//userData =  new UserDataService();
		User user = UserDataService.retrieveFromUserName(arg0);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		
		authorities.add(new SimpleGrantedAuthority("NORMAL_USER"));
		
		
		
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(),user.getPassword(), authorities);
		
		
		return userDetails;
	}

}
