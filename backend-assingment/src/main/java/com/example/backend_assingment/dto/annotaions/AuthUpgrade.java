package com.example.backend_assingment.dto.annotaions;

import com.example.backend_assingment.dto.Entities.AuthModule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD}) // Can be applied to classes or methods
@Retention(RetentionPolicy.RUNTIME) // Available at runtime
public @interface AuthUpgrade {
    AuthModule module();
    boolean overrideUpgradePermissions() default false;
}
