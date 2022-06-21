package by.itacademy.user.config;

import by.itacademy.user.service.api.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class UsersStorageConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(H2)
//                .addScript(JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY)
//                .build();
//    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder encoder) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        try{
            UserDetails user = User.builder()
                    .username("user")
                    .password(encoder.encode("123"))
                    .roles(UserRole.USER.toString())
                    .build();
            UserDetails admin = User.builder()
                    .username("admin")
                    .password(encoder.encode("321"))
                    .roles(UserRole.USER.toString(), UserRole.ADMIN.toString())
                    .build();

            manager.createUser(user);
            manager.createUser(admin);
        }catch (DuplicateKeyException e){
            //всё ок, уже есть
        }

        return manager;
    }
}
