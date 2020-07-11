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


public class ReporteAdapter extends ArrayAdapter<Movimiento> {

    private Context context;
    private List<Movimiento> movimientos;

    public ReporteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movimiento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movimientos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_reporte, parent, false);

        TextView txtReporteMovimientoId = (TextView) rowView.findViewById(R.id.txtReporteMovimientoId);
        TextView txtReporteMovimientoFecha = (TextView) rowView.findViewById(R.id.txtReporteMovimientoFecha);
        TextView txtReporteMovimientoCantidad = (TextView) rowView.findViewById(R.id.txtReporteMovimientoCantidad);
        TextView txtReporteMovimientoDescripcion = (TextView) rowView.findViewById(R.id.txtReporteMovimientoDescripcion);
        TextView txtReporteMovimientoCosto = (TextView) rowView.findViewById(R.id.txtReporteMovimientoCosto);
        TextView txtReporteMovimientoProductoId = (TextView) rowView.findViewById(R.id.txtReporteMovimientoProductoId);
        TextView txtReporteMovimientoAlmacenamientoId = (TextView) rowView.findViewById(R.id.txtReporteMovimientoAlmacenamientoId);

        txtReporteMovimientoId.setText(String.format("#ID: %d", movimientos.get(pos).getId()));
        txtReporteMovimientoFecha.setText(String.format("Fecha: %s", movimientos.get(pos).getFecha().toString()));
        txtReporteMovimientoCantidad.setText(String.format("Cantidad: %d", movimientos.get(pos).getCantidad()));
        txtReporteMovimientoDescripcion.setText(String.format("Descripcion: %s", movimientos.get(pos).getDescripcion()));
        txtReporteMovimientoCosto.setText(String.format("Costo: %f", movimientos.get(pos).getCosto()));
        txtReporteMovimientoProductoId.setText(String.format("Producto Id: %d", movimientos.get(pos).getProducto().getId()));
        txtReporteMovimientoAlmacenamientoId.setText(String.format("Almacen Id: %d", movimientos.get(pos).getAlmacenamiento().getId()));

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
