package io.opentelemetry.benchmark.course.model.services;

import io.opentelemetry.benchmark.course.model.entities.Course;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CourseService {

    Course create(Course course);

    Course update(Long id, Course course) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    List<Course> findAll();

    Course findOne(Long id) throws EntityNotFoundException;
}
