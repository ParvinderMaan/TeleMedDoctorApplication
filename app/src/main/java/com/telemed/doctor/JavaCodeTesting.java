package com.telemed.doctor;

import com.telemed.doctor.helper.Validator;

public class JavaCodeTesting {

    public static void main(String[] args) {


        String a="PARVINDER";
        String b="parvinder";
        String c="Parvinder";
        String d="12345";
        String e="PARVINDER123";
        String f="Parvinder123";
        String g="Parvinder123#";
        String h="@arvinder123#";


        System.out.println("\n ");
        System.out.println("a: "+ Validator.isAlphaNumeric(a));

        System.out.println("\n ");
        System.out.println("b:"+ Validator.isAlphaNumeric(b));


        System.out.println("\n ");
        System.out.println("c:"+ Validator.isAlphaNumeric(c));


        System.out.println("\n ");
        System.out.println("d:"+ Validator.isAlphaNumeric(d));


        System.out.println("\n ");
        System.out.println("e:"+ Validator.isAlphaNumeric(e));

        System.out.println("\n ");
        System.out.println("f:"+ Validator.isAlphaNumeric(f));


        System.out.println("\n ");
        System.out.println("g:"+ Validator.isAlphaNumeric(g));




    }
}
