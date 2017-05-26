package com.formacion.juanjosecanotena.jjct_buenosdiasbebe_app2.WebService;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by juanjosecanotena on 18/5/17.
 */

public class WebServiceAsyncTask<T> extends AsyncTask<Void,Void,Object> {

// T : Tipo del objeto result

    private static final String RUTA_URL_WEBSERVICES = "http://femxa-ebtm.rhcloud.com/BuenosDiasBebe?fecha=";
    private String fechaWS ="";
    //private T objetoInput;
   // private Context context;
    private Class<T> tipoObjetoOutput;
    private MyCallbackInterface callback;

    //Constructor
    public WebServiceAsyncTask(String fechaWS, /* T objetoInput,  Context context,*/ Class<T> tipoObjetoOutput, MyCallbackInterface callback) {
        this.fechaWS = fechaWS;
        //this.objetoInput = objetoInput;
       // this.context = context;
        this.tipoObjetoOutput = tipoObjetoOutput;
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Void... params) {

        //String objetoInputJSON="";

        URL url=null;
        HttpURLConnection httpURLConnection = null;
        int responseCode=0;
        InputStream inputStream = null;
        //OutputStream outputStream=null;
        Object objetoOutput=null;
        //Boolean objetoInputValido=true;
/*
        if(objetoInput== null) objetoInputValido=false;
        else if(objetoInput.getClass() == String.class && objetoInput=="") objetoInputValido=false;
        else if(objetoInput.getClass() == ArrayList.class &&  ((ArrayList) objetoInput).size() < 1) objetoInputValido = false;

        if (objetoInputValido){
            Gson gson = new Gson();
            objetoInputJSON = gson.toJson(objetoInput);
        }
        else{
            objetoInputJSON="#";
        }
*/
        try {

            url=new URL(RUTA_URL_WEBSERVICES + fechaWS);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            //httpURLConnection.setDoOutput(true);
            //httpURLConnection.setRequestProperty("Accept-Charset","UTF-8");
            //httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

            //outputStream = httpURLConnection.getOutputStream();
            //outputStream.write(String.format("objetoInputJSON=%s", objetoInputJSON).getBytes("utf-8"));

            //Log.d("MENSAJE", "outputStream: " + String.format("parametroJSON=%s", objetoInputJSON));

            responseCode=httpURLConnection.getResponseCode();
            Log.d("MENSAJE","CÓDIGO DE RESPUESTA: "+new Integer(responseCode).toString());

            switch (responseCode) {

                case HttpURLConnection.HTTP_NO_CONTENT:

                    Log.d("MENSAJE", "SIN MENSAJE");
                    break;

                case HttpURLConnection.HTTP_OK:

                    Log.d("MENSAJE", "TODO OK");
                    inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                    Gson gson = new Gson();
                    objetoOutput = new Object();
                    objetoOutput = gson.fromJson(inputStreamReader, tipoObjetoOutput);
                    inputStream.close();

                    break;

                default:
                    Log.d("MENSAJE", "ERROR");
                    break;

            }


        }catch (Exception e){

            e.printStackTrace();
        }

        return objetoOutput;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        callback.onWebServiceEjecutado(result);

    }
}
