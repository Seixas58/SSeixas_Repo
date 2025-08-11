package com.example.psoftg5.usermanagement.api;

import com.example.psoftg5.usermanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    public abstract UserView toUserView(User user);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    public abstract List<UserView> toUserView(List<User> users);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    public abstract Iterable<UserView> toUserView(Iterable<User> readers);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    public abstract User create(CreateUserRequest resource);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    public abstract void update(CreateUserRequest request, @MappingTarget User user);

    @Mapping(source = "borrowCount", target = "borrowCount")
    public abstract UserView toUserViewWithBorrowCount(User user);
}
