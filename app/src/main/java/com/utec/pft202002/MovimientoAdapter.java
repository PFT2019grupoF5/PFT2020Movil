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

import java.util.List;


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
        View rowView = inflater.inflate(R.layout.list_movimiento, parent, false);

        TextView txtMovimientoId = (TextView) rowView.findViewById(R.id.txtMovimientoId);
        TextView txtMovimientoFecha = (TextView) rowView.findViewById(R.id.txtMovimientoFecha);
        TextView txtMovimientoCantidad = (TextView) rowView.findViewById(R.id.txtMovimientoCantidad);
        TextView txtMovimientoDescripcion = (TextView) rowView.findViewById(R.id.txtMovimientoDescripcion);
        TextView txtMovimientoCosto = (TextView) rowView.findViewById(R.id.txtMovimientoCosto);
        TextView txtMovimientoProductoId = (TextView) rowView.findViewById(R.id.txtMovimientoProductoId);
        TextView txtMovimientoAlmacenamientoId = (TextView) rowView.findViewById(R.id.txtMovimientoAlmacenamientoId);

        txtMovimientoId.setText(String.format("#ID: %d", movimientos.get(pos).getId()));
        txtMovimientoFecha.setText(String.format("Fecha: %s", movimientos.get(pos).getFecha().toString()));
        txtMovimientoCantidad.setText(String.format("Cantidad: %d", movimientos.get(pos).getCantidad()));
        txtMovimientoDescripcion.setText(String.format("Descripcion: %s", movimientos.get(pos).getDescripcion()));
        txtMovimientoCosto.setText(String.format("Costo: %f", movimientos.get(pos).getCosto()));
        txtMovimientoProductoId.setText(String.format("Producto Id: %d", movimientos.get(pos).getProducto().getId()));
        txtMovimientoAlmacenamientoId.setText(String.format("Almacen Id: %d", movimientos.get(pos).getAlmacenamiento().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Movimiento Form
                Intent intent = new Intent(context, MovimientoActivity.class);
                intent.putExtra("movimiento_id", String.valueOf(movimientos.get(pos).getId()));
                intent.putExtra("movimiento_fecha", String.valueOf(movimientos.get(pos).getFecha()));
                intent.putExtra("movimiento_cantidad", String.valueOf(movimientos.get(pos).getCantidad()));
                intent.putExtra("movimiento_descripcion", movimientos.get(pos).getDescripcion());
                intent.putExtra("movimiento_costo", String.valueOf(movimientos.get(pos).getCosto()));
                intent.putExtra("movimiento_productoid", String.valueOf(movimientos.get(pos).getProducto().getId()));
                intent.putExtra("movimiento_almacenamientoid", String.valueOf(movimientos.get(pos).getAlmacenamiento().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
