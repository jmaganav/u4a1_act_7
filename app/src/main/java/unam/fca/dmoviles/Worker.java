package unam.fca.dmoviles;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class Worker extends AsyncTask <Uri, Void, Bitmap>  {

    private final static int IMAGE_WIDTH = 400;
    //Es importante usar WeakReference para evitar errores como memory leaks.
    //Esto es tener referencias a objetos que han sido destruidos.
    private WeakReference<ImageView> imageView;

    public Worker(ImageView imageView){
        this.imageView = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {

        Bitmap bitmap = null;

        //doInBackground se ejecuta en segundo plano independientemente de MainActivity. Si MainActivity fuera destruida por falta de recursos
        //por el sistema operativo, el metodo this.imageView.get() regresaria null
        if(this.imageView.get() != null && uris.length > 0){

            try {

                Uri imageUri = uris[0];

                //para compartir imagen hay que visitar la pagina, y ya alli si se puede compartir

                //En la actividad 6 obteniamos el contexto con el metodo getApplicationContext()
                //En un AsyncTask, este metodo no esta disponible.
                //Para obtener el contexto lo hacemos mediante el imageView.
                //Para obtener una referencia al imageview es necesario usar el metodo this,imageview.get()
                //y finalmente obtenemos el contexto con el metodo getContext()

                bitmap = ImageUtils.getScaledBitmapFromUri(this.imageView.get().getContext(),imageUri, IMAGE_WIDTH);

                //Con Thread.sleep, simulamos que el proceso tarda 3 segundos mas
                Thread.sleep(3000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        //El metodo onPostExecute se ejecuta en el UI thread, por lo que podemos asignar la imagen al imageView
        if (this.imageView.get() != null && bitmap != null){
            this.imageView.get().setImageBitmap(bitmap);
        }
    }
}
