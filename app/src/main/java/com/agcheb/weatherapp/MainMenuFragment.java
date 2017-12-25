package com.agcheb.weatherapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {

    private final static int VERTICAL = 1;

    private CitiesListListener mainActivity;

    SharedPreferences sPrefs;
    final  String SAVED_CHBOX1 = "chbox1";
    final  String SAVED_CHBOX2 = "chbox2";

    CheckBox checkBoxPressure;
    CheckBox checkBoxTommorow;


    public MainMenuFragment() {
        // Required empty public constructor
    }

    interface CitiesListListener{
        void onListItemClick(int id,boolean chbox1,boolean chbox2);
    }

    @Override
    public void onAttach(Context context) {
        mainActivity = (CitiesListListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        RecyclerView citiesRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view); //Найдем наш RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //Создадим LinearLayoutManager
        layoutManager.setOrientation(VERTICAL);//Обозначим ориентацию
        citiesRecyclerView.setLayoutManager(layoutManager);//Назначим нашему RecyclerView созданный ранее layoutManager
        citiesRecyclerView.setAdapter(new MainMenuFragment.MyAdapter());//Назначим нашему RecyclerView адаптер

        checkBoxPressure = (CheckBox) rootView.findViewById(R.id.checkboxpressure);
        checkBoxTommorow = (CheckBox) rootView.findViewById(R.id.checkboxtommorow);


        return rootView;
    }

    @Override
    public void onPause() {
        savePrefs();
        super.onPause();
    }

    @Override
    public void onResume() {
        loadPrefs();
        super.onResume();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView cityNameitem;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.city_list_item,parent,false));
            itemView.setOnClickListener(this);
            cityNameitem = (TextView)itemView.findViewById(R.id.city_name_textview);
        }

        void bind(int position) {
            String category = WeatherInCity.getCity(getActivity())[position];
            cityNameitem.setText(category);
        }
        @Override
        public void onClick(View view) {
            showWeather(this.getLayoutPosition());
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
            return WeatherInCity.getCity(getActivity()).length;
        }
    }

    private void showWeather(int cityIndex){
        mainActivity.onListItemClick(cityIndex,checkBoxPressure.isChecked(),checkBoxTommorow.isChecked());
    }

    private void savePrefs(){
        sPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefs.edit();
        ed.putBoolean(SAVED_CHBOX1,checkBoxPressure.isChecked());
        ed.putBoolean(SAVED_CHBOX2,checkBoxTommorow.isChecked());
        ed.commit();
    }
    private void loadPrefs(){
        sPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean chbox1 = sPrefs.getBoolean(SAVED_CHBOX1,false);
        boolean chbox2 = sPrefs.getBoolean(SAVED_CHBOX2,false);
        checkBoxPressure.setChecked(chbox1);
        checkBoxTommorow.setChecked(chbox2);
    }
}
