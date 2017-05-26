package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class InicioReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        String estadoAlarma;
        int horaAlarmaProgramada;
        int minutoAlarmaProgramada;

        Log.d("MENSAJE","MÓVIL ENCENDIDO");
/*
        Intent intentInicio = new Intent(context, MainActivity.class);

        intentInicio.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intentInicio);

*/

        SharedPreferences preferences = context.getSharedPreferences("Preferencias",MODE_PRIVATE);
        estadoAlarma = preferences.getString("estadoAlarma","DESACTIVADA");

        Log.d("MENSAJE","ESTADO ALARMA : "+ estadoAlarma);

        if(estadoAlarma.equals("ACTIVADA")) {

            horaAlarmaProgramada = preferences.getInt("horaAlarmaProgramada", 0);
            minutoAlarmaProgramada = preferences.getInt("minutoAlarmaProgramada", 0);
            MainActivity mainActivity=new MainActivity();

           // mainActivity.desactivarAlarma(context);
            mainActivity.programarAlarma(horaAlarmaProgramada,minutoAlarmaProgramada,context);
            Log.d("MENSAJE","ALARMA REPROGRAMADA a las " + (new Integer(horaAlarmaProgramada)).toString() + " : "+ (new Integer(minutoAlarmaProgramada)).toString());
        }



    }
}
