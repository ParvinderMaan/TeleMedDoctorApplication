package com.telemed.doctor.interfacor;

public interface HomeFragmentSelectedListener {

    void showFragment(String tag);
    void popTillFragment(String tag, int flag);
    void popTopMostFragment();
    void showDialog(String tag);
    void startActivity(String tag, Object object);
    void signOut();

}
