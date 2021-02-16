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

import com.utec.pft202002.model.Pedido;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ReporteAdapter extends ArrayAdapter<Pedido> {

    private Context context;
    private List<Pedido> pedidos;

    public ReporteAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pedido> objects) {
        super(context, resource, objects);
        this.context = context;
        this.pedidos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_reporte, parent, false);
        //View rowView = inflater.inflate(R.layout.list_reporte_tabla, parent, false);

        TextView txtPedidoId = (TextView) rowView.findViewById(R.id.txtPedidoId);
        TextView txtPedidoPedEstado = (TextView) rowView.findViewById(R.id.txtPedidoPedEstado);
        TextView txtPedidoPedFecEstim = (TextView) rowView.findViewById(R.id.txtPedidoPedFecEstim);
        TextView txtPedidoFecha = (TextView) rowView.findViewById(R.id.txtPedidoFecha);
        TextView txtPedidoPedRecCodigo = (TextView) rowView.findViewById(R.id.txtPedidoPedRecCodigo);
        TextView txtPedidoPedRecFecha = (TextView) rowView.findViewById(R.id.txtPedidoPedRecFecha);
        TextView txtPedidoComentario = (TextView) rowView.findViewById(R.id.txtPedidoRecComentario);
        TextView txtPedidoUsuarioId = (TextView) rowView.findViewById(R.id.txtPedidoUsuarioId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        txtPedidoId.setText(String.format("#ID: %d", pedidos.get(pos).getId()));
        txtPedidoPedEstado.setText(String.format("PedEstado: %s", pedidos.get(pos).getPedestado()));
        Timestamp tPedFecEstim = new Timestamp(Long.parseLong(pedidos.get(pos).getPedfecestim()));
        txtPedidoPedFecEstim.setText("PedFecEstim: " + sdf.format(new Date(tPedFecEstim.getTime())));
        Timestamp tFecha = new Timestamp(Long.parseLong(pedidos.get(pos).getFecha()));
        txtPedidoFecha.setText("Fecha: " + sdf.format(new Date(tFecha.getTime())));
        txtPedidoPedRecCodigo.setText(String.format("PedRecCodigo: %d", pedidos.get(pos).getPedreccodigo()));
        Timestamp tPedRecFecha = new Timestamp(Long.parseLong(pedidos.get(pos).getPedrecfecha()));
        txtPedidoPedRecFecha.setText("PedRecFecha: " + sdf.format(new Date(tPedRecFecha.getTime())));
        txtPedidoComentario.setText(String.format("Comentario: %s", pedidos.get(pos).getPedreccomentario()));
        txtPedidoUsuarioId.setText(String.format("Usuario Id: %d", pedidos.get(pos).getUsuario().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Pedido Form
                Intent intent = new Intent(context, PedidoActivity.class);
                intent.putExtra("pedido_id", String.valueOf(pedidos.get(pos).getId()));
                intent.putExtra("pedido_pedestado", String.valueOf(pedidos.get(pos).getPedestado()));
                intent.putExtra("pedido_pedfecestim", String.valueOf(pedidos.get(pos).getPedfecestim()));
                intent.putExtra("pedido_fecha", String.valueOf(pedidos.get(pos).getFecha()));
                intent.putExtra("pedido_pedreccodigo", String.valueOf(pedidos.get(pos).getPedreccodigo()));
                intent.putExtra("pedido_pedrecfecha", String.valueOf(pedidos.get(pos).getPedrecfecha()));
                intent.putExtra("pedido_pedreccomentario", String.valueOf(pedidos.get(pos).getPedreccomentario()));
                intent.putExtra("pedido_usuarioid", String.valueOf(pedidos.get(pos).getUsuario().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}