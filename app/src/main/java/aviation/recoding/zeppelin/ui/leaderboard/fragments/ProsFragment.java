package aviation.recoding.zeppelin.ui.leaderboard.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aviation.recoding.zeppelin.R;

public class ProsFragment extends Fragment {

    public ProsFragment() {
        // Required empty public constructor
    }

    public static ProsFragment newInstance() {
        ProsFragment fragment = new ProsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pros, container, false);
    }


}
