package com.hoursmanager.HoursManager.exceptions.controlleradvice;

import com.hoursmanager.HoursManager.dto.ErrorDetailsDto;
import com.hoursmanager.HoursManager.exceptions.DbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class DbExceptionHandler
{
    @ExceptionHandler(DbException.class)
    public ResponseEntity<ErrorDetailsDto> handleDbException(DbException dbException, WebRequest webRequest)
    {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                dbException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorDetailsDto>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
