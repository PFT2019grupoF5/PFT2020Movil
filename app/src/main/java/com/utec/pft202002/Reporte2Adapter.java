package com.utec.pft202002;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.utec.pft202002.model.RenglonReporte;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Reporte2Adapter extends ArrayAdapter<RenglonReporte> {

    private Context context;
    private List<RenglonReporte> renglonesReporte;

    public Reporte2Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<RenglonReporte> objects) {
        super(context, resource, objects);
        this.context = context;
        this.renglonesReporte = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_reporte2, parent, false);

        TextView txtReporte2PedidoPedRecCodigo = (TextView) rowView.findViewById(R.id.txtReporte2PedidoPedRecCodigo);
        TextView txtReporte2PedidoPedFecEstim = (TextView) rowView.findViewById(R.id.txtReporte2PedidoPedFecEstim);
        TextView txtReporte2PedidoFecha = (TextView) rowView.findViewById(R.id.txtReporte2PedidoFecha);
        TextView txtReporte2PedidoPedEstado = (TextView) rowView.findViewById(R.id.txtReporte2PedidoPedEstado);
        TextView txtReporte2ProductoID = (TextView) rowView.findViewById(R.id.txtReporte2ProductoID);
        TextView txtReporte2RenCant = (TextView) rowView.findViewById(R.id.txtReporte2RenCant);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        txtReporte2PedidoPedRecCodigo.setText(String.format("Cod %d", renglonesReporte.get(pos).getPedreccodigo()));
        Timestamp tPedFecEstim = new Timestamp(Long.parseLong(renglonesReporte.get(pos).getPedfecestim()));
        txtReporte2PedidoPedFecEstim.setText("F.Est. " + sdf.format(new Date(tPedFecEstim.getTime())));
        Timestamp tFecha = new Timestamp(Long.parseLong(renglonesReporte.get(pos).getFecha()));
        txtReporte2PedidoFecha.setText("Fecha " + sdf.format(new Date(tFecha.getTime())));
        txtReporte2PedidoPedEstado.setText(String.format("Estado %s", renglonesReporte.get(pos).getPedestado()));
        txtReporte2ProductoID.setText(String.format("ProID %d", renglonesReporte.get(pos).getProducto().getId()));
        txtReporte2RenCant.setText(String.format("Cant %d", renglonesReporte.get(pos).getRencant()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return rowView;
    }

}