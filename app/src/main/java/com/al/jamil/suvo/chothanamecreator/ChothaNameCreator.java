package com.al.jamil.suvo.chothanamecreator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ChothaNameCreator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button camScannerOpen;
    Spinner subject;
    EditText name;
    DatePicker dp;
    //Button ok_bnt;
    Button date_bnt;
    boolean onDate=false;
    boolean twoDateBool=false;
    ClipboardManager clipManager;
    String camscannerPackage="com.intsig.camscanner";
    String sub;
    String dat1;
    String dat2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chotha_name_creator);
        Log.i("A", "A");
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try{
            setApp(this);
        }catch (Exception ep){
            Toast.makeText(getBaseContext(),ep.toString(),Toast.LENGTH_LONG).show();
            Log.i("Eror Caught",ep.toString());
        }
    }

    private void init(){
        subject=(Spinner)findViewById(R.id.subSpinner);
        name=(EditText)findViewById(R.id.name);
        dp=(DatePicker)findViewById(R.id.dp);
        ArrayAdapter<CharSequence> subAdapter=ArrayAdapter.createFromResource(this,R.array.subjectName,android.R.layout.simple_spinner_item);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subject.setAdapter(subAdapter);
        clipManager=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        camScannerOpen = (Button) findViewById(R.id.cam_button);
        date_bnt=(Button)findViewById(R.id.date_button);
       // ok_bnt=(Button)findViewById(R.id.ok_button);
        sub="";
        dat1="";
        dat2="";
        subject.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chotha_name_creator, menu);
        return true;
    }

    public void setApp(final Context context) {
        init();

        date_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onDate){
                    String dat=dp.getYear()+"."+dp.getMonth()+"."+dp.getDayOfMonth();
                    if(!twoDateBool){
                        dat1=dat;
                        twoDateBool=true;
                        date_bnt.setText("    From    ");
                    }
                    else{
                        dat2=dat;
                        date_bnt.setText("    From    ");
                    }
                    setName();
                    dp.setVisibility(View.INVISIBLE);
                    onDate=false;

                }
                else{
                    dp.setVisibility(View.VISIBLE);
                    //ok_bnt.setVisibility(View.VISIBLE);
                    date_bnt.setText("     OK     ");
                    onDate=true;
                }

            }
        });


        camScannerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable edt = name.getText();
                String str = edt.toString();
                if (str == null) {
                    Toast.makeText(getApplicationContext(), "Give information and then Click Generate Name", Toast.LENGTH_LONG).show();
                } else {
                    ClipData clipData = ClipData.newPlainText(str, str);
                    clipManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(), "Chotha Name Copied to ClipBoard !!!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getBaseContext(), "Try to Open Cam Scanner", Toast.LENGTH_SHORT).show();
                startCamScanner(context, camscannerPackage);
            }
        });

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        sub=subject.getSelectedItem().toString();
        setName();
    }


    private void setName(){
        String str=sub+" "+dat1;
        if (twoDateBool && dat2!="") str+=" from " +dat2;
        name.setText(str);
    }

    private  void startCamScanner(Context context,String packageName){
        Intent intent=context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent==null){
            Toast.makeText(getBaseContext(),"CamScanner might not installed on your device. Try to install it",Toast.LENGTH_LONG).show();
            intent=new Intent(Intent.ACTION_VIEW);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
        else{
            //intent=new Intent(Intent.ACTION_VIEW);

           // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

}
