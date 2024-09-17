package com.devteria.identify_service.Service;

import com.devteria.identify_service.DTO.Request.UserCreationRequest;
import com.devteria.identify_service.DTO.Request.UserUpdateRequest;
import com.devteria.identify_service.DTO.Response.UserResponse;
import com.devteria.identify_service.Enums.Role;
import com.devteria.identify_service.Exception.AppException;
import com.devteria.identify_service.Exception.ErrorCode;
import com.devteria.identify_service.Mapper.UserMapper;
import com.devteria.identify_service.Repository.UserRepository;
import com.devteria.identify_service.Entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}
