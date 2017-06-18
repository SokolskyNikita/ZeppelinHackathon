package aviation.recoding.zeppelin.ui.scanning;

import android.app.Dialog;
import android.content.Context;
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
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.vision.barcode.BarcodeInterface;
import aviation.recoding.zeppelin.vision.barcode.BarcodeTrackerFactory;
import aviation.recoding.zeppelin.vision.camera.CameraSourcePreview;
import aviation.recoding.zeppelin.vision.camera.GraphicOverlay;
import aviation.recoding.zeppelin.vision.ocr.OcrDetectorProcessor;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ScanFragment extends Fragment implements BarcodeInterface{

    private static final int RC_HANDLE_GMS = 23;
    @BindView(R.id.preview)
    CameraSourcePreview preview;

    @BindView(R.id.infoOverlay)
    GraphicOverlay mGraphicOverlay;


    private CameraSource mCameraSource = null;

    @Override
    public void onBarcodeFound(Barcode barcode) {

    }

    public interface scanFragmentInterface {
        void onInfoFound(TextBlock t, double mTotal);
    }

    private scanFragmentInterface mListener;

    public ScanFragment() {
        // Required empty public constructor
    }

    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof scanFragmentInterface) {
            mListener = (scanFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement scanFragmentInterface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
        mTotal = 0.0;
        tempBlock = null;
        detectedStrings.clear();
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

        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        textRecognizer.setProcessor(new OcrDetectorProcessor(this, mGraphicOverlay));

        MultiDetector multiDetector = new MultiDetector.Builder()
                .add(barcodeDetector)
                .add(textRecognizer)
                .build();


        if (!multiDetector.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), multiDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(15.0f)
                .build();
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

    List<TextBlock> detectedStrings = new ArrayList<>();

    private double mTotal = 0.0D;

    public void addTextBlock(TextBlock item) {
        if (detectedStrings.size() < 20) {
            detectedStrings.add(item);
        } else {

            if (tempBlock == null) {
                for (TextBlock t : detectedStrings) {
                    if (t.getValue().contains(".") || item.getValue().contains(",")) {
                        try {
                            String str = t.getValue();
                            str = str.replaceAll("[^0-9?!\\.\\,]", "").replace(",", ".");
                            Double tempValue = Double.parseDouble(str);
                            if (mTotal < tempValue) {
                                mTotal = tempValue;
                                tempBlock = t;
                            }
                        } catch (NumberFormatException NFE) {
                            // ignore
                        }
                    }
                }
                openNextStep(tempBlock);
            }
        }
    }

    TextBlock tempBlock;
    private void openNextStep(Object o) {
        if (o != null && mListener != null) {
            mListener.onInfoFound((TextBlock) o, mTotal);
        }
    }
}
