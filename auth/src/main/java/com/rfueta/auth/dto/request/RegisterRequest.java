package com.rfueta.auth.dto.request;

import com.rfueta.auth.validation.PasswordMatches;
import com.rfueta.auth.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class RegisterRequest {

    @NotBlank(message = "{auth.username.notblank}")
    @Size(min = 4, max = 30, message = "{auth.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{auth.username.pattern}")
    private String username;

    @NotBlank(message = "{auth.email.notblank}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @NotBlank(message = "{auth.password.notblank}")
    @ValidPassword
    private String password;

    @NotBlank(message = "{auth.confirmPassword.notblank}")
    private String confirmPassword;
}