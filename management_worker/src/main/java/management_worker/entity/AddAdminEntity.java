package management_worker.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddAdminEntity {
    private Integer employeeId;

    private Integer registerId;

    private String adminPassword;

    private String phone;

    private String adminType;

    private String adminStatus;
}
