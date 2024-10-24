package com.hhplus.commerce.globals;

import com.hhplus.commerce.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return handleError(CommonErrorCode.UNDEFINED_ERROR);
    }

    // 표준 에러를 처리하는 핸들러
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleError(errorCode);
    }

//    FIXME Ambiguous @ExceptionHandler method mapped
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return handleError(CommonErrorCode.METHOD_ARGUMENT_NOT_VALID, e);
//    }

//    FIXME Ambiguous @ExceptionHandler method mapped
//    @ExceptionHandler(value = NoHandlerFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
//        ErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;
//        return handleError(errorCode);
//    }

//    FIXME Ambiguous @ExceptionHandler method mapped
//    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
//        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
//        ErrorInfo errorInfo = ErrorInfo.builder()
//                .addDetail("method", e.getMethod())
//                .addDetail("supportedMethods", e.getSupportedHttpMethods())
//                .build();
//
//        return handleError(errorCode, errorInfo);
//    }


    // 서비스 로직에서 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return handleError(e.getErrorCode(), e.getErrorInfo());
    }

    private ResponseEntity<Object> handleError(ErrorCode errorCode, BindException bindException) {
        ErrorInfo.ErrorInfoBuilder builder = ErrorInfo.builder();
        bindException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            builder.addDetail(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = makeErrorResponse(errorCode, builder.build());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleError(ErrorCode errorCode, ErrorInfo errorInfo) {
        ErrorResponse errorResponse = makeErrorResponse(errorCode, errorInfo);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleError(ErrorCode errorCode) {
        ErrorResponse errorResponse = makeErrorResponse(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, ErrorInfo errorInfo) {
        if (errorInfo == null) {
            return makeErrorResponse(errorCode);
        }

        return new ErrorResponse(errorCode.name(), errorCode.getMessage(), errorInfo.getDetails());
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage(), null);
    }


}
