package pw.jcollado.qrcodes_apptest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class CameraActivity extends AppCompatActivity {
    QREader qrEader;

    @BindView(R.id.camera_view)
    SurfaceView surfaceView;
    @BindView(R.id.numTelefTx)
    TextView numTelefTx;
    @BindView(R.id.qrContentTx)
    TextView qrContentTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        TelephonyManager telemamanger  = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        String getSimSerialNumber = telemamanger.getSimSerialNumber();
        String getSimNumber = telemamanger.getLine1Number();

        numTelefTx.setText(getSimNumber +"/" + getSimSerialNumber);
        startQRReader();

    }

    public void startQRReader(){


        qrEader = new QREader.Builder(this, surfaceView, new QRDataListener() {
            @Override public void onDetected(final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        qrContentTx.setText(data);//stuff that updates ui

                    }
                });

            }
        }).build();
        qrEader.init();
        qrEader.start();


    }
}
