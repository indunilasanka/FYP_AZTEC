package aztec.rbir_rest2.service;
import org.springframework.security.core.userdetails.UserDetails;

import aztec.rbir_database.Entities.UserRole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserDetails implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1562851049468104105L;
	private String userName;
    private String password;
    private String email;
    private byte[] image;
    List<GrantedAuthority> authorities = new ArrayList<>();
    
    public List<GrantedAuthority> getAuthorities() {  return authorities;}
    
    //List<GrantedAuthority> authorities = new ArrayList<>();
    
    public String getPassword() {  return this.password; }
    public String getUsername() {  return this.userName; }
    public boolean isAccountNonExpired() { return true;}
    public boolean isAccountNonLocked() { return true; }
    public boolean isCredentialsNonExpired() {return true; }
    public boolean isEnabled() {
        // We don't have an enabled/active field in our DB, all users are always enabled.
        return true;
    }
 
    public void setCustomerAuthorities(List<UserRole> userRoles) {
		for(UserRole userRole:userRoles){
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
		}
    }
    
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	
    
}