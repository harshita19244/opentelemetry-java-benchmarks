package io.opentelemetry.benchmark.course.resources;

import io.opentelemetry.benchmark.course.model.entities.Course;
import io.opentelemetry.benchmark.course.model.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private CourseService courseService;

    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }

    @POST
    public Response create(Course course) {
        if (course.getTitle() == null || course.getLevel() == null || course.getTeacher() == null || course.getState() == null) {
            return Response.status(500).entity("Validation exception").build();
        }
        return Response.ok().entity(courseService.create(course)).build();
    }

    @GET
    public Response getAll() {
        return Response.ok().entity(courseService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Course course = null;
        try {
            course = courseService.findOne(id);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return Response.status(404).build();
        }
        return Response.ok().entity(course).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Course course) {
        Course courseUpdated = null;
        try {
            if (course.getTitle() == null || course.getLevel() == null || course.getTeacher() == null || course.getState() == null) {
                return Response.status(500).entity("Validation exception").build();
            }
            courseUpdated = courseService.update(id, course);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return Response.status(404).build();
        }
        return Response.ok().entity(courseUpdated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            courseService.delete(id);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return Response.status(404).build();
        }
        return Response.ok().entity("Course deleted").build();
    }
}
