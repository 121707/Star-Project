package user_shop.demo.service.Imp;


import com.example.common1.security.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user_shop.demo.entity.User;


import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class userDetailService implements UserDetailsService {
    @Autowired
    UserServiceImp imp;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       User user = imp.getById(Integer.valueOf(s));

        com.example.common1.security.entity.User user1 = new com.example.common1.security.entity.User();
        user1.setUsername(String.valueOf(user.getUserId()));
        user1.setPassword("{bcrypt}" + user.getUserPassword());
        //查询权限,并将该权限作为security的判断参数，也可以是多个
//        String Type = user.getUserType();
//        List<SimpleGrantedAuthority> collect = new ArrayList<>();
//        SimpleGrantedAuthority temp = new SimpleGrantedAuthority(Type);
//        collect.add(temp);

        SecurityUser securityUser = new SecurityUser();;
        securityUser.setCurrentUserInfo(user1);
        securityUser.setPermissionValueList(new ArrayList<>());

        return securityUser;

    }
}
