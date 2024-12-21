package com.hoursmanager.HoursManager.exceptions.controlleradvice;

import com.hoursmanager.HoursManager.dto.ErrorDetailsDto;
import com.hoursmanager.HoursManager.exceptions.UserLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserLoginExceptionHandler
{
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<ErrorDetailsDto> handleUserLoginException(UserLoginException userLoginException, WebRequest webRequest)
    {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                userLoginException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorDetailsDto>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }
}
