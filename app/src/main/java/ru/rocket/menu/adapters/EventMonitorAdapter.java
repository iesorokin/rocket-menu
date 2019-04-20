
package ru.rocket.menu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ru.rocket.menu.activities.EventActivity;
import ru.rocket.menu.fragments.event.DescriptionFragment;
import ru.rocket.menu.fragments.event.InstractureFragment;

public class EventMonitorAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private boolean mIsAuthor = false;

    public Fragment[] mFragments;

    public EventMonitorAdapter(EventActivity sourceActivity, FragmentManager fm, int numOfTabs, boolean isAuthor) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mIsAuthor = isAuthor;
        mFragments = new Fragment[]{new DescriptionFragment(), new InstractureFragment()};

        for (Fragment fragment : mFragments) {
            if (fragment instanceof EventActivity.ConnectionListener) {
                sourceActivity.addConnectionListener((EventActivity.ConnectionListener) fragment);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return (mFragments[0]);
            case 1:
                return (mFragments[1]);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return mNumOfTabs;


    }

}
