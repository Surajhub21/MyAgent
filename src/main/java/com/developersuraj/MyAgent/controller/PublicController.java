package com.developersuraj.MyAgent.controller;

import com.developersuraj.MyAgent.model.UserProfile;
import com.developersuraj.MyAgent.services.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class PublicController {

    @Autowired
    private UserProfileService userProfileService;


    @GetMapping("{userName}")
    public ResponseEntity<UserProfile> home(@PathVariable String userName){
        UserProfile user = userProfileService.findUser(userName);

        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile user){
        try {
            return userProfileService.createUser(user);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return null;
        }
    }

}
