package aviation.recoding.zeppelin.vision.barcode;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import aviation.recoding.zeppelin.ui.scanning.ScanFragment;


public class BarcodeGraphicTracker
        extends Tracker<Barcode> {
    BarcodeInterface schemeListener;

     BarcodeGraphicTracker(BarcodeInterface paramScanSchemeFragment) {
        this.schemeListener = paramScanSchemeFragment;
    }

    @Override
    public void onDone() {
    }

    @Override
    public void onMissing(Detector.Detections<Barcode> paramDetections) {
    }

    @Override
    public void onNewItem(int paramInt, Barcode paramBarcode) {

        schemeListener.onBarcodeFound(paramBarcode);
    }

    @Override
    public void onUpdate(Detector.Detections<Barcode> paramDetections, Barcode paramBarcode) {
    }
}