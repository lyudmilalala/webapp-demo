package com.jerry.user;

import com.jerry.common.response.CommonEntityResponse;
import com.jerry.common.response.CommonListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.jerry.user.UserService.userDTOList;

@RestController
public class UserController {

    @RequestMapping(value="/getUserList", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonListResponse<UserDTO>> getUserList() {
        return new ResponseEntity<>(new CommonListResponse<>(userDTOList), HttpStatus.OK);
    }

    @RequestMapping(value="/findById", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonEntityResponse<UserDTO>> findById(int id) {
        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getId() == id) {
                return new ResponseEntity<>(new CommonEntityResponse<>(userDTO), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new CommonEntityResponse<>(1000, "No user with id = " + id), HttpStatus.NOT_FOUND);
    }
}