package com.telemed.doctor.interfacor;

public interface RouterFragmentSelectedListener {

    void showFragment(String tag, String payload);
    void popTopMostFragment();
    void popTillFragment(String tag, int flag);

    void startActivity(String tag);
    void hideSoftKeyboard();
    void sendDataToFragment(String fragmentTag, String data, String type);
}
