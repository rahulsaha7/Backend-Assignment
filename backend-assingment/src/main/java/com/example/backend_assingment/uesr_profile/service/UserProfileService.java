package com.example.backend_assingment.uesr_profile.service;

import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.EUser;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.dto.repository.IUserRepository;
import com.example.backend_assingment.uesr_profile.model.UpdateUserDataRequest;
import com.example.backend_assingment.uesr_profile.model.UpdateUserDataResponse;
import com.example.backend_assingment.utils.PasswordUtil;
import com.example.backend_assingment.utils.TokenUtils;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserProfileService implements IUserProfileService{

    private IUserRepository userRepository;
    private  TokenUtils tokenUtils;

    @Override
    public UpdateUserDataResponse updateUserProfile(UpdateUserDataRequest updateUserDataRequest) {

        Optional<EUser> user = userRepository.findByUserName(updateUserDataRequest.getUserName());
        if (user.isEmpty()) {
            throw new ApiException(
                ApiResponseCodes.UNAUTHORIZED_ACCESS.getTitle(),
                "Unathorized Access",
                ApiResponseCodes.UNAUTHORIZED_ACCESS,
                null);
        }
        userRepository.update(getDataToBeUpdate(updateUserDataRequest));
        String token = tokenUtils.generateToken(updateUserDataRequest.getUserName(),
            updateUserDataRequest.getFirstName(), updateUserDataRequest.getLastName(),
            user.get().getEmail(), updateUserDataRequest.getMobile(), AuthPermissions.AUTHENTICATED);
        return new UpdateUserDataResponse(
            updateUserDataRequest.getFirstName(),
            updateUserDataRequest.getLastName(),
            updateUserDataRequest.getMobile(),
            updateUserDataRequest.getUserName(),
            token
        );
    }

    private EUser getDataToBeUpdate(UpdateUserDataRequest updateUserDataRequest) {
        EUser newUserData = new EUser();
        newUserData.setFirstName(updateUserDataRequest.getFirstName());
        newUserData.setLastName(updateUserDataRequest.getLastName());
        newUserData.setMobile(updateUserDataRequest.getMobile());
        newUserData.setPassword(PasswordUtil.encryptPassword(updateUserDataRequest.getPassword()));
        newUserData.setUsername(updateUserDataRequest.getUserName());

        return newUserData;
    }
}
