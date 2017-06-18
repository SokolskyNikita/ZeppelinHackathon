package aviation.recoding.zeppelin.ui.leaderboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.TitlePageIndicator;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.ui.leaderboard.fragments.LifetimeFragment;
import aviation.recoding.zeppelin.ui.leaderboard.fragments.ProsFragment;
import aviation.recoding.zeppelin.ui.leaderboard.fragments.WinnersFragment;

public class LeaderboardActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        PagerTitleStrip titlePageIndicator = (PagerTitleStrip) findViewById(R.id.pagertitlestrip);

        mPager = (ViewPager) findViewById(R.id.leaderBoardsPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Lifetime";
                case 1:
                    return "This Month";
                case 2:
                    return "Aviation Pro";
            }

            return null;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LifetimeFragment.newInstance();
                case 1:
                    return WinnersFragment.newInstance();
                case 2:
                    return ProsFragment.newInstance();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}