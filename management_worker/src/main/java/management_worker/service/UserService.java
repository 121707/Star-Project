package management_worker.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import management_worker.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService extends IService<User> {

    public String removeById(int userid);

    public String update(User user);

    public Page<User> SelectBySome(Integer uid, String uname, String utype, String ustatus,
                                   String ltf, String ltl, String rtf, String rtl , int curpage);

    public String updateAvatar(String src,int userId);

    public void replaceFile(String oldUrl,String newUrl,MultipartFile avatar) throws IOException;

    public String getAvatarUrlById(Integer userId);

    public String adduser(User user);
}
