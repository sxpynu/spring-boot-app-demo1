package ynu.sxp.demo.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ynu.sxp.demo.common.BaseController;
import ynu.sxp.demo.course.vo.CourseRO;
import ynu.sxp.demo.course.service.CourseService;
import ynu.sxp.demo.course.vo.CourseVO;

import java.util.List;
import java.util.UUID;

@PreAuthorize("hasRole('teacher')")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("course")
@Tag(name = "课程管理", description = "课程的增删改查")
public class CourseController extends BaseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "新增课程")
    @PostMapping
    public CourseVO addCourse(@Valid @RequestBody CourseRO ro) {
        var newCourse = courseService.addCourse(ro);
        return modelMapper.map(newCourse, CourseVO.class);
    }

    @Operation(summary = "删除课程")
    @DeleteMapping("{courseId}")
    public boolean deleteCourse(@PathVariable UUID courseId) {
        courseService.deleteCourseById(courseId);
        return true;
    }

    @Operation(summary = "更新课程")
    @PutMapping
    public CourseVO updateCourse(@Valid @RequestBody CourseRO ro) {
        var updatedCourse = courseService.updateCourse(ro);
        return modelMapper.map(updatedCourse, CourseVO.class);
    }

    @Operation(summary = "获取所有课程")
    @GetMapping("list")
    public List<CourseVO> listAllCourses() {
        var allCourses = courseService.getAllCourses();
        return allCourses.stream().map(courseEntity -> modelMapper.map(courseEntity, CourseVO.class)).toList();
    }

    @Operation(summary = "获取单个课程")
    @GetMapping("{courseId}")
    public CourseVO getCourse(@PathVariable UUID courseId) {
        var course = courseService.getCourseById(courseId);
        return modelMapper.map(course, CourseVO.class);
    }
}