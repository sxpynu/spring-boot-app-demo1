package ynu.sxp.demo.course.vo;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseVO {
    private UUID id;
    private String code;
    private String name;
}