package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HistoricoActivity extends AppCompatActivity {


    private DrawerLayout dwActivityHistorico;
    private boolean menuVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        menuVisible = false;

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nvMenuLateralHistorico =(NavigationView) findViewById(R.id.nvMenuLateralHistorico);
        nvMenuLateralHistorico.setNavigationItemSelectedListener(new NavigationMenuListener(this));

        dwActivityHistorico=(DrawerLayout)findViewById(R.id.dwActivityHistorico);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            if(menuVisible){
                dwActivityHistorico.closeDrawers();
                menuVisible=false;
            }
            else{

                dwActivityHistorico.openDrawer(GravityCompat.START);
                menuVisible=true;
            }
        }

        return super.onOptionsItemSelected(item);

    }







}
