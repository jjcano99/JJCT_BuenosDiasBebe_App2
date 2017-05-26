package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BuenosDiasActivity extends AppCompatActivity {

    private DrawerLayout dwActivityBuenosDias;
    private ImageView ivImagenBuenosDias;
    private Button bAceptar;
    private boolean menuVisible;
    private String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buenosdias);

        menuVisible = false;

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nvMenuLateralBuenosDias =(NavigationView) findViewById(R.id.nvMenuLateralBuenosDias);
        nvMenuLateralBuenosDias.setNavigationItemSelectedListener(new NavigationMenuListener(this));

        dwActivityBuenosDias=(DrawerLayout)findViewById(R.id.dwActivityBuenosDias);
        ivImagenBuenosDias=(ImageView)findViewById(R.id.ivImagenBuenosDias);
        bAceptar = (Button)findViewById(R.id.bAceptar);


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                foto=null;
            }
            else{
                foto=(String) extras.get("fotoBuenosDias");
            }

        }
        else{
            foto=(String)savedInstanceState.getSerializable("fotoBuenosDias");
        }

if(foto!=null) {
    Log.d("MENSAJE", foto);

    byte[] fotoByte = Base64.decode(foto, Base64.DEFAULT);
    Bitmap fotoBitmap = BitmapFactory.decodeByteArray(fotoByte, 0, fotoByte.length);
    ivImagenBuenosDias.setImageBitmap(fotoBitmap);
}
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                dwActivityBuenosDias.closeDrawers();
                menuVisible=false;
            }
            else{

                dwActivityBuenosDias.openDrawer(GravityCompat.START);
                menuVisible=true;
            }
        }

        return super.onOptionsItemSelected(item);

    }

}
