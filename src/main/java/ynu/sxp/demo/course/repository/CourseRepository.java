package ynu.sxp.demo.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ynu.sxp.demo.course.entity.CourseEntity;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}