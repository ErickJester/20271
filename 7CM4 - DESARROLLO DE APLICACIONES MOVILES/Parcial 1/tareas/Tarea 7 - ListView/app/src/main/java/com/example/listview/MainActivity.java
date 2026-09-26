package com.example.listview;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

public class MainActivity extends Activity {
    private ListView lv;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.listado);

        ArrayList<ListaEntrada> al = new ArrayList<ListaEntrada>();
        al.add(new ListaEntrada(R.drawable.sol, "SOL", "El Sol es la estrella que se encuentra en el centro del sistema solar. Es una esfera de plasma que concentra más del 99% de la masa del sistema y provee la luz y el calor que hacen posible la vida en la Tierra."));
        al.add(new ListaEntrada(R.drawable.mercurio, "MERCURIO", "Mercurio es el planeta más cercano al Sol y el más pequeño del sistema solar. Carece prácticamente de atmósfera, por lo que su superficie presenta temperaturas extremas entre el día y la noche."));
        al.add(new ListaEntrada(R.drawable.venus, "VENUS", "Venus es el segundo planeta desde el Sol. Su densa atmósfera de dióxido de carbono provoca un efecto invernadero muy intenso que lo convierte en el planeta más caliente del sistema solar."));
        al.add(new ListaEntrada(R.drawable.tierra, "TIERRA", "La Tierra es el tercer planeta del sistema solar y el único conocido con condiciones adecuadas para albergar vida, gracias a su atmósfera, su agua líquida y su distancia al Sol."));
        al.add(new ListaEntrada(R.drawable.luna, "LUNA", "La Luna es el único satélite natural de la Tierra. Su gravedad influye en las mareas del planeta y siempre muestra la misma cara hacia nosotros debido al acoplamiento de mareas."));
        al.add(new ListaEntrada(R.drawable.marte, "MARTE", "Marte es el cuarto planeta del sistema solar, conocido como el planeta rojo por el óxido de hierro de su superficie. Ha sido el destino de numerosas misiones robóticas en busca de indicios de agua y vida pasada."));
        al.add(new ListaEntrada(R.drawable.jupiter, "JÚPITER", "Júpiter es el planeta más grande del sistema solar, un gigante gaseoso con una Gran Mancha Roja, una tormenta que lleva siglos activa, y decenas de lunas conocidas."));
        al.add(new ListaEntrada(R.drawable.saturno, "SATURNO", "Saturno es el segundo planeta más grande del sistema solar, famoso por su vistoso sistema de anillos formados principalmente por hielo y roca."));
        al.add(new ListaEntrada(R.drawable.urano, "URANO", "Urano es un gigante de hielo cuyo eje de rotación está inclinado casi 98 grados, por lo que gira prácticamente de costado respecto al resto de los planetas."));
        al.add(new ListaEntrada(R.drawable.neptuno, "NEPTUNO", "Neptuno es el planeta más alejado del Sol. Es un gigante de hielo con los vientos más fuertes registrados en el sistema solar y un intenso color azul."));

        lv = (ListView) findViewById(R.id.xlv_listado);
        lv.setAdapter(new ListaAdapter(this, R.layout.activity_main, al) {
            public void onEntrada(Object o, View v) {
                if (o != null) {
                    TextView texto_superior_entrada = (TextView) v.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((ListaEntrada) o).get_textoEncima());
                    TextView texto_inferior_entrada = (TextView) v.findViewById(R.id.textView_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((ListaEntrada) o).get_textoDebajo());
                    ImageView imagen_entrada = (ImageView) v.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((ListaEntrada) o).get_idImagen());
                }
            }
        });

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                ListaEntrada le = (ListaEntrada) av.getItemAtPosition(i);
                CharSequence cs = "Seleccionado: " + le.get_textoDebajo();
                Toast t = Toast.makeText(MainActivity.this, cs, Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}
