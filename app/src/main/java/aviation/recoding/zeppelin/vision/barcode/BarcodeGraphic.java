package aviation.recoding.zeppelin.vision.barcode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.google.android.gms.vision.barcode.Barcode;

import aviation.recoding.zeppelin.vision.camera.GraphicOverlay;

public class BarcodeGraphic
        extends GraphicOverlay.Graphic {
    private static final int[] COLOR_CHOICES = {-16776961, -16711681, -16711936};
    private static int mCurrentColorIndex = 0;
    private volatile Barcode mBarcode;
    private int mId;
    private Paint mRectPaint;
    private Paint mTextPaint;

    BarcodeGraphic(GraphicOverlay paramGraphicOverlay) {
        super(paramGraphicOverlay);
        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        int i = COLOR_CHOICES[mCurrentColorIndex];
        this.mRectPaint = new Paint();
        this.mRectPaint.setColor(i);
        this.mRectPaint.setStyle(Style.STROKE);
        this.mRectPaint.setStrokeWidth(4.0F);
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(i);
        this.mTextPaint.setTextSize(36.0F);
    }

    public boolean contains(float paramFloat1, float paramFloat2) {
        return false;
    }

    public void draw(Canvas paramCanvas) {
        Barcode localBarcode = this.mBarcode;
        if (localBarcode == null) {
            return;
        }
        RectF localRectF = new RectF(localBarcode.getBoundingBox());
        localRectF.left = translateX(localRectF.left);
        localRectF.top = translateY(localRectF.top);
        localRectF.right = translateX(localRectF.right);
        localRectF.bottom = translateY(localRectF.bottom);
        paramCanvas.drawRect(localRectF, this.mRectPaint);
        paramCanvas.drawText(localBarcode.rawValue, localRectF.left, localRectF.bottom, this.mTextPaint);
    }

    public Barcode getBarcode() {
        return this.mBarcode;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int paramInt) {
        this.mId = paramInt;
    }

    void updateItem(Barcode paramBarcode) {
        this.mBarcode = paramBarcode;
        postInvalidate();
    }
}


/* Location:              C:\Users\Hanson\Documents\Projects\OB\ob.jar!\com\vision\barcode\BarcodeGraphic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */