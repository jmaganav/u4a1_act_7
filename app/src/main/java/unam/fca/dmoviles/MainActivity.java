package unam.fca.dmoviles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static int IMAGE_WIDTH = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        final ImageView iv = findViewById(R.id.iv);


      //se comparte la imagen desde la pagina de la misma y no desde el buscador de google
        // Cuando nuestra aplicacion sea iniciada por otra  aplicacion, type no sera nulo
        if (Intent.ACTION_SEND.equals(action) && type != null) {

            //Verificamos que el mime type del dato sea de una imagen
            //https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
            if (type.startsWith("image/")) {

                Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                if (imageUri != null) {
                    //Se ejecuta el proceso de reduccion de la imagen en segundo plano
                    //y no interrumpir
                    new Worker(iv).execute(imageUri);
                }
            }
        }


        //Actividades
        //a) Revisa la urls de referencia, que metodos se ejecutan en el UI Thread en la actividad?
        //b) Ejecuta la aplicacion en modo de depuracion, coloca un breakpoint en el metodo doInBackground y otro en el onPostExecute
        //c) Que ocurre si intentas asignar el bitmap al image view en doInBackground?
        //  con :  this.imageView.get().setImageBitmap(bitmap);
        //d) Revisa la urls de referencia, que procesos se pueden ejecutar en segundo plano?
        //e) Anota tus respuestas y las capturas de pantalla en un documento en Word
        //f) Sube tu codigo al repositorio.
        //g) Sube el documento Word a la plataforma Moodle. Incluye la liga a tu repositorio


    }
}
