package com.example.backend_assingment.uesr_profile.service;

import com.example.backend_assingment.uesr_profile.model.UpdateUserDataRequest;
import com.example.backend_assingment.uesr_profile.model.UpdateUserDataResponse;

public interface IUserProfileService {

    UpdateUserDataResponse updateUserProfile(UpdateUserDataRequest updateUserDataRequest);

}
