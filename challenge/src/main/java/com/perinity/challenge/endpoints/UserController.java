package com.perinity.challenge.endpoints;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.UserAverageHoursDto;
import com.perinity.challenge.entities.dtos.UserDetailsDto;
import com.perinity.challenge.entities.dtos.UserDto;
import com.perinity.challenge.entities.dtos.UserPeriodDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.UserRepository;
import com.perinity.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/pessoas")
public class UserController {
    @Autowired
    private UserRepository userRepository;
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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Exception {
        userService.checkAndUpdateUser(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gastos")
    public ResponseEntity<?> requestUsersByPeriodAndName(@RequestBody UserPeriodDto userPeriodDto) {
        List<UserAverageHoursDto> listUsersOnTime = userRepository.findUserByNameAndTime(userPeriodDto.getName(),userPeriodDto.getStartDate(),userPeriodDto.getEndDate());
        return ResponseEntity.ok(listUsersOnTime);
    }
    @GetMapping
    public ResponseEntity<?> requestUsersHours() {
        List<UserDetailsDto> listUsers =userRepository.findUserWithHours();
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> l =userRepository.findAll();//stackoverflow need fix
        return ResponseEntity.ok(l);
    }
}