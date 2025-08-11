package com.example.psoftg5.usermanagement.api;

import com.example.psoftg5.usermanagement.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor")
@Component
public class UserViewMapperImpl extends UserViewMapper {
    @Override
    public UserView toUserView(User user) {
        if (user == null) {
            return null;
        }

        UserView userView = new UserView();
        userView.setUsername(user.getUsername());
        userView.setName(user.getName());
        userView.setDateOfBirth(user.getDateOfBirth().toString());
        userView.setPhoneNumber(user.getPhoneNumber());
        userView.setGdpr(user.isGdpr());
        userView.setReaderNumber(String.valueOf(user.getReaderNumber()));
        userView.setRole(user.getRole());
        userView.setOptionalPhoto(user.getOptionalPhoto());

        return userView;
    }

    @Override
    public List<UserView> toUserView(List<User> users) {
        if (users == null) {
            return null;
        }

        List<UserView> list = new ArrayList<>(users.size());
        for (User user : users) {
            list.add(toUserView(user));
        }

        return list;
    }

    @Override
    public Iterable<UserView> toUserView(Iterable<User> readers) {
        if (readers == null) {
            return null;
        }

        List<UserView> list = new ArrayList<>();
        for (User reader : readers) {
            list.add(toUserView(reader));
        }

        return list;
    }

    @Override
    public User create(CreateUserRequest resource) {
        if (resource == null) {
            return null;
        }

        String username = resource.getUsername();
        String password = resource.getPassword();
        String name = resource.getName();
        String dateOfBirth = String.valueOf(resource.getDateOfBirth()); // Direto para String
        Long phoneNumber = Long.valueOf(resource.getPhoneNumber());
        boolean gdpr = Boolean.parseBoolean(resource.getGdpr()); // Convertendo String para boolean
        String role = resource.getRole();
        String optionalPhoto = resource.getOptionalPhoto();

        // Validação do número de telefone
        if (phoneNumber == null || !String.valueOf(phoneNumber).matches("\\d{9}")) {
            throw new IllegalArgumentException("Invalid Phone Number " + phoneNumber);
        }

        LocalDate date = null;
        if (dateOfBirth != null && !dateOfBirth.trim().isEmpty() && !"null".equals(dateOfBirth)) {
            try {
                date = LocalDate.parse(dateOfBirth); // Convertendo String para LocalDate
            } catch (DateTimeParseException e) {
                // Trate a exceção, se necessário
                throw new IllegalArgumentException("Invalid date format for date of birth: " + dateOfBirth);
            }
        }

        User user = User.newUser(username, password, name, date, phoneNumber, gdpr, role, optionalPhoto);

        return user;
    }

    @Override
    public void update(CreateUserRequest request, User user) {
        if (request == null) {
            return;
        }

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getDateOfBirth() != null) {
            LocalDate date = request.getDateOfBirth();
            user.setDateOfBirth(date);
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getGdpr() != null) {
            boolean gdpr = Boolean.parseBoolean(request.getGdpr());
            user.setGdpr(gdpr);
        }
    }

    @Override
    public UserView toUserViewWithBorrowCount(User user) {
        if (user == null) {
            return null;
        }

        UserView userView = new UserView();
        userView.setUsername(user.getUsername());
        userView.setName(user.getName());
        userView.setDateOfBirth(user.getDateOfBirth().toString());
        userView.setPhoneNumber(user.getPhoneNumber());
        userView.setGdpr(user.isGdpr());
        userView.setReaderNumber(String.valueOf(user.getReaderNumber()));
        userView.setRole(user.getRole());
        userView.setBorrowCount(user.getBorrowCount());

        return userView;
    }
}
