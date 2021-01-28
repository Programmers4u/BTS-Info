package p4u.btsinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    SignalStrengthListener signalStrengthListener;

    List<CellInfo> cellInfoList;

    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCellInfo();
                Snackbar.make(view, "@p4u bts-info:refresh", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //start the signal strength listener
        signalStrengthListener = new SignalStrengthListener();

        try {
            // TelephonyManager provides system details

            ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(signalStrengthListener,
                    PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE | PhoneStateListener.LISTEN_SERVICE_STATE | SignalStrengthListener.LISTEN_SIGNAL_STRENGTHS
            );

            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

//            this.loadCellInfo(tm);

        } catch (Exception e) {
            Log.d("SignalStrength", "+++++++++++++++++++++++++++++++++++++++++ null array spot 1: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadCellInfo() {

        TextView text7 = findViewById(R.id.text7);
        text7.setMovementMethod(new ScrollingMovementMethod());


        String non = tm.getNetworkOperatorName();
        TextView text1 = findViewById(R.id.text1);
        text1.setText("Netrwork name: " + non );

        //Network Type
        int nt = tm.getNetworkType();
        String mcc = tm.getNetworkOperator();
        TextView text2 = findViewById(R.id.text2);
        text2.setText("MCC: " + mcc.substring(0, mcc.length()-2) );

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }

            List<CellInfo> cellInfoList = tm.getAllCellInfo();
            if (cellInfoList != null) {
                String inf = "";//text7.getText().toString();
                for (final CellInfo cellInfo : cellInfoList)
                {
                    if(cellInfo instanceof CellInfoGsm) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            CellSignalStrengthGsm ssg = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                try {
                                    int ssgsm = ssg.getDbm();
                                    inf = "" + ssg + "";
                                } catch (NullPointerException e) {

                                }
                            } else {
                                inf = inf + cellInfo.toString().replaceAll("\\s|\\}|\\{", "\r\n");
                            }
                        }

                    }

//                    if(cellInfo instanceof CellInfoNr) {
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                            CellSignalStrengthNr ssnr = ((CellInfoNr) cellInfo).getCellSignalStrength();
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                try {
//                                    int ssgsm = ssnr.getDbm();
//                                    inf = "" + ssnr + "";
//                                } catch (NullPointerException e) {
//
//                                }
//                            } else {
//                                inf = inf + cellInfo.toString().replaceAll("\\s|\\}|\\{", "\r\n");
//                            }
//                        }
//                    }

                    if (cellInfo instanceof CellInfoLte) {
                        int cellMcc = ((CellInfoLte) cellInfo).getCellIdentity().getMcc();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

                            CellSignalStrengthLte ss = ((CellInfoLte) cellInfo).getCellSignalStrength();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    try {
                                        int Bandwidth = ((CellInfoLte) cellInfo).getCellIdentity().getBandwidth();
                                        int Dbm = ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
                                        int rssi = ss.getRssi();
                                        inf = "" + rssi + "";
                                    } catch (NullPointerException e) {

                                    }
                                } else {
                                    inf = inf + cellInfo.toString().replaceAll("\\s|\\}|\\{", "\r\n");
                                }
                        }
                    }
                }

                text7.setText("" + inf);

            }
        } catch (NullPointerException npe) {
            Log.d("loadCellInfo", "+++++++++++++++++++++++++++++++++++++++++ null array spot 1: " + npe);

        }

    }

    private class SignalStrengthListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {

            final int cdmaDbm = signalStrength.getCdmaDbm();
            final int cdmaEcio = signalStrength.getCdmaEcio();
            final int evdoSnr = signalStrength.getEvdoSnr();

            // cast to CellInfoLte and call all the CellInfoLte methods you need
            // gets RSRP cell signal strength:
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                final int getRssnr = ((CellInfoLte) signalStrength).getCellSignalStrength().getRssnr();
            }

            String ltestr = signalStrength.toString();
            String[] parts = ltestr.split(" ");

            TextView text3 = findViewById(R.id.text3);
            text3.setText("RSRP: " + parts[9] +" dBm");

            TextView text4 = findViewById(R.id.text4);
            text4.setText("RSRQ: " + parts[10] + " dB");

            TextView text5 = findViewById(R.id.text5);
            text5.setText("RSSNR: "+ parts[11] + "");
//            - ref. sig. signal-to-noise ratio

            TextView text6 = findViewById(R.id.text6);
            text6.setText("LteSignalStrength: " + parts[8]);

            TextView text8 = findViewById(R.id.text8);
            text8.setText("LteCqi: " + parts[12]);

            TextView text9 = findViewById(R.id.text9);
            text9.setText(" " + parts[15]);

            TextView text10 = findViewById(R.id.text10);
            text10.setText("Rssi: " + evdoSnr);
//            mTdScdmaRscp
            /*
            The parts[] array will then contain these elements:
            parts[1] = GsmSignalStrength
            parts[2] = GsmBitErrorRate
            parts[3] = CdmaDbm
            parts[4] = CdmaEcio
            parts[5] = EvdoDbm
            parts[6] = EvdoEcio
            parts[7] = EvdoSnr
            parts[8] = LteSignalStrength
            parts[9] = LteRsrp
            parts[10] = LteRsrq
            parts[11] = LteRssnr
            parts[12] = ?
            parts[13] = ?
            parts[14] = LteCqi
            parts[15] = gsm|lte
             */

            super.onSignalStrengthsChanged(signalStrength);
        }

        @Override
        public void onDataConnectionStateChanged(int state) {

            switch (state) {
                case TelephonyManager.DATA_DISCONNECTED:
                    Toast.makeText(getApplicationContext(), "DISCONNECTED", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.DATA_CONNECTED:
                    Toast.makeText(getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.DATA_CONNECTING:
                    Toast.makeText(getApplicationContext(), "CONNECTING", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.DATA_SUSPENDED:
                    Toast.makeText(getApplicationContext(), "SUSPENDED", Toast.LENGTH_LONG).show();
                    break;
            }
            super.onDataConnectionStateChanged(state);
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {

            if (cellInfo != null) {

                for (final CellInfo info : cellInfo) {

                    //Network Type
                    int nt = tm.getNetworkType();
                    String no = tm.getNetworkOperator();

                }
            } else {
                loadCellInfo();
            }

            super.onCellInfoChanged(cellInfo);
        }
    }
}
