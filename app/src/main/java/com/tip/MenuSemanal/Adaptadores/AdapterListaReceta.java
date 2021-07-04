package com.tip.MenuSemanal.Adaptadores;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tip.MenuSemanal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Clases.Menu;
import Clases.Recetas;

import static androidx.navigation.Navigation.findNavController;
import static com.tip.MenuSemanal.R.color.*;


public class AdapterListaReceta extends RecyclerView.Adapter<AdapterListaReceta.ViewHoldersDatos> {

    ArrayList<Recetas> listaRecetas;
    View viewant = null;
    int posant =-1;
    boolean multiselect = false;
    String previo = "";
    String comida = "";
    int hoy;
    int diaAGuardar;
    DatabaseReference db;


    public AdapterListaReceta(@NonNull ArrayList<Recetas> lRecetas, String previo, String comida, String dia) {
        listaRecetas = lRecetas;
        this.previo = previo;
        this.comida =comida;
        if(!dia.equals("")){
            this.diaAGuardar = Integer.parseInt(dia);
        }
        db = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @NotNull
    @Override


    public AdapterListaReceta.ViewHoldersDatos onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recetas, parent, false);
        return new ViewHoldersDatos(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterListaReceta.ViewHoldersDatos holder, int position) {
        //holder.asignardatos(listaRecetas.get(position).getNombre(),listaRecetas.get(position).getDescripcion());
        Recetas receta ;
        holder.asignardatos(listaRecetas.get(position));

        receta = listaRecetas.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(multiselect==true)
                    receta.setSel(!receta.getSel());
                else{
                    if (posant >-1){
                        listaRecetas.get(posant).setSel(false);
                        notifyItemChanged(posant);
                    }
                    receta.setSel(true);
                    posant=position;

                }
                notifyItemChanged(position);
                Bundle arg = new Bundle();
                Bundle paraHome = new Bundle();

                if (!multiselect) {
                    if (previo.equals("menu")){
                        String idMenu = db.push().getKey();
                        long millis=System.currentTimeMillis();
                        int masDias;
                        //Obtengo el dia de hoy para poder calcular que dia es el que esta guardando
                        Date currentDate = new Date ();
                        Calendar c = Calendar.getInstance();
                        c.setTime(currentDate);
                        hoy = c.get(Calendar.DAY_OF_WEEK)-1;
                        if (diaAGuardar >= hoy){
                            masDias = 86400000*(diaAGuardar-hoy);
                        }else{
                            masDias = 86400000*(7+diaAGuardar-hoy);
                        }
                        Menu newMenu = new Menu(millis+masDias,receta.getNombre(), comida, idMenu);

                        //Para guardar un nuevo menu
                        db.child("Menu-Semana").child(idMenu).setValue(newMenu).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isComplete()) {
                                    paraHome.putString("diaGuardado", Integer.toString(diaAGuardar));
                                    findNavController(holder.itemView).navigate(R.id.navigation_home, paraHome);
                                }
                            }
                        });
                    }else {
                        arg.putString("param1", receta.getNombre());
                        findNavController(holder.itemView).navigate(R.id.fragmentAgregarReceta, arg);
                    }
                }
            }
        });

        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (multiselect == true) {
                    for (Recetas rec : listaRecetas) {
                        rec.setSel(false);

                    }
                }
                receta.setSel(true);
                posant=position;
                notifyDataSetChanged();
                multiselect = !multiselect;
                return true;
            }
        });

        if(receta.getSel()==true){
            if (multiselect == true)
                holder.item.setBackgroundColor(Color.GRAY)                                                                                              ;
            else
                holder.item.setBackgroundColor(purple_200);
        } else
            holder.item.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Recetas receta) {
        listaRecetas.add(position, receta);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Recetas receta) {
        int position = listaRecetas.indexOf(receta);
        if(position>=0) {
            listaRecetas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeSelected (){
        ArrayList<Recetas> elim =new ArrayList<Recetas>();
        for(Recetas ing: listaRecetas){
            if(ing.getSel()==true){
                elim.add(ing);
            }
        }
        if(elim.size() > 0) {
            listaRecetas.removeAll(elim);
            notifyDataSetChanged();
        }
    }

    public class ViewHoldersDatos extends RecyclerView.ViewHolder {
        TextView txtnombre;
        TextView txtdescripcion;
        public View item;


        public ViewHoldersDatos(@NonNull @NotNull View itemView) {
            super(itemView);
            this.item= itemView;
            txtnombre=itemView.findViewById(R.id.txtNombreReceta);
            txtdescripcion=itemView.findViewById(R.id.txtDescripcionReceta);

            FloatingActionButton btnAddMenu = itemView.findViewById(R.id.btnAgregarMenu);
            btnAddMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void asignardatos(Recetas receta) {
            txtnombre.setText(receta.getNombre());
            txtdescripcion.setText(receta.getDescripcion());
        }
    }
}
