package com.example.psoftg5.bootstrapping;

import com.example.psoftg5.usermanagement.model.AuthorityRole;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.repositories.UserDBRepository;
import com.example.psoftg5.usermanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class UserBootstrapper implements CommandLineRunner {

    private final UserDBRepository repository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByUsername("beatriz123@mail.com").isEmpty()) {
            final User u1 = User.newUser("beatriz123@mail.com", encoder.encode("Biabia123!"), "Beatriz", LocalDate.of(2003, 1, 23), Long.valueOf("965329687"), true, AuthorityRole.LIBRARIAN,"http://example.com/montalegre-corajoso.jpg");
            repository.save(u1);
        }
        if (repository.findByUsername("nuno123@mail.com").isEmpty()) {
            final User u2 = User.newUser("nuno123@mail.com", encoder.encode("nuno123!"), "nuno", LocalDate.of(1999, 5, 26), Long.valueOf("978462712"), true, AuthorityRole.LIBRARIAN,"");
            repository.save(u2);
        }
        if (repository.findByUsername("miguel@mail.com").isEmpty()) {
            final User u3 = User.newUser("miguel@mail.com", encoder.encode("miguel123!"), "miguel", LocalDate.of(1989, 8, 21), Long.valueOf("913872673"), false, AuthorityRole.READER,"");
            repository.save(u3);
        }
        if (repository.findByUsername("simao@mail.com").isEmpty()) {
            final User u4 = User.newUser("simao@mail.com", encoder.encode("simao123!"), "simao", LocalDate.of(1979, 8, 21), Long.valueOf("993872673"), false, AuthorityRole.READER,"");
            repository.save(u4);
        }
        if (repository.findByUsername("helder@mail.com").isEmpty()) {
            final User u5 = User.newUser("helder@mail.com", encoder.encode("helder8!"), "helder", LocalDate.of(1988, 7, 1), Long.valueOf("923872673"), false, AuthorityRole.READER,"");
            repository.save(u5);
        }
        if (repository.findByUsername("tiago@mail.com").isEmpty()) {
            final User u6 = User.newUser("tiago@mail.com", encoder.encode("tiago24!"), "tiago", LocalDate.of(2002, 2, 15), Long.valueOf("923444444"), false, AuthorityRole.READER,"");
            repository.save(u6);
        }

    }
}
