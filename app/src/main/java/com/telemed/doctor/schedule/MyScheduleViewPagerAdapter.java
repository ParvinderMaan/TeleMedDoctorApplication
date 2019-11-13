package com.telemed.doctor.schedule;


import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * @author Pmaan on 4/7/18.
 *         Copyright © All rights reserved.
 */

public class MyScheduleViewPagerAdapter {}
//        extends FragmentStatePagerAdapter {
//    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
//    private static final int NO_COUNT = 12;
//
//    /**
//     * Create pager adapter
//     *
//     * @param fm
//     */
//    public MyScheduleViewPagerAdapter(FragmentManager fm) {
//        super(fm);
//
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        final Fragment result;
//        switch (position) {
//            case 0:
//                // First Fragment of First Tab
//                result = new ScheduleWeekIFragment();
//                break;
//            case 1:
//                // First Fragment of Second Tab
//                result = new ScheduleWeekIIFragment();
//                break;
//            case 2:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekIIIFragment();
//                break;
//            case 3:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekIVFragment();
//                break;
//            case 4:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekVFragment();
//                break;
//
//            case 5:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekVIFragment();
//                break;
//            case 6:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekVIIFragment();
//                break;
//
//            case 7:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekVIIIFragment();
//                break;
//            case 8:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekIXFragment();
//                break;
//            case 9:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekXFragment();
//                break;
//            case 10:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekXIFragment();
//                break;
//
//            case 11:
//                // First Fragment of Third Tab
//                result =  new ScheduleWeekXIIFragment();
//                break;
//
//
//
//
//            default:
//                result = null;
//                break;
//        }
//
//        return result;
//    }
//
//    @Override
//    public int getCount() {
//        return NO_COUNT;
//    }
//
//    /**
//     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
//     * It will help us to retrieve the Fragment by position
//     *
//     * @param container
//     * @param position
//     * @return
//     */
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container, position);
//        registeredFragments.put(position, fragment);
//        return fragment;
//    }
//
//    /**
//     * Remove the saved reference from our Map on the Fragment destroy
//     *
//     * @param container
//     * @param position
//     * @param object
//     */
//
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        registeredFragments.remove(position);
//        super.destroyItem(container, position, object);
//    }
//
//
//    /**
//     * Get the Fragment by position
//     *
//     * @param position tab position of the fragment
//     * @return
//     */
//    public Fragment getRegisteredFragment(int position) {
//        return registeredFragments.get(position);
//    }

//}

