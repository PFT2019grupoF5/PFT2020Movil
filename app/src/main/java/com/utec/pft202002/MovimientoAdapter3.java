package com.utec.pft202002;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utec.pft202002.model.Movimiento;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;


public class MovimientoAdapter3 extends RecyclerView.Adapter<MovimientoAdapter3.MovimientoAdapterVh> implements Filterable {

    private List<Movimiento> listaMovimientos;
    private List<Movimiento> listaMovimientosFiltrada;
    private Context context;
    private SelectedMovimiento selectedMovimiento;

    public MovimientoAdapter3(List<Movimiento> listaMovimientos, MovimientoAdapter3.SelectedMovimiento selectedMovimiento) {
        this.listaMovimientos = listaMovimientos;
        this.listaMovimientosFiltrada = listaMovimientos;
        this.selectedMovimiento = selectedMovimiento;
    }

    @NonNull
    @Override
    public MovimientoAdapter3.MovimientoAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MovimientoAdapter3.MovimientoAdapterVh(LayoutInflater.from(context).inflate(R.layout.list_movimiento3, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoAdapter3.MovimientoAdapterVh holder, int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        Movimiento movimiento = listaMovimientos.get(position);

        String id = movimiento.getId().toString();
        String cantidad = String.valueOf(movimiento.getCantidad());
        String descripcion = movimiento.getDescripcion();
        String costo = String.valueOf(movimiento.getCosto());
        String tipoMov = movimiento.getTipoMov().toString();
        String productoId = movimiento.getProducto().getId().toString();
        String almacenamientoId = movimiento.getAlmacenamiento().getId().toString();

        Timestamp tFecha = new Timestamp(Long.parseLong(movimiento.getFecha()));
        String fecha = sdf.format(new Date(tFecha.getTime()));

        holder.txtMovimientoId.setText(id);
        holder.txtMovimientoCantidad.setText(cantidad);
        holder.txtMovimientoDescripcion.setText(descripcion);
        holder.txtMovimientoCosto.setText(costo);
        holder.txtMovimientoTipoMov.setText(tipoMov);
        holder.txtMovimientoProductoId.setText(productoId);
        holder.txtMovimientoAlmacenamientoId.setText(almacenamientoId);
        holder.txtMovimientoFecha.setText(fecha);

    }

    @Override
    public int getItemCount() {
        return listaMovimientos.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = listaMovimientosFiltrada.size();
                    filterResults.values = listaMovimientosFiltrada;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Movimiento> resultData = new ArrayList<>();
                    for (Movimiento movimiento: listaMovimientosFiltrada) {
                        if (movimiento.getDescripcion().toLowerCase().contains(searchChr)){
                            resultData.add(movimiento);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaMovimientos = (List<Movimiento>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedMovimiento{

        void selectedMovimiento(Movimiento movimiento);

    }

    public class MovimientoAdapterVh extends RecyclerView.ViewHolder {

        TextView txtMovimientoId, txtMovimientoCantidad, txtMovimientoDescripcion, txtMovimientoCosto,
            txtMovimientoTipoMov, txtMovimientoProductoId, txtMovimientoAlmacenamientoId, txtMovimientoFecha;

        public MovimientoAdapterVh(@NonNull View itemView) {
            super(itemView);
            txtMovimientoId = itemView.findViewById(R.id.txtMovimientoId);
            txtMovimientoCantidad = itemView.findViewById(R.id.txtMovimientoCantidad);
            txtMovimientoDescripcion = itemView.findViewById(R.id.txtMovimientoDescripcion);
            txtMovimientoCosto = itemView.findViewById(R.id.txtMovimientoCosto);
            txtMovimientoTipoMov = itemView.findViewById(R.id.txtMovimientoTipoMov);
            txtMovimientoProductoId = itemView.findViewById(R.id.txtMovimientoProductoId);
            txtMovimientoAlmacenamientoId = itemView.findViewById(R.id.txtMovimientoAlmacenamientoId);
            txtMovimientoFecha = itemView.findViewById(R.id.txtMovimientoFecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedMovimiento.selectedMovimiento(listaMovimientos.get(getAdapterPosition()));
                }
            });
        }
    }
}

