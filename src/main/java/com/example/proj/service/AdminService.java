package com.example.proj.service;

import com.example.proj.dto.UserDTO;
import com.example.proj.model.User;
import com.example.proj.repositry.InstructorRepositry;
import com.example.proj.repositry.StudentRepositry;
import com.example.proj.repositry.UserRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminService implements UserService{

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private StudentRepositry studentRepositry;

    @Autowired
    private InstructorRepositry instructorRepositry;

    public UserDTO createUser(UserDTO userDTO) {
        User user = GenericObjectMapper.map(userDTO,User.class);
        User savedUsed = userRepositry.save(user);
        return mapToDTO(savedUsed);
    }

    public Page<UserDTO> getUsers(int page, int size,String sortBy,String direction){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepositry.findAll(pageable).map(this::mapToDTO);
    }

    public boolean deleteUser(long id){
        User user = userRepositry.findById(id).orElse(null);
        if(user!=null) {
            int role = user.getRole().ordinal();
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

    public UserDTO getUserById(long id) {
        return userRepositry.findById(id).map(this::mapToDTO).orElse(null);
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

}
