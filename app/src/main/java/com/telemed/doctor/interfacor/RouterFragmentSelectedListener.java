package com.telemed.doctor.interfacor;

public interface RouterFragmentSelectedListener {

    void showFragment(String tag, Object payload);
    void popTopMostFragment();
    void popTillFragment(String tag, int flag);
    void abortSignUpDialog();

    void startActivity(String tag, Object payload);
    void hideSoftKeyboard();
    void sendDataToFragment(String fragmentTag, String data, String type);
}
