package com.example.proj.dto;

import com.example.proj.model.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Type;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"

)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserDTO.class,name = "ADMIN"),
        @JsonSubTypes.Type(value = StudentDTO.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = InstructorDTO.class, name = "instructor"),
})
public class UserDTO{

    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private ERole role;

}
