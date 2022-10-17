package victor.a04_ejercicioinmobiliaria;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import victor.a04_ejercicioinmobiliaria.configuraciones.Constantes;
import victor.a04_ejercicioinmobiliaria.databinding.ActivityMainBinding;
import victor.a04_ejercicioinmobiliaria.modelos.Inmueble;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Inmueble> inmueblesList;
    private ActivityResultLauncher<Intent> addInmuebleLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inmueblesList = new ArrayList<>();
        inicializaLauncgcher();

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: OJO CREAR EL EVENTO
                addInmuebleLauncher.launch(new Intent(MainActivity.this, AddInmuebleActivity.class));
            }
        });
    }
    private void inicializaLauncgcher(){
        addInmuebleLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                if (result.getData().getExtras() != null){
                                    Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);//CREAMOS UNA CLASE PARA LOS TAGS DE LOS BUNDLES
                                    if(inmueble != null){
                                        inmueblesList.add(inmueble);
                                        mostrarInmuebles();
                                    }else{
                                        Toast.makeText(MainActivity.this, "no hay inmueble en el bundle", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "no hay bundle en el intent", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "no tiene intent", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "VENTANA CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mostrarInmuebles() {
        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < inmueblesList.size(); i++) {
            Inmueble inmueble = inmueblesList.get(i);

            View inmuebleview = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view, null);
            TextView lblDireccion = inmuebleview.findViewById(R.id.lblDireccionInmuebleModel);
            TextView lblNumero = inmuebleview.findViewById(R.id.lblNumeroInmuebleModel);
            TextView lblProvincia = inmuebleview.findViewById(R.id.lblProvinciaInmuebleModel);
            TextView lblCiudad = inmuebleview.findViewById(R.id.lblCiudadInmuebleModel);
            RatingBar rbValoracion = inmuebleview.findViewById(R.id.rbValoracionInmuebleModel);

            lblDireccion.setText(inmueble.getDireccion());
            lblNumero.setText(String.valueOf(inmueble.getNumero()));
            lblProvincia.setText(inmueble.getProvincia());
            lblCiudad.setText(inmueble.getCiudad());
            rbValoracion.setRating(inmueble.getValoracion());

            binding.contentMain.contenedor.addView(inmuebleview);
        }
    }
}