package com.telemed.doctor.interfacor;

public interface RouterFragmentSelectedListener {

    void showFragment(String tag);
    void popTopMostFragment();
    void popTillFragment(String tag, int flag);
    void startActivity(String tag);
}
