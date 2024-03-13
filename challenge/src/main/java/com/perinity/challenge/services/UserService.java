package com.perinity.challenge.services;

import com.perinity.challenge.entities.Department;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.UserDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    // Remove a person

    @Autowired
    private DepartmentRepository departmentRepository;

    public void createUser(UserDto user){
        final Department d =  departmentRepository.findById(user.getDepartment_id()).get();
        User u = new User(user.getName(),d);
        userRepository.save(u);

    }
    public boolean checkAndUpdateUser(Long id, UserDto userDto) throws Exception {
        boolean alter=false;
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Person not found with id: " + id));

        if(!userDto.getName().equals(user.getName())){
            alter=true;
            user.setName(userDto.getName());
        }
        if(!userDto.getDepartment_id().equals(user.getDepartment().getId())){
            alter=true;
            Department d =departmentRepository.findById(userDto.getDepartment_id()).orElseThrow(()->new Exception("department not found"));
            user.setDepartment(d);
        }
        if(alter){
            userRepository.save(user);
        }
         return alter;

    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
