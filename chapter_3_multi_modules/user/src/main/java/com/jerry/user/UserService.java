package com.jerry.user;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    public static List<UserDTO> userDTOList = new LinkedList<UserDTO>() {{
        add(new UserDTO(1, "user1", "123456", "example1@email.com"));
        add(new UserDTO(2, "user2", "123456", "example2@email.com"));
        add(new UserDTO(3, "user3", "123456", "example3@email.com"));
    }};

    public UserDTO findById(int id) {
        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getId() == id) {
                return userDTO;
            }
        }
        return null;
    }
}
