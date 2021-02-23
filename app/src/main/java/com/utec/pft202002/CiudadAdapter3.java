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

import com.utec.pft202002.model.Ciudad;

import java.util.ArrayList;
import java.util.List;


public class CiudadAdapter3 extends RecyclerView.Adapter<CiudadAdapter3.CiudadAdapterVh> implements Filterable {

    private List<Ciudad> listaCiudades;
    private List<Ciudad> listaCiudadesFiltrada;
    private Context context;
    private SelectedCiudad selectedCiudad;

    public CiudadAdapter3(List<Ciudad> listaCiudades, SelectedCiudad selectedCiudad) {
        this.listaCiudades = listaCiudades;
        this.listaCiudadesFiltrada = listaCiudades;
        this.selectedCiudad = selectedCiudad;
    }

    @NonNull
    @Override
    public CiudadAdapter3.CiudadAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CiudadAdapterVh(LayoutInflater.from(context).inflate(R.layout.list_ciudad2, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CiudadAdapter3.CiudadAdapterVh holder, int position) {

        Ciudad ciudad = listaCiudades.get(position);

        String id = ciudad.getId().toString();
        String nombre = ciudad.getNombre();

        holder.txtCiudadId.setText(id);
        holder.txtCiudadNombre.setText(nombre);

    }

    @Override
    public int getItemCount() {
        return listaCiudades.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = listaCiudadesFiltrada.size();
                    filterResults.values = listaCiudadesFiltrada;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Ciudad> resultData = new ArrayList<>();
                    for (Ciudad ciudad: listaCiudadesFiltrada) {
                        if (ciudad.getNombre().toLowerCase().contains(searchChr)){
                            resultData.add(ciudad);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaCiudades = (List<Ciudad>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedCiudad{

        void selectedCiudad(Ciudad ciudad);

    }

    public class CiudadAdapterVh extends RecyclerView.ViewHolder {

        TextView txtCiudadId;
        TextView txtCiudadNombre;

        public CiudadAdapterVh(@NonNull View itemView) {
            super(itemView);
            txtCiudadId = itemView.findViewById(R.id.txtCiudadId2);
            txtCiudadNombre = itemView.findViewById(R.id.txtCiudadNombre2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedCiudad.selectedCiudad(listaCiudades.get(getAdapterPosition()));
                }
            });
        }
    }
}
