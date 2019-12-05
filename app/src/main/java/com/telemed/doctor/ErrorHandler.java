package com.telemed.doctor;

import com.google.gson.JsonSyntaxException;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.WrapperError;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.HttpException;

import static com.telemed.doctor.network.Status.ERROR;

public class ErrorHandler {
    private static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static String reportError(Throwable error) {
        String errorMessage;
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
                case API_STATUS_CODE_LOCAL_ERROR:
                    errorMessage="No Internet Connection";
                    break;
                default:
                    errorMessage=error.getLocalizedMessage();

            }
        } else if (error instanceof WrapperError) {
            errorMessage=error.getMessage();
        } else if (error instanceof JsonSyntaxException) {
            errorMessage="Something Went Wrong API is not responding properly!";
        } else {
            errorMessage=error.getMessage();
        }




        return errorMessage;
    }
}
