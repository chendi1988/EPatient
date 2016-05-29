package com.example11.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example11.myapp.R;

public class Fragment_Main_01 extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.fragment_main_01, null);
	}	
}
