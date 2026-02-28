package com.rfueta.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.password")
public class PasswordSecurityProperties {
    
    private int minLength;
    private int maxLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecial;
}
