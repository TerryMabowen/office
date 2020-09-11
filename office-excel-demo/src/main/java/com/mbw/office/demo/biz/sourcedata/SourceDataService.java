package com.mbw.office.demo.biz.sourcedata;

import com.google.common.collect.Lists;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.demo.model.Course;
import com.mbw.office.demo.model.Student;
import com.mbw.office.demo.model.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-09-11 17:20
 */
@Slf4j
@Service
public class SourceDataService {

    public List<Course> listCourses() {
        String prefix = "2020-09-";
        List<Course> courses = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            for (int i1 = 0; i1 < 30; i1++) {
                Course course = new Course();
                course.setCourseName("课程" + (i + 1));
                course.setAttendTime(DateUtil.parseShort(prefix + (i + 1)));
                course.setClassroom("三年级" + (i + 1) + "班");

                Teacher teacher = new Teacher();
                teacher.setName("老师" + (i + 1));
                if (i % 2 == 0) {
                    teacher.setGender(1);
                } else {
                    teacher.setGender(0);
                }
                course.setTeacher(teacher);

                List<Student> students = Lists.newArrayList();

                Student student = new Student();
                student.setName("学生" + (i1 + 1));
                student.setAge(((i1 % 2) + 10));
                if (i1 % 2 == 0) {
                    student.setGender(1);
                } else {
                    student.setGender(0);
                }
                students.add(student);

                course.setStudents(students);
                courses.add(course);
            }
        }
        return courses;
    }
}
