package user_shop.demo.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserInfo {
    private Integer name;
    private String email;
}
