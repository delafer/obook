package net.j7.ebook.api.ws;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@Slf4j
public class StudentThrowSocketController {

//    private final StudentService studentService;
//
//    @SubscribeMapping("/students/get")
//    public List<Student> findAllStudents(){
//        return studentService.readAllStudents();
//    }
//
//    @SubscribeMapping("/students/{id}/get")
//    public Student findById(@DestinationVariable Long id, Principal principal) throws NotFoundException {
//        return studentService.readStudentById(id);
//    }
//
//    @MessageMapping("/students/create")
//    @SendTo("/students/created")
//    public Student saveStudent(Student student){
//        return studentService.createStudent(student);
//    }
//
//    @MessageExceptionHandler
//    @SendToUser("/students/error")
//    public String handleException(NotFoundException ex){
//        log.info("Student not found");
//        return "The requested student was not found";
//    }

}
