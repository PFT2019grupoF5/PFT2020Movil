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
import java.util.Date;
import java.util.List;


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

        TextView txtPedidoId = (TextView) rowView.findViewById(R.id.txtPedidoId);
        TextView txtPedidoPedEstado = (TextView) rowView.findViewById(R.id.txtPedidoPedEstado);
        TextView txtPedidoPedFecEstim = (TextView) rowView.findViewById(R.id.txtPedidoPedFecEstim);
        TextView txtPedidoFecha = (TextView) rowView.findViewById(R.id.txtPedidoFecha);
        TextView txtPedidoPedRecCodigo = (TextView) rowView.findViewById(R.id.txtPedidoPedRecCodigo);
        TextView txtPedidoPedRecFecha = (TextView) rowView.findViewById(R.id.txtPedidoPedRecFecha);
        TextView txtPedidoComentario = (TextView) rowView.findViewById(R.id.txtPedidoRecComentario);
        TextView txtPedidoUsuarioId = (TextView) rowView.findViewById(R.id.txtPedidoUsuarioId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        txtPedidoId.setText(String.format("#ID: %d", pedidos.get(pos).getId()));

        txtPedidoPedEstado.setText(String.format("PedEstado: %s", pedidos.get(pos).getPedestado()));

        Date pedfecestim = new Date(Long.parseLong(pedidos.get(pos).getPedfecestim()));
        String Spedfecestim = sdf.format(pedfecestim);
        txtPedidoPedFecEstim.setText(String.format("PedFecEstim: %s", Spedfecestim));

        Date fecha= new Date(Long.parseLong(pedidos.get(pos).getFecha()));
        String Sfecha = sdf.format(fecha);
        txtPedidoFecha.setText(String.format("Fecha: %s", Sfecha));

        txtPedidoPedRecCodigo.setText(String.format("PedRecCodigo: %d", pedidos.get(pos).getPedreccodigo()));

        Date pedrecfecha= new Date(Long.parseLong(pedidos.get(pos).getPedrecfecha()));
        String Spedrecfecha = sdf.format(pedrecfecha);
        txtPedidoPedRecFecha.setText(String.format("PedRecFecha: %s", Spedrecfecha));

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