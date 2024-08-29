package helper.helperapi.entity;


import lombok.Data;
import org.hibernate.annotations.Subselect;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Data
@Entity
@Subselect("SELECT * FROM t_user")
public class LoginViewFromEXUser implements UserDetails {

    private static final long serialVersionUID = -1277212490254819778L;

    @Id
//	@GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "userid", unique = true)
    private String userid;

    @Column(name = "password")
    private String password;

    @Column(name = "usertype")
    private int usertype;
//
//    @Column(name = "appid")
//    private int appId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
