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

import com.utec.pft202002.model.Producto;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;


public class ProductoAdapter3 extends RecyclerView.Adapter<ProductoAdapter3.ProductoAdapterVh> implements Filterable {

    private List<Producto> listaProductos;
    private List<Producto> listaProductosFiltrada;
    private Context context;
    private SelectedProducto selectedProducto;

    public ProductoAdapter3(List<Producto> listaProductos, SelectedProducto selectedProducto) {
        this.listaProductos = listaProductos;
        this.listaProductosFiltrada = listaProductos;
        this.selectedProducto = selectedProducto;
    }

    @NonNull
    @Override
    public ProductoAdapter3.ProductoAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductoAdapter3.ProductoAdapterVh(LayoutInflater.from(context).inflate(R.layout.list_producto3, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter3.ProductoAdapterVh holder, int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        Producto producto = listaProductos.get(position);

        String id = producto.getId().toString();
        String nombre = producto.getNombre();
        String lote = producto.getLote();
        String precio = String.valueOf(producto.getPrecio());

        Timestamp tFelab = new Timestamp(Long.parseLong(producto.getFelab()));
        String felab = sdf.format(new Date(tFelab.getTime()));
        Timestamp tFven = new Timestamp(Long.parseLong(producto.getFven()));
        String fven = sdf.format(new Date(tFven.getTime()));

        String peso = String.valueOf(producto.getPeso());
        String volumen = String.valueOf(producto.getVolumen());
        String estiba = String.valueOf(producto.getEstiba());
        String stkMin = String.valueOf(producto.getStkMin());
        String stkTotal = String.valueOf(producto.getStkTotal());
        String segmentac = producto.getSegmentac().toString();
        String usuarioId = producto.getUsuario().getId().toString();
        String familiaId = producto.getFamilia().getId().toString();

        holder.txtProductoId.setText(id);
        holder.txtProductoNombre.setText(nombre);
        holder.txtProductoLote.setText(lote);
        holder.txtProductoPrecio.setText(precio);
        holder.txtProductoFelab.setText(felab);
        holder.txtProductoFven.setText(fven);
        holder.txtProductoPeso.setText(peso);
        holder.txtProductoVolumen.setText(volumen);
        holder.txtProductoEstiba.setText(estiba);
        holder.txtProductoStkMin.setText(stkMin);
        holder.txtProductoStkTotal.setText(stkTotal);
        holder.txtProductoSegmentac.setText(segmentac);
        holder.txtProductoUsuarioId.setText(usuarioId);
        holder.txtProductoFamiliaId.setText(familiaId);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = listaProductosFiltrada.size();
                    filterResults.values = listaProductosFiltrada;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Producto> resultData = new ArrayList<>();
                    for (Producto producto: listaProductosFiltrada) {
                        if (producto.getNombre().toLowerCase().contains(searchChr)){
                            resultData.add(producto);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaProductos = (List<Producto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedProducto{

        void selectedProducto(Producto producto);

    }

    public class ProductoAdapterVh extends RecyclerView.ViewHolder {

        TextView txtProductoId, txtProductoNombre, txtProductoLote, txtProductoPrecio, txtProductoFelab,
            txtProductoFven, txtProductoPeso, txtProductoVolumen, txtProductoEstiba, txtProductoStkMin,
            txtProductoStkTotal, txtProductoSegmentac, txtProductoUsuarioId, txtProductoFamiliaId;

        public ProductoAdapterVh(@NonNull View itemView) {
            super(itemView);
            txtProductoId = itemView.findViewById(R.id.txtProductoId);
            txtProductoNombre = itemView.findViewById(R.id.txtProductoNombre);
            txtProductoLote = itemView.findViewById(R.id.txtProductoLote);
            txtProductoPrecio = itemView.findViewById(R.id.txtProductoPrecio);
            txtProductoFelab = itemView.findViewById(R.id.txtProductoFelab);
            txtProductoFven = itemView.findViewById(R.id.txtProductoFven);
            txtProductoPeso = itemView.findViewById(R.id.txtProductoPeso);
            txtProductoVolumen = itemView.findViewById(R.id.txtProductoVolumen);
            txtProductoEstiba = itemView.findViewById(R.id.txtProductoEstiba);
            txtProductoStkMin = itemView.findViewById(R.id.txtProductoStkMin);
            txtProductoStkTotal = itemView.findViewById(R.id.txtProductoStkTotal);
            txtProductoSegmentac = itemView.findViewById(R.id.txtProductoSegmentac);
            txtProductoUsuarioId = itemView.findViewById(R.id.txtProductoUsuarioId);
            txtProductoFamiliaId = itemView.findViewById(R.id.txtProductoFamiliaId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedProducto.selectedProducto(listaProductos.get(getAdapterPosition()));
                }
            });
        }
    }
}
