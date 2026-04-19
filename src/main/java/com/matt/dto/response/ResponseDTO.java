package com.matt.dto.response;

import com.matt.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private String code;
    private String message;
    private T data;

    public static <T> ResponseDTO<String> success() {
        return new ResponseDTO<>(Constant.RESPONSE_SUCCESS, "", null);
    }

    public static  <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(Constant.RESPONSE_SUCCESS, "", data);
    }
}
