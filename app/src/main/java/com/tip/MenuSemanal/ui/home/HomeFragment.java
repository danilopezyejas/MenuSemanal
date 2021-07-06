package com.tip.MenuSemanal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tip.MenuSemanal.Adaptadores.AdapterPestanias;
import com.tip.MenuSemanal.R;
import com.tip.MenuSemanal.dias_semana;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    int diaGuardado = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            diaGuardado = Integer.parseInt(getArguments().getString("diaGuardado"));
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager2 pager;
        AdapterPestanias adapterPestanias;

        pager = root.findViewById(R.id.pager);
        adapterPestanias = new AdapterPestanias(this);
        for (int i = 0; i < 7; i++) {
            adapterPestanias.agregarFragment(new dias_semana(i));
        }

        pager.setAdapter(adapterPestanias);


        TabLayout tabLayout = root.findViewById(R.id.tab_semana);

        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Domingo");
                        break;
                    case 1:
                        tab.setText("Lunes");
                        break;
                    case 2:
                        tab.setText("Martes");
                        break;
                    case 3:
                        tab.setText("Miercoles");
                        break;
                    case 4:
                        tab.setText("Jueves");
                        break;
                    case 5:
                        tab.setText("Viernes");
                        break;
                    case 6:
                        tab.setText("Sábado");
                        break;
                }
            }
        }).attach();

        if (diaGuardado < 0){
            //Para iniciar en el dia de la semana que estoy
            Calendar c = Calendar.getInstance();
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            TabLayout.Tab tab = tabLayout.getTabAt(dayOfWeek-1);
            tab.select();
        }else {
            TabLayout.Tab tab = tabLayout.getTabAt(diaGuardado);
            tab.select();
        }

        return root;
    }
}