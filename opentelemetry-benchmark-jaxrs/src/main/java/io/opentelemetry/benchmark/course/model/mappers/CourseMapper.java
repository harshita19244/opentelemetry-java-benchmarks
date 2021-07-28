package io.opentelemetry.benchmark.course.model.mappers;

import io.opentelemetry.benchmark.course.model.entities.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("INSERT INTO COURSES(TITLE, LEVEL, HOURS, TEACHER, STATE) VALUES(#{title}, #{level}, #{numberOfHours}, #{teacher}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    public int insert(Course course);

    @Update("UPDATE COURSES SET TITLE = #{title}, LEVEL = #{level}, HOURS = #{numberOfHours}, TEACHER = #{teacher}, STATE = #{state} WHERE ID=#{id}")
    public int update(Course course);

    @Delete("DELETE FROM COURSES WHERE ID=#{id}")
    public int deleteById(Long id);

    @Select("SELECT c.ID, c.TITLE, c.LEVEL, c.HOURS, c.TEACHER, c.STATE FROM COURSES c")
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "level", column = "LEVEL"),
            @Result(property = "numberOfHours", column = "HOURS"),
            @Result(property = "teacher", column = "TEACHER"),
            @Result(property = "state", column = "STATE")
    })
    public List<Course> getAll();

    @Select("SELECT c.ID, c.TITLE, c.LEVEL, c.HOURS, c.TEACHER, c.STATE FROM COURSES c WHERE c.ID = #{id}")
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "title", column = "TITLE"),
            @Result(property = "level", column = "LEVEL"),
            @Result(property = "numberOfHours", column = "HOURS"),
            @Result(property = "teacher", column = "TEACHER"),
            @Result(property = "state", column = "STATE")
    })
    public Course getById(@Param("id") Long id);

}
