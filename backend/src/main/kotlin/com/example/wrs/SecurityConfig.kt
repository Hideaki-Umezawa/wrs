////セキュリティルール設定
//
//package com.example.wrs.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//
//@Configuration
//@EnableWebSecurity
//open class SecurityConfig {
//    @Bean
//    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        // CSRF対策の無効化
//        http
//            .csrf { it.disable() }
//            //.authorizeHttpRequests {どのリクエストを許可するか設定}
//            .authorizeHttpRequests {
//                //すべてのリクエストを無条件で許可
//                it.anyRequest().permitAll()
//            }
//        //設定をビルド
//        return http.build()
//    }
//}
