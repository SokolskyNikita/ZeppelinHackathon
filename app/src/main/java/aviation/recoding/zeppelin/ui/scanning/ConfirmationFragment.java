package aviation.recoding.zeppelin.ui.scanning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.vision.text.TextBlock;

import java.text.MessageFormat;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.model.Receipt;
import aviation.recoding.zeppelin.ui.leaderboard.GameActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationFragment extends Fragment {

    private static double theTotal;
    private ConfirmationListener mListener;

    private Receipt receiptInfo;
    public TextBlock tempTextBlock;

    @BindView(R.id.retailer)
    public TextInputEditText retailerText;

    @BindView(R.id.date)
    public TextInputEditText dateText;

    @BindView(R.id.total)
    public TextInputEditText totalText;

    @BindView(R.id.points)
    public TextView pointsInfoText;

    @BindView(R.id.buttonAdd)
    public Button buttonAdd;

    @BindView(R.id.buttonRescan)
    public Button buttonRescan;

    @BindView(R.id.viewSwitcher)
    public ViewSwitcher viewSwitcher;

    @BindView(R.id.pointsImgText)
    public TextView pointsText;

    public interface ConfirmationListener {
        void onConfirmation(Receipt receipt);

        void onRescan();
    }

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    public static ConfirmationFragment newInstance(TextBlock textBlock, double mTotal) {
        theTotal = mTotal;
        ConfirmationFragment confirmationFragment = new ConfirmationFragment();
        confirmationFragment.tempTextBlock = textBlock;
        return confirmationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiptInfo = new Receipt();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ButterKnife.bind(this, view);

        retailerText.setText("UNKNOWN");
        String valuePoints = Double.valueOf(theTotal).toString();
        totalText.setText(MessageFormat.format("EUR {0}", valuePoints));
        pointsInfoText.setText(valuePoints + " POINTS");
        return view;
    }

    @OnClick(R.id.buttonRescan)
    public void rescan() {
        mListener.onRescan();
    }

    @OnClick(R.id.buttonAdd)
    public void addReceipt() {
        receiptInfo.total = theTotal;
        int tempDouble = Double.valueOf(theTotal).intValue();
        if (tempDouble > 10) {
            receiptInfo.points = tempDouble / 10;
        } else {
            receiptInfo.points = 0;
        }
        pointsText.setText(String.valueOf(receiptInfo.points) + " POINTS");
        receiptInfo.storeName = "EINSTEIN";

        viewSwitcher.showNext();
    }

    @OnClick(R.id.popup)
    public void popupConfirm() {
        startActivity(new Intent(getActivity(), GameActivity.class));
        getActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmationListener) {
            mListener = (ConfirmationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ConfirmationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
