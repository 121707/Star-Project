package management_worker.service.Imp;

import management_worker.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class userDetailService implements UserDetailsService {
    @Autowired
    adminServiceImp adminServiceImp;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminServiceImp.getById(Integer.valueOf(s));
        if(admin == null) try {
            throw new Exception("用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询权限,并将该权限作为security的判断参数，也可以是多个
        String adminType = admin.getAdminType();
        List<SimpleGrantedAuthority> collect = new ArrayList<>();
        SimpleGrantedAuthority temp = new SimpleGrantedAuthority(adminType);
        collect.add(temp);
        return   new User(s,admin.getAdminPassword(),collect);

    }
}
