package com.personalproj.amaterasuhotel.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
