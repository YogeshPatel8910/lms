package com.example.proj.dto;

import com.example.proj.model.ERole;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Type;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "role",
        visible = true

)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserDTO.class,name = "ADMIN"),
        @JsonSubTypes.Type(value = StudentDTO.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = InstructorDTO.class, name = "INSTRUCTOR"),
})
public class UserDTO{

    private Long id;

    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private ERole role;

}
