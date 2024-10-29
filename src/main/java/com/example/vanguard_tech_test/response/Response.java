package com.example.vanguard_tech_test.response;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class Response {

    public static ResponseEntity<ResponseFields> successResponse(Object data, String msg, HttpStatusCode statusCode)
    {
        ResponseFields responseFields = new ResponseFields();
        responseFields.setData(data);
        responseFields.setMessage(msg);

        return new ResponseEntity<>(responseFields, statusCode);
    }

    public static ResponseEntity<ResponseFields> errorResponse(Object data, String msg, HttpStatusCode statusCode)
    {
        ResponseFields responseFields = new ResponseFields();
        responseFields.setData(data);
        responseFields.setMessage(msg);

        return new ResponseEntity<>(responseFields, statusCode);
    }
}
