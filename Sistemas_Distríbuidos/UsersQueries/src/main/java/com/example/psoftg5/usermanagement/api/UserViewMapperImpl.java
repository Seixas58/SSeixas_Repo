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
