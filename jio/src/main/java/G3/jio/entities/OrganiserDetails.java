// package G3.jio.entities;

// import java.util.Arrays;
// import java.util.Collection;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// public class OrganiserDetails implements UserDetails {
// private Organiser organiser;

// @Autowired
// public OrganiserDetails(Organiser organiser) {
// this.organiser = organiser;
// }

// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
// return Arrays.asList(new SimpleGrantedAuthority(organiser.getAUTHORITY()));
// }

// @Override
// public String getPassword() {
// return organiser.getPassword();
// }

// @Override
// public String getUsername() {
// return organiser.getUsername();
// }

// @Override
// public boolean isAccountNonExpired() {
// return true;
// }

// @Override
// public boolean isAccountNonLocked() {
// return true;
// }

// @Override
// public boolean isCredentialsNonExpired() {
// return true;
// }

// @Override
// public boolean isEnabled() {
// return true;
// }
// }
