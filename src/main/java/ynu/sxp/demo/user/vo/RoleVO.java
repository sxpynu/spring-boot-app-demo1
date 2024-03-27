package ynu.sxp.demo.user.vo;


import lombok.Data;

import java.util.UUID;

@Data
public class RoleVO {
    private UUID id;
    private String code;
    private String name;
}

