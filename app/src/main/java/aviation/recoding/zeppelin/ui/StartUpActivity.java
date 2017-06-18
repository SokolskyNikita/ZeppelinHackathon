package aviation.recoding.zeppelin.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import aviation.recoding.zeppelin.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        ImageView logoImage = (ImageView) findViewById(R.id.imageView);
        animateLogo(logoImage);
    }
    

    protected void animateLogo(ImageView logoImage)
    {
        TranslateAnimation animation = new TranslateAnimation(-600, getWindowManager().getDefaultDisplay().getWidth() + 600, 0, 0);
        animation.setDuration(5000);
        animation.setRepeatCount(50);
        animation.setFillAfter(false);
        //animation.setAnimationListener(new MyAnimationListener());

        logoImage.startAnimation(animation);
    }

    public void nextScreen(View view)
    {
        Intent intent = new Intent(StartUpActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

}
