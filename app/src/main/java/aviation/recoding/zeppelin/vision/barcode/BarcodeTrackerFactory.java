package aviation.recoding.zeppelin.vision.barcode;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import aviation.recoding.zeppelin.ui.scanning.ScanFragment;
import aviation.recoding.zeppelin.vision.camera.GraphicOverlay;

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlay mGraphicOverlay;

    private BarcodeInterface barcodeListener;

    public BarcodeTrackerFactory(BarcodeInterface barcodeListener){
        this.barcodeListener = barcodeListener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
        return new BarcodeGraphicTracker(barcodeListener);
    }

}

