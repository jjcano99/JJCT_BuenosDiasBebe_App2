package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by juanjosecanotena on 23/5/17.
 */

public class NavigationMenuListener implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;

    public NavigationMenuListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String tituloItem = item.getTitle().toString();

        Log.d(getClass().getCanonicalName(),"Opción "+tituloItem);

/*

        if(context.getClass()==MainActivity.class) {

            Intent intent = new Intent(context, BuenosDiasActivity.class);
            context.startActivity(intent);
        }
        else if(context.getClass()==BuenosDiasActivity.class){
            Intent intent = new Intent(context, HistoricoActivity.class);
            context.startActivity(intent);
        }
        else if(context.getClass()==HistoricoActivity.class) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
     //   MainActivity mainActivity = (MainActivity)context;
      //  mainActivity.cierraMenu();

*/
        return true;
    }
}
