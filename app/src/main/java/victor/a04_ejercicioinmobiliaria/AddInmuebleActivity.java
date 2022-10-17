package victor.a04_ejercicioinmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import victor.a04_ejercicioinmobiliaria.configuraciones.Constantes;
import victor.a04_ejercicioinmobiliaria.databinding.ActivityAddInmuebleBinding;
import victor.a04_ejercicioinmobiliaria.modelos.Inmueble;

public class AddInmuebleActivity extends AppCompatActivity {

    private ActivityAddInmuebleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmueble inmueble = crearInmueble();
                if(inmueble != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, inmueble);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(AddInmuebleActivity.this, "faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Inmueble crearInmueble() {

        if(
                binding.txtCiudad.getText().toString().isEmpty() || binding.txtCP.getText().toString().isEmpty() || binding.txtDireccion.getText().toString().isEmpty() || binding.txtNumero.getText().toString().isEmpty() || binding.txtProvincia.getText().toString().isEmpty()
        )
            return null;

        return new Inmueble(
                binding.txtCiudad.getText().toString(),
                Integer.parseInt(binding.txtNumero.getText().toString()),
                binding.txtCP.getText().toString(),
                binding.txtCiudad.getText().toString(),
                binding.txtProvincia.getText().toString(),
                binding.rbValAddInmueble.getRating()
        );
    }
}