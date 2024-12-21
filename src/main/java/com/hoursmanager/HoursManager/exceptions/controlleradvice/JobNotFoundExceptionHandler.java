package com.hoursmanager.HoursManager.exceptions.controlleradvice;

import com.hoursmanager.HoursManager.dto.ErrorDetailsDto;
import com.hoursmanager.HoursManager.exceptions.JobNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class JobNotFoundExceptionHandler
{
    @ExceptionHandler(JobNotFound.class)
    public ResponseEntity<ErrorDetailsDto> handleJobNotFoundException(JobNotFound jobNotFound, WebRequest webRequest)
    {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(
                LocalDateTime.now(),
                jobNotFound.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<ErrorDetailsDto>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }
}
