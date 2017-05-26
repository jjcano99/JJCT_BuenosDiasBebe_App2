package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2.WebService.MyCallbackInterface;
import com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2.WebService.WebServiceAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {

    int horaAlarmaProgramada;
    int minutoAlarmaProgramada;
    String estadoAlarma;

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        Log.d("MENSAJE", "Alarma Recibida " );

        //COMPROBAR QUE LA ALARMA ESTÁ ACTIVADA

        SharedPreferences preferences = context.getSharedPreferences("Preferencias",MODE_PRIVATE);
        estadoAlarma=preferences.getString("estadoAlarma","DESACTIVADA");
        if(estadoAlarma.equalsIgnoreCase("ACTIVADA")) {

            //REPROGRAMAR LA SIGUIENTE ALARMA

            horaAlarmaProgramada = preferences.getInt("horaAlarmaProgramada", 0);
            minutoAlarmaProgramada = preferences.getInt("minutoAlarmaProgramada", 0);
            Log.d("MENSAJE", "Preferences: " + new Integer(horaAlarmaProgramada).toString() + new Integer(minutoAlarmaProgramada).toString());
            MainActivity mainActivity = new MainActivity();
            mainActivity.programarAlarma(horaAlarmaProgramada, minutoAlarmaProgramada, context);

            //HACER PETICIÓN AL SERVIDOR

            new WebServiceAsyncTask<>("DDMMAAAA", Mensaje.class ,new MyCallbackInterface() {
                @Override
                public void onWebServiceEjecutado(Object result) {

                        if(result != null){

                            Mensaje mensaje = (Mensaje)result;
                            String foto = mensaje.getFoto();
                            Log.d("MENSAJE","FOTO : "+foto);

                            Intent intentBuenosDias = new Intent(context,BuenosDiasActivity.class);
                            intentBuenosDias.putExtra("fotoBuenosDias",foto);
                            context.startActivity(intentBuenosDias);
                    }
                }
            }).execute();

        }

    }
}
