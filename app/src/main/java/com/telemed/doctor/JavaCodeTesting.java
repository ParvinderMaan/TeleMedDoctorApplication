package com.telemed.doctor;

import com.telemed.doctor.helper.Validator;

/*
    removeView it in production .....

    -   -   --  -- - - - - - - - - - -- -


 */
public class JavaCodeTesting {

    public static void main(String[] args) {

           String abc="123#Qwer21";
           boolean b=Validator.isAlphaNumeric(abc);
           System.out.println(b);



    }
}
