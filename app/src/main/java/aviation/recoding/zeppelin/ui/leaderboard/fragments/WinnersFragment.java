package aviation.recoding.zeppelin.ui.leaderboard.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aviation.recoding.zeppelin.R;
import butterknife.ButterKnife;

public class WinnersFragment extends Fragment {

    public WinnersFragment() {
        // Required empty public constructor
    }

    public static WinnersFragment newInstance() {
        WinnersFragment fragment = new WinnersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_winners, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
