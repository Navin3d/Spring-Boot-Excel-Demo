package gmc.project.student.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");  
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> generalPurpose() {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Something Went Wrong Try again Later...");  
  }
  
}
