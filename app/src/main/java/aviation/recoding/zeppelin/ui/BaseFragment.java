package aviation.recoding.zeppelin.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

    //TODO: Add injection calls here

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(getLayoutResId(), container);
    }

    private int getLayoutResId() {
        return 0;
    }

}
