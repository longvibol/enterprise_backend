package com.pisethjavaschool.platform.exception.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import com.pisethjavaschool.platform.exception.GlobalExceptionHandler;

@AutoConfiguration
@Import({GlobalExceptionHandler.class})
public class PlatformExceptionAutoConfiguration {}