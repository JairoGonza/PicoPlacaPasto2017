package com.example.jairo.picoplacapasto2017;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIF_ALERTA_ID = 0;
    private TextView textView,textView3,textViewestupico,textView8;
    private Spinner lista;
    private String[] datos={"Elige ultimo digito de tu placa","0 - 1","2 - 3","4 - 5","6 - 7","8 - 9",""};
    private String[] valor1={"-","-","-","-","-","-"};
    private int lugar;
    Date fecha = new Date();
    Calendar localCalendar = Calendar.getInstance();
    int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
    int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);
    String picoYplacaHoy;
    private static final String[] PYP = {"4 - 5", "6 - 7", "8 - 9","0 - 1", "2 - 3"};
    private static final Integer[] FESTIVOS = {1, 9, 79, 103, 104, 121, 149, 170, 177, 184, 201, 219, 233, 289, 310, 317, 342, 359};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista=(Spinner)findViewById(R.id.lista);
        textView=(TextView)findViewById(R.id.textView);
        textView3=(TextView)findViewById(R.id.textView3);
        textView8=(TextView)findViewById(R.id.textView8);
        textViewestupico=(TextView)findViewById(R.id.textViewestupico);
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        lista.setAdapter(adaptador);
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        int i=prefe.getInt("placa_pos",0);
        lista.setSelection(i);
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            //en el onItemSelected selecciona los datos que tiene en ese array.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("placa", parent.getItemAtPosition(position).toString());
                editor.putInt("placa_pos", position);
                editor.commit();
                switch (position)
                {
                    case 0:
                        lugar=position;
                        valor1[position]=datos[position];
                        Toast to =Toast.makeText(getApplicationContext(),"Por favor "+valor1[position], Toast.LENGTH_LONG);
                        to.show();
                        notificar();
                        break;
                    case 1:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                    case 2:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                    case 3:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                    case 4:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                    case 5:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                    case 6:
                        lugar=position;
                        valor1[position]=datos[position];
                        consultaporplaca2();
                        notificar();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(Arrays.asList(FESTIVOS).contains(diaAnio) || diaSemana == 1 || diaSemana == 7)
        {// si es festivo o sabado o domingo
            picoYplacaHoy="NO APLICA";
        }
        else
        {
            int j=diaAnio;
            while(j>7)
            {
                j=localCalendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY?j-11:j-6;//si es viernes resta 11 y si es otro dia resta 6
                localCalendar.set(Calendar.DAY_OF_YEAR, j);// cambia la fecha a la fecha restada
            }
            picoYplacaHoy=PYP[localCalendar.get(Calendar.DAY_OF_YEAR)-2];//se resta dos porque lunes comenzo el dia 2
        }
        textView.setText(" "+picoYplacaHoy+"  ");
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",new Locale("es"));
        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);
        textView8.setText(fecha);
    }
    public void notificar()
    {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setLargeIcon((((BitmapDrawable)getResources()
                        .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                .setContentTitle("Pico y placa en Pasto")
                .setContentText(textView.getText())
                .setColor(Color.RED)
                .setTicker("Alerta!");

        Intent notIntent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent contIntent =PendingIntent.getActivity(MainActivity.this, 0, notIntent, 0);
        mBuilder.setContentIntent(contIntent);

        NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());

    }
    public void consultaporplaca2()
    {
        boolean encontrado = false;
        int posicion = 2;
        //primer if else para ver si tiene pico y placa hoy y decirle con color rojo que es hoy
        if(picoYplacaHoy.equals(valor1[lugar]))
        {
            textView.setTextColor(Color.RED);
            textViewestupico.setText("Hoy es tu pico y placa!");
            textViewestupico.setTextColor(Color.RED);
        }
        else
        {
            textView.setTextColor(Color.WHITE);
            textViewestupico.setText(" ");
        }
        //ciclo para comparar si el valor del spinner es igual al PYP en la posicion i
        for (int i = 0; i < PYP.length && !encontrado; i++)
        {
            if (valor1[lugar].equals(PYP[i]))
            {
                encontrado = true;
                posicion = posicion + i;
            }
        }

        while (posicion <= diaAnio)
        {
            localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            posicion=localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? posicion + 11 : posicion + 6;
            localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            if (Arrays.asList(FESTIVOS).contains(posicion))
            {
                posicion=localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ? posicion + 11 : posicion + 6;
                localCalendar.set(Calendar.DAY_OF_YEAR, posicion);
            }
        }
        fecha = localCalendar.getTime();
        SimpleDateFormat fechaEspañol = new SimpleDateFormat("EEEE d 'de' MMMM",new Locale("es"));
        String fechaFinal = fechaEspañol.format(fecha);
        textView3.setText("En " + (posicion - diaAnio) + " dias, " + fechaFinal);
    }
    public void acercade(View view)
    {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }
}