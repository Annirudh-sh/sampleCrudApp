package com.sample.crud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private String error;
}
