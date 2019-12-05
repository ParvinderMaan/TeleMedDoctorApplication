package com.telemed.doctor.network;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class WrapperResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private Converter<ResponseBody, WrapperResponse<T>> converter;

    public WrapperResponseBodyConverter(Converter<ResponseBody,
            WrapperResponse<T>> converter) {
        this.converter = converter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        WrapperResponse<T> response = converter.convert(value);
        if (!response.getError()) {
            return response.getData();
        }
        // RxJava will call onError with this exception
        throw new WrapperError(Long.parseLong(response.getStatus()), response.getMessage());
    }
}
