package com.arelalo.correasv;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    public Button buttonCalcular;
    public int distancia , P1, tipocorrea, Desint,  resul;
    public String etDistancia1, Polea1, Polea2, TipoCorrea;
    private static final String[] SPINNER_DATA = new String[] { "Tipo C", "Tipo B", "Tipo A" };
    public double resultado, pulgadas, tension, longitudresultado,lr ;
    public CheckBox checktensas;

    private static final String TAG = "MainActivity";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditText etDistancia = (EditText) findViewById(R.id.etDistancia);
        //EditText etPolea1 = (EditText) findViewById(R.id.etPolea1);
        //EditText etPolea2 = (EditText) findViewById(R.id.etPolea2);
        checktensas = (CheckBox) findViewById(R.id.checktensas);
        buttonCalcular = (Button) findViewById(R.id.buttonCalcular);
        Spinner spn = (Spinner) findViewById(R.id.spinner);
        spn.setOnItemSelectedListener(this);
        spn.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA));

        mAdView = findViewById(R.id.AdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etDistancia = (EditText) findViewById(R.id.etDistancia);
                EditText etPolea1 = (EditText) findViewById(R.id.etPolea1);
                EditText etPolea2 = (EditText) findViewById(R.id.etPolea2);

                etDistancia1 = etDistancia.getText().toString();
                Polea1 = etPolea1.getText().toString();
                Polea2 = etPolea2.getText().toString();

                if (!etDistancia1.equals("") && !Polea1.equals("") && !Polea2.equals(""))     //Comprobacion de que no hay ningun dato vacio

                {
                    Calcularlongitud();


                    alertOneButton();

                    //Toast.makeText(getApplicationContext(), "Tension"+tension+" Longitud" + resultado+" Resultado " + TipoCorrea+" " + resul, Toast.LENGTH_LONG).show();
                }


                else               Toast.makeText(getApplicationContext(), "Introduzca todos los valores", Toast.LENGTH_LONG).show();





            }
        });




    }


    public void alertOneButton() {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Resultado")
                .setMessage("Desarrollo ="+lr+"\n"+"Correa "+TipoCorrea+resul)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }




    private void Calcularlongitud() {
        double Distancia1int = Integer.valueOf(etDistancia1);
        int Polea1int = Integer.valueOf(Polea1);
        int Polea2int = Integer.valueOf(Polea2);


        if (Polea1int == Polea2int)
        {resultado = (2* Distancia1int)+(Math.PI*Polea1int);
           tension =  (16 *(Distancia1int/1000));
        }
            else
        { resultado = (2*Distancia1int) + (1.5707*(Polea1int+Polea2int)) + (Math.pow((Polea1int-Polea2int), 2) / (4*Distancia1int));

                tension =  (16* ((Math.pow(Distancia1int, 2) - (Math.pow(((Polea1int-Polea2int) /2 ), 2))) /1000000) ); }

        resultado = resultado - Desint;
        if (!checktensas.isChecked()) {
            tension = 0 ;
        }
        longitudresultado = resultado - tension;
        pulgadas = longitudresultado  * 0.03937;
        resul = (int) Math.round(pulgadas);
        lr =((double)Math.round(longitudresultado * 100d) / 100d);



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            TipoCorrea = SPINNER_DATA [i];
        switch (i) {
            case 0:
                Desint = 56;

                break;
            case 1:
                Desint = 43;
                break;
            case 2:
                Desint = 33;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
