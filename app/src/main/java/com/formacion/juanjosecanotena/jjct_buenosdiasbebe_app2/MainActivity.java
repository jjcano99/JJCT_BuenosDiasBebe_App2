package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dwActivityMain;
    private boolean menuVisible;

    public static final int PERIODO_MS = 20000;
    public static final int COD_INTENT_ALARMA = 55;

    public String estadoAlarma;
    public int horaAlarmaProgramada;
    public int minutoAlarmaProgramada;
    public boolean botonDisponible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VARIABLES INICIALES

        botonDisponible=true;


        //MENU LATERAL
        menuVisible = false;
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nvMenuLateralMain =(NavigationView) findViewById(R.id.nvMenuLateralMain);
        nvMenuLateralMain.setNavigationItemSelectedListener(new NavigationMenuListener(this));
        dwActivityMain=(DrawerLayout)findViewById(R.id.dwActivityMain);

        //ASIGNACIÓN DE OBJETOS
        final Button bProgramar = (Button)findViewById(R.id.bProgramar);
        final TimePicker tpHora = (TimePicker)findViewById(R.id.tpHora);
        final TextView tvProgramado = (TextView)findViewById(R.id.tvProgramado);


        //LEEMOS LA PREFERENCIA estadoAlarma
        SharedPreferences preferences = getSharedPreferences("Preferencias",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        estadoAlarma = preferences.getString("estadoAlarma","DESACTIVADA");

        //CONFIGURAMOS LA ALARMA SEGÚN EL ESTADO
       // if (estadoAlarma == "ACTIVADA"){
        if(estadoAlarma.equals("ACTIVADA")){
            horaAlarmaProgramada= preferences.getInt("horaAlarmaProgramada",0);
            minutoAlarmaProgramada= preferences.getInt("minutoAlarmaProgramada",0);
            tpHora.setHour(horaAlarmaProgramada);
            tpHora.setMinute(minutoAlarmaProgramada);
            bProgramar.setText("DESACTIVAR");
            tvProgramado.setText("ACTIVADA");
        }
        else{
            bProgramar.setText("ACTIVAR");
            tvProgramado.setText("DESACTIVADA");
        }

        bProgramar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                Log.d("MENSAJE", "Click en PROGRAMAR" );

                if(botonDisponible) {
                    botonDisponible = false;

                    if (estadoAlarma.equals("DESACTIVADA")) { //ACTIVAR

                        horaAlarmaProgramada = tpHora.getHour();
                        minutoAlarmaProgramada = tpHora.getMinute();
                        programarAlarma(horaAlarmaProgramada,minutoAlarmaProgramada,MainActivity.this);
                        estadoAlarma = "ACTIVADA";
                        tvProgramado.setText("ACTIVADA");
                        bProgramar.setText("DESACTIVAR");
                        editor.putString("estadoAlarma", "ACTIVADA");
                        editor.putInt("horaAlarmaProgramada", horaAlarmaProgramada);
                        editor.putInt("minutoAlarmaProgramada", minutoAlarmaProgramada);
                        editor.commit();
                        //Log.d("MENSAJE", "Hora programada: " + (new Integer(tpHora.getHour()).toString()) + " : " + (new Integer(tpHora.getMinute()).toString()));

                    } else { //DESACTIVAR

                        desactivarAlarma(MainActivity.this);
                        estadoAlarma = "DESACTIVADA";
                        tvProgramado.setText("DESACTIVADA");
                        bProgramar.setText("ACTIVAR");
                        editor.putString("estadoAlarma", "DESACTIVADA");
                        editor.commit();
                    }
                    botonDisponible = true;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            if(menuVisible){
                dwActivityMain.closeDrawers();
                menuVisible=false;
            }
            else{

                dwActivityMain.openDrawer(GravityCompat.START);
                menuVisible=true;
            }
        }

        return super.onOptionsItemSelected(item);

    }
/*
    public void cierraMenu(){

        dwActivityMain.closeDrawers();
        menuVisible=false;

    }
*/
    public void programarAlarma(int hora, int minuto, Context contextAlarm){


        Log.d("MENSAJE", "programarAlarma");

        //desactivarAlarma(contextAlarm);

        Intent intentAlarma = new Intent(contextAlarm, AlarmReceiver.class);

        PendingIntent pendingIntentAlarma = PendingIntent.getBroadcast(contextAlarm, COD_INTENT_ALARMA, intentAlarma, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) contextAlarm.getSystemService(Context.ALARM_SERVICE);

        //----------------------
        SimpleDateFormat dateformatter = new SimpleDateFormat("E dd/MM/yyyy 'a las' kk:mm:ss");
/*
                            H   Hour in day (0-23)  Number  0
                            k   Hour in day (1-24)  Number  24
                            K   Hour in am/pm (0-11)    Number  0
                            h   Hour in am/pm (1-12)    Number  12
*/

        Calendar calendarActual = Calendar.getInstance();
        long calendarActualMillis = calendarActual.getTimeInMillis();

        Log.d("MENSAJE", "Hora actual "+ dateformatter.format(calendarActualMillis));

        Calendar calendarProgramado = calendarActual;

        calendarProgramado.set(Calendar.HOUR_OF_DAY,hora);
        calendarProgramado.set(Calendar.MINUTE,minuto);
        calendarProgramado.set(Calendar.SECOND,0);

        long calendarProgramadoMillis = calendarProgramado.getTimeInMillis();

        //Log.d("MENSAJE", "Hora programada "+ dateformatter.format(calendarProgramadoMillis));

        if (calendarProgramadoMillis < calendarActualMillis){

            calendarProgramado.add(Calendar.DATE,1);
            //calendarProgramado.add(Calendar.SECOND,PERIODO_MS);

            calendarProgramadoMillis =  calendarProgramado.getTimeInMillis();

        }
        Log.d("MENSAJE", "Hora programada "+ dateformatter.format(calendarProgramadoMillis));
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendarProgramadoMillis, pendingIntentAlarma);



    }//FIN programarAlarma

public void desactivarAlarma(Context contextAlarm){

    Log.d("MENSAJE", "desactivarAlarma");
    Intent intentCancelar = new Intent(contextAlarm,AlarmReceiver.class);
    PendingIntent pendingIntentCancelar=PendingIntent.getBroadcast(contextAlarm.getApplicationContext(),COD_INTENT_ALARMA, intentCancelar,0);
    AlarmManager alarmManager = (AlarmManager) contextAlarm.getSystemService(ALARM_SERVICE);

    alarmManager.cancel(pendingIntentCancelar);
    pendingIntentCancelar.cancel();

}


}
