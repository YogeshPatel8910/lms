package com.example.proj.service;

import com.example.proj.dto.UserDTO;
import com.example.proj.model.User;
import com.example.proj.repositry.InstructorRepositry;
import com.example.proj.repositry.StudentRepositry;
import com.example.proj.repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("adminService")
public class AdminService implements UserService{

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private StudentRepositry studentRepositry;

    @Autowired
    private InstructorRepositry instructorRepositry;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        User savedUsed = userRepositry.save(user);
        return mapToDTO(savedUsed);
    }
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
    public Page<User> getUsers(int page, int size,String sortBy,String direction){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepositry.findAll(pageable);
    }

    public boolean deleteUser(long id){
        Optional<User> user = userRepositry.findById(id);
        if(user.isPresent()) {
            int role = user.get().getRole().ordinal();
            switch (role) {
                case 0:
                    userRepositry.deleteById(id);
                    break;
                case 1:
                    instructorRepositry.deleteById(id);
                    break;
                case 2:
                    studentRepositry.deleteById(id);
                    break;
                default:
                    break;
            }
            return true;
        }
        else
            return false;
    }
}
