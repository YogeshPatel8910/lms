package com.example.proj.dto;

import com.example.proj.model.ERole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

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
