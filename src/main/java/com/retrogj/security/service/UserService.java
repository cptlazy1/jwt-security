package com.retrogj.security.service;

import com.retrogj.security.dto.UserDto;
import com.retrogj.security.exception.RecordNotFoundException;
import com.retrogj.security.model.User;
import com.retrogj.security.repository.GameConditionRepository;
import com.retrogj.security.repository.GameRepository;
import com.retrogj.security.repository.GameSystemRepository;
import com.retrogj.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameSystemRepository gameSystemRepository;
    private final GameService gameService;
    private final GameSystemService gameSystemService;

    // Method to get all users
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new RecordNotFoundException("No user records exist");
        } else {
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                UserDto userDto = convertToUserDto(user);
                userDtos.add(userDto);
            }
            return userDtos;
        }
    }

    // Method to get user by username
    public UserDto getUserByUserName(String username) {
        UserDto userDto = new UserDto();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userDto = convertToUserDto(user);

        } else {
            throw new RecordNotFoundException("No user record exists for given username");
        }
        return userDto;
    }


    // Method to convert User to UserDto with ModelMapper
    private UserDto convertToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    // Method to convert UserDto to User with ModelMapper
    private User convertToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}
