package com.developersuraj.MyAgent.services;

import com.developersuraj.MyAgent.model.UserProfile;
import com.developersuraj.MyAgent.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile createUser(UserProfile user){
        return userProfileRepository.save(user);
    }

    public UserProfile findUser(String userName){
        return userProfileRepository.findByUserName(userName);
    }


}
