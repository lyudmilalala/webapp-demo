package com.jerry.userapp;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDTO findById(int id) {
        for (UserDTO userDTO : UserAppApplication.userDTOList) {
            if (userDTO.getId() == id) {
                return userDTO;
            }
        }
        return null;
    }
}
