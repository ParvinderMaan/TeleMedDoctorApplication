package com.telemed.doctor.helper;

import com.google.gson.JsonSyntaxException;
import com.telemed.doctor.network.WrapperError;

import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.HttpException;


public class ErrorHandler {

    public static String reportError(Throwable error) {
        String errorMessage="Something Went Wrong"; // default msg
        if (error instanceof HttpException) {
            switch (((HttpException) error).code()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    errorMessage="Unauthorised User";
                    break;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    errorMessage="Forbidden";
                    break;
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    errorMessage="Internal Server Error";
                    break;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    errorMessage="Bad Request";
                    break;

                default:
                    errorMessage=error.getLocalizedMessage();

            }
        } else if (error instanceof WrapperError) {
            errorMessage=error.getMessage();
        } else if (error instanceof JsonSyntaxException) {
            errorMessage="Something Went Wrong API is not responding properly!";
        }else if (error instanceof UnknownHostException){
            errorMessage="Sorry cannot connect to the server!";
        } else {
            errorMessage=error.getMessage();
        }

        return errorMessage;
    }



    public static String reportError(int errorCode) {
        String errorMessage="";
        switch (errorCode) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    errorMessage="Unauthorised User";
                    break;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    errorMessage="Forbidden";
                    break;
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    errorMessage="Internal Server Error";
                    break;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    errorMessage="Bad Request";
                    break;

        }

        return errorMessage;
    }
}
