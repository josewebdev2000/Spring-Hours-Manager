package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.dto.SpringUserDto;
import com.hoursmanager.HoursManager.exceptions.DbException;
import com.hoursmanager.HoursManager.exceptions.UserLoginException;
import com.hoursmanager.HoursManager.exceptions.UserRegistrationException;
import com.hoursmanager.HoursManager.exceptions.ValidationError;
import com.hoursmanager.HoursManager.forms.LoginInfo;
import com.hoursmanager.HoursManager.forms.RegisterInfo;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import com.hoursmanager.HoursManager.utils.PasswordHasher;
import com.hoursmanager.HoursManager.validators.EmailValidator;
import com.hoursmanager.HoursManager.validators.NameValidator;
import com.hoursmanager.HoursManager.validators.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Implement the SpringUser Repository here to actually do what's required to interact with the user model
* */

@Service
public class SpringUserRepoImp
{
    // Autowire the repo
    @Autowired
    private SpringUserRepository springUserRepository;

    // Make code to register the user
    public SpringUserDto registerUser(RegisterInfo registerInfo)
    {
        try
        {
            // Validate name, email, and password fields, otherwise, throw validation error
            if (!NameValidator.isValidName(registerInfo.name))
            {
                throw new ValidationError("Invalid Name Format");
            }

            if (!EmailValidator.isValidEmail(registerInfo.email))
            {
                throw new ValidationError("Invalid Email Format");
            }

            if (!PasswordValidator.isValidPassword(registerInfo.password))
            {
                throw new ValidationError("Invalid Password Format");
            }

            // First check if the email already exists in the DB
            if (springUserRepository.findSpringUserBySpringUserEmail(registerInfo.email) != null)
            {
                throw new UserRegistrationException("Email already in use");
            }

            // Hash the password of the new user
            String hash = PasswordHasher.getHashedPassword(registerInfo.password);

            // Make a model for the SpringUser
            SpringUser springUser = new SpringUser(
                    registerInfo.name,
                    registerInfo.email,
                    hash
            );

            // Grab the new user
            SpringUser newUser = springUserRepository.save(springUser);

            // Make the DTO for the new user
            SpringUserDto springUserDto = new SpringUserDto(
                    newUser.getSpringUserId(),
                    newUser.getSpringUserName(),
                    newUser.getSpringUserPicUrl()
            );

            // Return the new saved user
            return springUserDto;
        }

        catch (ValidationError | UserRegistrationException e)
        {
            throw e;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            throw new DbException("A database error caused registration to fail");
        }
    }

    // Make code to login the user
    public SpringUserDto loginUser(LoginInfo loginInfo)
    {
        try
        {
            // Validate email and password
            if (!EmailValidator.isValidEmail(loginInfo.email))
            {
                throw new ValidationError("Invalid Email Format");
            }

            if (!PasswordValidator.isValidPassword(loginInfo.password))
            {
                throw new ValidationError("Invalid Password Format");
            }

            // Check if the user email already exists in the DB
            // Grab the spring User
            SpringUser springUser = springUserRepository.findSpringUserBySpringUserEmail(loginInfo.email);

            if (springUser == null)
            {
                throw new UserLoginException("User does not exist");
            }

            // Compare the given hash with the hash stored in the DB
            if (!PasswordHasher.isPasswordCorrect(loginInfo.password, springUser.getSpringUserPassword()))
            {
                throw new UserLoginException("Incorrect Credentials");
            }

            // By this point everything is OK, so we can return the SpringUserDto
            return new SpringUserDto(
                    springUser.getSpringUserId(),
                    springUser.getSpringUserName(),
                    springUser.getSpringUserPicUrl()
            );

        }

        catch (ValidationError | UserLoginException e)
        {
            throw e;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            throw new DbException("A database error caused registration to fail");
        }
    }

}
