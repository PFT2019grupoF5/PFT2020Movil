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

import com.utec.pft202002.model.Producto;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;


public class ProductoAdapter extends ArrayAdapter<Producto> {

    private Context context;
    private List<Producto> productos;

    public ProductoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Producto> objects) {
        super(context, resource, objects);
        this.context = context;
        this.productos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_producto2, parent, false);

        TextView txtProductoId = (TextView) rowView.findViewById(R.id.txtProductoId2);
        TextView txtProductoNombre = (TextView) rowView.findViewById(R.id.txtProductoNombre2);
//        TextView txtProductoLote = (TextView) rowView.findViewById(R.id.txtProductoLote);
//        TextView txtProductoPrecio = (TextView) rowView.findViewById(R.id.txtProductoPrecio);
//        TextView txtProductoFelab = (TextView) rowView.findViewById(R.id.txtProductoFelab);
//        TextView txtProductoFven = (TextView) rowView.findViewById(R.id.txtProductoFven);
//        TextView txtProductoPeso = (TextView) rowView.findViewById(R.id.txtProductoPeso);
//        TextView txtProductoVolumen = (TextView) rowView.findViewById(R.id.txtProductoVolumen);
//        TextView txtProductoEstiba = (TextView) rowView.findViewById(R.id.txtProductoEstiba);
//        TextView txtProductoStkMin = (TextView) rowView.findViewById(R.id.txtProductoStkMin);
//        TextView txtProductoStkTotal = (TextView) rowView.findViewById(R.id.txtProductoStkTotal);
//        TextView txtProductoSegmentac = (TextView) rowView.findViewById(R.id.txtProductoSegmentac);
//        TextView txtProductoUsuarioId = (TextView) rowView.findViewById(R.id.txtProductoUsuarioId);
//        TextView txtProductoFamiliaId = (TextView) rowView.findViewById(R.id.txtProductoFamiliaId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        txtProductoId.setText(String.format("#ID: %d", productos.get(pos).getId()));
        txtProductoNombre.setText(String.format("%s", productos.get(pos).getNombre()));
//        txtProductoLote.setText(String.format("Lote: %s", productos.get(pos).getLote()));
//        txtProductoPrecio.setText(String.format("Precio: %f", productos.get(pos).getPrecio()));
//        Timestamp tFelab = new Timestamp(Long.parseLong(productos.get(pos).getFelab()));
//        txtProductoFelab.setText(sdf.format(new Date(tFelab.getTime())));
//        Timestamp tFven = new Timestamp(Long.parseLong(productos.get(pos).getFven()));
//        txtProductoFven.setText(sdf.format(new Date(tFven.getTime())));
//        txtProductoPeso.setText(String.format("Peso: %f", productos.get(pos).getPeso()));
//        txtProductoVolumen.setText(String.format("Volumen: %f", productos.get(pos).getVolumen()));
//        txtProductoEstiba.setText(String.format("Estiba: %d", productos.get(pos).getEstiba()));
//        txtProductoStkMin.setText(String.format("StkMin: %f", productos.get(pos).getStkMin()));
//        txtProductoStkTotal.setText(String.format("StkTotal: %f", productos.get(pos).getStkTotal()));
//        txtProductoSegmentac.setText(String.format("Segmentac: %s", productos.get(pos).getSegmentac()));
//        txtProductoUsuarioId.setText(String.format("Usuario Id: %d", productos.get(pos).getUsuario().getId()));
//        txtProductoFamiliaId.setText(String.format("Familia Id: %d", productos.get(pos).getFamilia().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //start Activity Producto Form
                Intent intent = new Intent(context, ProductoActivity.class);
                intent.putExtra("producto_id", String.valueOf(productos.get(pos).getId()));
                intent.putExtra("producto_nombre", String.valueOf(productos.get(pos).getNombre()));
                intent.putExtra("producto_lote", String.valueOf(productos.get(pos).getLote()));
                intent.putExtra("producto_precio", String.valueOf(productos.get(pos).getPrecio()));
                intent.putExtra("producto_felab", String.valueOf(productos.get(pos).getFelab()));
                intent.putExtra("producto_fven", String.valueOf(productos.get(pos).getFven()));
                intent.putExtra("producto_peso", String.valueOf(productos.get(pos).getPeso()));
                intent.putExtra("producto_volumen", String.valueOf(productos.get(pos).getVolumen()));
                intent.putExtra("producto_estiba", String.valueOf(productos.get(pos).getEstiba()));
                intent.putExtra("producto_stkmin", String.valueOf(productos.get(pos).getStkMin()));
                intent.putExtra("producto_stktotal", String.valueOf(productos.get(pos).getStkTotal()));
                intent.putExtra("producto_segmentac", String.valueOf(productos.get(pos).getSegmentac()));
                intent.putExtra("producto_usuarioid", String.valueOf(productos.get(pos).getUsuario().getId()));
                intent.putExtra("producto_familiaid", String.valueOf(productos.get(pos).getFamilia().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}
