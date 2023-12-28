package com.ng.websculpture.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Configuration
@ComponentScan({"com.ng.websculpture.*"})
@Import({RouterConfig.class})
public class ApplicationConfig {


}
