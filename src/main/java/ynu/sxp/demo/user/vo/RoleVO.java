package ynu.sxp.demo.user.vo;


import lombok.Data;

import java.util.UUID;

@Data
public class RoleVO {
    public UUID id;
    public String code;
    public String name;
}

