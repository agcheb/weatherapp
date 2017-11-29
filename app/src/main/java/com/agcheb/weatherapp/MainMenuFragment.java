package com.agcheb.weatherapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {

    private final static int VERTICAL = 1;

    private CitiesListListener mainActivity;

    private List<String> elements;

    CheckBox checkBoxPressure;
    CheckBox checkBoxTommorow;
    CheckBox checkBoxWeekly;


    public MainMenuFragment() {
        // Required empty public constructor
    }

    interface CitiesListListener{
        void onListItemClick(int id,boolean chbox1,boolean chbox2,boolean chbox3);
    }

    @Override
    public void onAttach(Context context) {
        mainActivity = (CitiesListListener) context;
        super.onAttach(context);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_edit : //editElement(info.position);
                return true;
            case R.id.menu_delete : //deleteElement(info.position);
                return true;
            default: return super.onContextItemSelected(item);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        elements = new ArrayList<>();
        for (int i = 0; i < 8 ; i++) {
            elements.add("Element" + i);
        }

        View  rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        RecyclerView citiesRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        citiesRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        citiesRecyclerView.setAdapter(new MainMenuFragment.MyAdapter());//Назначим нашему RecyclerView адаптер
        registerForContextMenu(citiesRecyclerView);
        checkBoxPressure = (CheckBox) rootView.findViewById(R.id.checkboxpressure);
        checkBoxTommorow = (CheckBox) rootView.findViewById(R.id.checkboxtommorow);
        checkBoxWeekly = (CheckBox) rootView.findViewById(R.id.checkboxweekly);


        return rootView;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener{

        private TextView cityNameitem;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.city_list_item,parent,false));
            itemView.setOnClickListener(this);
            cityNameitem = (TextView)itemView.findViewById(R.id.city_name_textview);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bind(int position) {
            int defaultcitiesCount = WeatherInCity.getCity(getActivity()).length;
            String category;
            if(defaultcitiesCount > position){
            category = WeatherInCity.getCity(getActivity())[position];
            cityNameitem.setText(category);}
            else {
                 category = elements.get(position-WeatherInCity.getCity(getActivity()).length);
                cityNameitem.setText(category);
            }
        }
        @Override
        public void onClick(View view) {
            showWeather(this.getLayoutPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            getActivity().getMenuInflater().inflate(R.menu.context_menu, contextMenu);

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MainMenuFragment.MyViewHolder> {

        @Override
        public MainMenuFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MainMenuFragment.MyViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(MainMenuFragment.MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return WeatherInCity.getCity(getActivity()).length + elements.size();
        }
    }

    private void showWeather(int cityIndex){
       // mainActivity.onListItemClick(cityIndex,checkBoxPressure.isChecked(),checkBoxTommorow.isChecked(),checkBoxWeekly.isChecked());
    }
}
