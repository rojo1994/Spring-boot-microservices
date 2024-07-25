package com.microservice.course.service;

import com.microservice.course.client.StudentClient;
import com.microservice.course.dto.StudentDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;
import com.microservice.course.persistance.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CouseServiceImpl implements ICourseService{

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) this.coursesRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return this.coursesRepository.findById(id).orElseThrow(()-> new RuntimeException("Curso no encontrado"));
    }

    @Override
    public void save(Course course) {
        this.coursesRepository.save(course);
    }

    @Override
    public StudentByCourseResponse findStudentsByIdCourse(Long idCourse) {
        Course course = this.coursesRepository.findById(idCourse).orElse(new Course());

        //Obtener los estudiantes
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(idCourse);
        return StudentByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .StudentDTOList(studentDTOList)
                .build();
    }
}
