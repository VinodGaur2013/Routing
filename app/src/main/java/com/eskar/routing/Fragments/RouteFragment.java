package com.eskar.routing.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.eskar.routing.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class RouteFragment extends Fragment {

    Button btnSubmit;
    GoogleMap mMap;
    EditText et_source,et_destination;

    public RouteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_route, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        }

        et_source=(EditText)view.findViewById(R.id.et_source);
        et_destination=(EditText)view.findViewById(R.id.et_destination);
        btnSubmit=(Button) view.findViewById(R.id.btnSubmit);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync();

        return view;
    }

}
