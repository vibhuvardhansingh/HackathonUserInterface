package info.andriodhive.hackathonuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.TubeSpeedometer;

public class MeterView extends AppCompatActivity {
    private float swacchtaPoints = 0;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_view);

        TubeSpeedometer speedometer = findViewById(R.id.speed_view);
        b = findViewById(R.id.enter);


        b.setOnClickListener(v -> {
            swacchtaPoints = swacchtaPoints + 1;
            speedometer.speedTo(swacchtaPoints);
        });
    }
}
