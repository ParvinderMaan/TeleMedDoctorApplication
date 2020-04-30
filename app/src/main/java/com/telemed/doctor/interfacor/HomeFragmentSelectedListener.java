package com.telemed.doctor.interfacor;

public interface HomeFragmentSelectedListener {

    void showFragment(String tag, Object payload);
    void popTillFragment(String tag, int flag);
    void popTopMostFragment();
    void showDialog(String tag);
    void startActivity(String showActivity, String showFragment, Object payload);
    void refreshFragment(String tag);
    void hideSoftKeyboard();

    void signOut();

}
