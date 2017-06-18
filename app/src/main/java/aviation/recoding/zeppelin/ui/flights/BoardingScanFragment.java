package aviation.recoding.zeppelin.ui.flights;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.vision.barcode.BarcodeInterface;
import aviation.recoding.zeppelin.vision.barcode.BarcodeTrackerFactory;
import aviation.recoding.zeppelin.vision.camera.CameraSourcePreview;
import aviation.recoding.zeppelin.vision.camera.GraphicOverlay;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class BoardingScanFragment extends Fragment implements BarcodeInterface{

    private static final int RC_HANDLE_GMS = 23;
    @BindView(R.id.preview)
    CameraSourcePreview preview;

    @BindView(R.id.infoOverlay)
    GraphicOverlay mGraphicOverlay;


    private CameraSource mCameraSource = null;

    public interface BoardingFragmentInterface {
        void onImportPass();
        void onSelectFlight();
        void onBoardingPassFound();
    }
    private BoardingFragmentInterface mListener;

    public BoardingScanFragment() {
        // Required empty public constructor
    }

    public static BoardingScanFragment newInstance() {
        BoardingScanFragment fragment = new BoardingScanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onBarcodeFound(Barcode barcode) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BoardingFragmentInterface) {
            mListener = (BoardingFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement scanFragmentInterface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getActivity().getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                preview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCameraSource();
    }

    public void createCameraSource() {
        Context context = getActivity().getApplicationContext();
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();

        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(this);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());


        mCameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(15.0f)
                .build();
    }

    @OnClick(R.id.btnFlight)
    public void SelectFlights()
    {
        mListener.onSelectFlight();
    }

    /**
     * Stops the camera.
     */
    @Override
    public void onPause() {
        super.onPause();
        preview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

}
