package aviation.recoding.zeppelin.vision.barcode;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Hanson on 17/06/2017.
 */

public interface BarcodeInterface {
    void onBarcodeFound(Barcode barcode);
}
