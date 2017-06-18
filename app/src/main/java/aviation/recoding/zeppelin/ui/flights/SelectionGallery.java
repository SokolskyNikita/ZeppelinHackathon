package aviation.recoding.zeppelin.ui.flights;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.ui.HotSpotActivity;

public class SelectionGallery extends AppCompatActivity {
    private GyroscopeObserver gyroscopeObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_gallry);

        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);

        PanoramaImageView panoramaImageView = (PanoramaImageView) findViewById(R.id.backgroundImageView);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
        panoramaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectionGallery.this, HotSpotActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }

}
