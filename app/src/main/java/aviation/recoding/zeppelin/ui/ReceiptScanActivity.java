package aviation.recoding.zeppelin.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.vision.text.TextBlock;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.model.Receipt;
import aviation.recoding.zeppelin.ui.scanning.ConfirmationFragment;
import aviation.recoding.zeppelin.ui.scanning.ScanFragment;

public class ReceiptScanActivity extends AppCompatActivity implements ScanFragment.scanFragmentInterface,
        ConfirmationFragment.ConfirmationListener{

    private static final String TAG = ReceiptScanActivity.class.getName();

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    ScanFragment mScanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_scan);
        mScanFragment = ScanFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.container, mScanFragment)
                .commit();


        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

    }

    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");

            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };


    }

    @Override
    public void onInfoFound(TextBlock textBlock, double mTotal) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("aasdf")
                .replace(R.id.container, ConfirmationFragment.newInstance(textBlock, mTotal))
                .commit();
    }

    @Override
    public void onConfirmation(Receipt receipt) {

    }

    @Override
    public void onRescan() {
        getSupportFragmentManager().popBackStack();
    }
}
