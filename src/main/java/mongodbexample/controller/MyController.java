package mongodbexample.controller;

import mongodbexample.exception.ResourceNotFoundException;
import mongodbexample.models.Student;
import mongodbexample.rep.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class MyController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/")
    public ResponseEntity<?> addStudent(@RequestBody Student st)
    {
         Student save =  studentRepository.save(st);
         return ResponseEntity.ok(save);
    }

    @GetMapping ("/")
    public ResponseEntity<?> getStudents()
    {

        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    // get employee by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        Student st = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student not exist with id :" + id));
        return ResponseEntity.ok(st);
    }

    // update employee rest api

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails){
        Student st = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student not exist with id :" + id));


        st.setId(studentDetails.getId());
        st.setName(studentDetails.getName());
        st.setCity(studentDetails.getCity());
        st.setCollege(studentDetails.getCollege());

        Student updatedStudent = studentRepository.save(st);
        return ResponseEntity.ok(updatedStudent);
    }

    // delete student rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer id){
        Student st = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student does not exist with id :" + id));

        studentRepository.delete(st);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }




}
