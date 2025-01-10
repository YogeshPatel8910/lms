package com.example.proj.controller;

import com.example.proj.model.User;
import com.example.proj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                  @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                  @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(adminService.getUsers(page, size , sortBy , direction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        if (adminService.deleteUser(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
