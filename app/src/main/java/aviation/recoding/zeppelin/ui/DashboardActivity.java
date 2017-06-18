package aviation.recoding.zeppelin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import aviation.recoding.zeppelin.R;
import aviation.recoding.zeppelin.ui.flights.FlightInfoActivity;
import aviation.recoding.zeppelin.ui.leaderboard.GameActivity;
import aviation.recoding.zeppelin.ui.leaderboard.LeaderboardActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goToLeaderboard(View view)
    {
        Intent intent = new Intent(DashboardActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void goToReceiptScan(View view)
    {
        Intent intent = new Intent(DashboardActivity.this, ReceiptScanActivity.class);
        startActivity(intent);
    }

    public void goToGame(View view)
    {
        Intent intent = new Intent(DashboardActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void goToFlightScan(View view)
    {
        Intent intent = new Intent(DashboardActivity.this, FlightInfoActivity.class);
        startActivity(intent);
    }


}
