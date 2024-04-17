package ynu.sxp.demo.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ynu.sxp.demo.course.entity.CourseEntity;
import ynu.sxp.demo.course.repository.CourseRepository;
import ynu.sxp.demo.course.vo.CourseRO;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseEntity addCourse(CourseRO ro) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCode(ro.getCode());
        courseEntity.setName(ro.getName());
        return courseRepository.save(courseEntity);
    }

    @Transactional
    public void deleteCourseById(UUID courseId) {
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public CourseEntity updateCourse(CourseRO ro) {
        CourseEntity courseEntity = courseRepository.findById(ro.getId()).orElseThrow();
        courseEntity.setCode(ro.getCode());
        courseEntity.setName(ro.getName());
        return courseRepository.save(courseEntity);
    }

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }
}