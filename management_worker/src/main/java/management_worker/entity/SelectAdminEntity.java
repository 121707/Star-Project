package management_worker.entity;

import lombok.Data;
//admin条件查询的变量封装类
@Data
public class SelectAdminEntity {
    private Integer adminId;

    private Integer employeeId;

    private Integer registerId;

    private String adminType;

    private String adminStatus;

    private String lastLoginTimeFirst;
    private String lastLoginTimeLast;

    private String registerTimeFirst;
    private String registerTimeLast;


}
