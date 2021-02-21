package com.utec.pft202002;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.utec.pft202002.model.Movimiento;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MovimientoAdapter extends ArrayAdapter<Movimiento> {

    private Context context;
    private List<Movimiento> movimientos;

    public MovimientoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movimiento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movimientos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_movimiento2, parent, false);

//        TextView txtMovimientoId = (TextView) rowView.findViewById(R.id.txtMovimientoId2);
        TextView txtMovimientoFecha = (TextView) rowView.findViewById(R.id.txtMovimientoFecha2);
//        TextView txtMovimientoCantidad = (TextView) rowView.findViewById(R.id.txtMovimientoCantidad);
        TextView txtMovimientoDescripcion = (TextView) rowView.findViewById(R.id.txtMovimientoDescripcion2);
//        TextView txtMovimientoCosto = (TextView) rowView.findViewById(R.id.txtMovimientoCosto);
        TextView txtMovimientoTipoMov = (TextView) rowView.findViewById(R.id.txtMovimientoTipoMov2);
//        TextView txtMovimientoProductoId = (TextView) rowView.findViewById(R.id.txtMovimientoProductoId);
//        TextView txtMovimientoAlmacenamientoId = (TextView) rowView.findViewById(R.id.txtMovimientoAlmacenamientoId);
        TextView txtMovimientoProductoNombre = (TextView) rowView.findViewById(R.id.txtMovimientoProductoNombre2);
        TextView txtMovimientoAlmacenamientoNombre = (TextView) rowView.findViewById(R.id.txtMovimientoAlmacenamientoNombre2);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        Timestamp tFelab = new Timestamp(Long.parseLong(movimientos.get(pos).getFecha()));
        txtMovimientoFecha.setText(sdf.format(new Date(tFelab.getTime())));

//        txtMovimientoId.setText(String.format("#ID: %d", movimientos.get(pos).getId()));
//        txtMovimientoCantidad.setText(String.format("Cantidad: %d", movimientos.get(pos).getCantidad()));
        txtMovimientoDescripcion.setText(String.format("%s", movimientos.get(pos).getDescripcion()));
//        txtMovimientoCosto.setText(String.format("Costo: %f", movimientos.get(pos).getCosto()));
        txtMovimientoTipoMov.setText(String.format("%s", movimientos.get(pos).getTipoMov()));
//        txtMovimientoProductoId.setText(String.format("P: ", movimientos.get(pos).getProducto().getId()));
//        txtMovimientoAlmacenamientoId.setText(String.format("A: ", movimientos.get(pos).getAlmacenamiento().getId()));
        txtMovimientoProductoNombre.setText(String.format("P: %s", movimientos.get(pos).getProducto().getNombre()));
        txtMovimientoAlmacenamientoNombre.setText(String.format("A: %s", movimientos.get(pos).getAlmacenamiento().getNombre()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Movimiento Form
                Intent intent = new Intent(context, MovimientoActivity.class);
                intent.putExtra("movimiento_id", String.valueOf(movimientos.get(pos).getId()));
                intent.putExtra("movimiento_fecha", String.valueOf(movimientos.get(pos).getFecha()));
                intent.putExtra("movimiento_cantidad", String.valueOf(movimientos.get(pos).getCantidad()));
                intent.putExtra("movimiento_descripcion", String.valueOf(movimientos.get(pos).getDescripcion()));
                intent.putExtra("movimiento_costo", String.valueOf(movimientos.get(pos).getCosto()));
                intent.putExtra("movimiento_tipomov", String.valueOf(movimientos.get(pos).getTipoMov()));
                intent.putExtra("movimiento_productoid", String.valueOf(movimientos.get(pos).getProducto().getId()));
                intent.putExtra("movimiento_almacenamientoid", String.valueOf(movimientos.get(pos).getAlmacenamiento().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
