package io.opentelemetry.benchmark.course.model.services.mybatis;

import io.opentelemetry.benchmark.course.model.entities.Course;
import io.opentelemetry.benchmark.course.model.mappers.CourseMapper;
import io.opentelemetry.benchmark.course.model.services.CourseService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MyBatisCourseService implements CourseService {

    private CourseMapper courseMapper;

    public MyBatisCourseService(final CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public Course create(Course course) {
        this.courseMapper.insert(course);
        return course;
    }

    @Override
    public Course update(Long id, Course course) throws EntityNotFoundException {
        Course courseBD = this.courseMapper.getById(id);
        if (courseBD == null) {
            throw new EntityNotFoundException("Course not found");
        }
        courseBD.setTitle(course.getTitle());
        courseBD.setLevel(course.getLevel());
        courseBD.setNumberOfHours(course.getNumberOfHours());
        courseBD.setTeacher(course.getTeacher());
        courseBD.setState(course.getState());

        this.courseMapper.update(courseBD);
        return courseBD;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (this.courseMapper.getById(id) == null) {
            throw new EntityNotFoundException("Course not found");
        }
        this.courseMapper.deleteById(id);
    }

    @Override
    public List<Course> findAll() {
        return this.courseMapper.getAll();
    }

    @Override
    public Course findOne(Long id) throws EntityNotFoundException {
        Course course = this.courseMapper.getById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }
        return course;
    }

}
