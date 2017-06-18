package aviation.recoding.zeppelin.ui.flights;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.ui.flights.BoardingScanFragment;
import aviation.recoding.zeppelin.ui.flights.FlightSelectionFragment;
import aviation.recoding.zeppelin.ui.flights.SelectionGallery;

public class FlightInfoActivity extends AppCompatActivity implements FlightSelectionFragment.FlightInfoListener, BoardingScanFragment.BoardingFragmentInterface {

    private static final int RC_HANDLE_CAMERA_PERM = 45;
    private BoardingScanFragment mBoardingScanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        mBoardingScanFragment = BoardingScanFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.container, mBoardingScanFragment)
                .commit();


        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

    }

    private void requestCameraPermission() {


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
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            return;
        }


        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSelectedFlight() {
        startActivity(new Intent(this, SelectionGallery.class));

    }

    @Override
    public void onImportPass() {

    }

    @Override
    public void onSelectFlight() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("sd")
                .replace(R.id.container, FlightSelectionFragment.newInstance())
                .commit();
    }

    @Override
    public void onBoardingPassFound() {

    }
}
