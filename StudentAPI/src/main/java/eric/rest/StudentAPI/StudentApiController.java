package eric.rest.StudentAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    private static final List<Student> students = new ArrayList<>();
    private static Integer currStudentId = 100;

    static {
        students.add(new Student(currStudentId++, "Eric"));
        students.add(new Student(currStudentId++, "Patou"));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        if(students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable("id") Integer id) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findAny();
        return student
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        Student newStudent = new Student(currStudentId++, student.getName());
        students.add(newStudent);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") Integer id, @RequestBody Student student) {
        Optional<Student> studentToUpdate = students.stream()
                .filter(s -> s.getId().equals(id))
                .findAny();
        if(studentToUpdate.isPresent()) {
            int index = students.indexOf(studentToUpdate.get());
            Student UpdatedStudent = new Student(id, student.getName());
            students.set(index, UpdatedStudent);
            return new ResponseEntity<>(UpdatedStudent, HttpStatus.OK);
        } else {
            Student newStudent = new Student(currStudentId++, student.getName());
            students.add(newStudent);
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete(@PathVariable("id") Integer id) {
        Iterator<Student> iter = students.iterator();
        boolean found = false;
        while(iter.hasNext()) {
            Student curStudent = iter.next();
            if(curStudent.getId().equals(id)) {
                iter.remove();
                found = true;
                break;
            }
        }
        if(found) {
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }
}
