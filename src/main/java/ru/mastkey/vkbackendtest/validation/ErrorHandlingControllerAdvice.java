package ru.mastkey.vkbackendtest.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        final Map<String, String> map = e.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.entry(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }
}