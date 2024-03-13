package com.perinity.challenge;

import com.perinity.challenge.endpoints.UserController;
import com.perinity.challenge.entities.User;
import com.perinity.challenge.entities.dtos.UserAverageHoursDto;
import com.perinity.challenge.entities.dtos.UserDetailsDto;
import com.perinity.challenge.entities.dtos.UserDto;
import com.perinity.challenge.entities.dtos.UserPeriodDto;
import com.perinity.challenge.repositories.UserRepository;
import com.perinity.challenge.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
public class UserControllerTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddUser() throws Exception {
        UserDto userDto = new UserDto();
        ResponseEntity<UserDto> responseEntity =  userController.addUser(userDto);
        verify(userService, times(1)).createUser(userDto);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

//        System.out.println(ResponseEntity.ok().build());<200 OK OK,[]>
//        System.out.println(responseEntity);<200 OK OK,UserDto(name=null, department_id=null),[]>
//        assertEquals(ResponseEntity.ok().build(), responseEntity.getStatusCode());Actual   :200 OK
    }

    @Test
    void testUpdateUser() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        ResponseEntity<?> responseEntity = userController.updateUser(userId, userDto);
        verify(userService, times(1)).checkAndUpdateUser(userId, userDto);
        assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testRemoveUser() {
        Long userId = 1L;
        ResponseEntity<Void> responseEntity = userController.removeUser(userId);
        verify(userService, times(1)).deleteUser(userId);
        assertEquals(ResponseEntity.noContent().build(), responseEntity);
    }

    @Test
    void testRequestUsersByPeriodAndName() {
        UserPeriodDto userPeriodDto = new UserPeriodDto();
        List<UserAverageHoursDto> userList = Arrays.asList(new UserAverageHoursDto());
        when(userRepository.findUserByNameAndTime(any(), any(), any())).thenReturn(userList);

        ResponseEntity<?> responseEntity = userController.requestUsersByPeriodAndName(userPeriodDto);
        assertEquals(ResponseEntity.ok(userList), responseEntity);
    }

    @Test
    void testRequestUsersHours() {
        List<UserDetailsDto> userList = Arrays.asList(new UserDetailsDto());
        when(userRepository.findUserWithHours()).thenReturn(userList);

        ResponseEntity<?> responseEntity = userController.requestUsersHours();
        assertEquals(ResponseEntity.ok(userList), responseEntity);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(new User());
        when(userRepository.findAll()).thenReturn(userList);

        ResponseEntity<?> responseEntity = userController.getAllUsers();
        assertEquals(ResponseEntity.ok(userList), responseEntity);
    }
}
