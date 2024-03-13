package com.perinity.challenge.endpoints;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.UserDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.UserRepository;
import com.perinity.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class UserController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private DepartmentRepository departmentRepository;

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody final UserDto user) throws Exception {
        try {
            userService.createUser(user);
//            final Department d =  departmentRepository.findById(user.getDepartment_id()).get();
//            User u = new User(user.getName(),d);
//            userRepository.save(u);

            return Optional.of(user)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public void getAllUsers() {
        //return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        userService.checkAndUpdateUser(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
//
//@Autowired
//private UserRepository userRepository;
//
//@GetMapping("/getall")
//public ResponseEntity<?> showPeopleList() {
//
//    Object a= new User();
//    Object b=new Job();
//    Object c=new Department();
//    return ResponseEntity.ok(b);
//}
//
//@GetMapping("/get/{id}")
//public ResponseEntity<?> userPeopleById(@PathVariable Long id) {
//    return ResponseEntity.ok(null);
//}
//
//@PutMapping
//@Transactional
//public ResponseEntity<?> updateUser(@RequestBody Object data) {
//    return ResponseEntity.ok(null);
//}
//
//@PostMapping
//@Transactional
//public ResponseEntity<?> addUser(@RequestBody Object data, UriComponentsBuilder uriBuilder) {
//
////            var user = new User(data);
////            userRepository.save(user);
////            var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
//
//    return ResponseEntity.created(null).body(null);
//}
//
//@DeleteMapping("/{id}")
//@Transactional
//public ResponseEntity<?> removeUser(@PathVariable Long id) {
//    return ResponseEntity.noContent().build();
//}
