package com.mohamedibrahim.ToDoList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class NewItemFragment extends Fragment {

	private OnNewItemAddedListener onNewItemAddedListener;
	//private onSearchRequestListener onSearchRequestListener ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.new_item_fragment, container, false);

		final EditText myEditText = (EditText) v.findViewById(R.id.myEditText);
		final ImageButton addItem = (ImageButton) v.findViewById(R.id.addItem);
		addItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String newItem = myEditText.getText().toString();
				onNewItemAddedListener.onNewItemAdd(newItem);
				myEditText.setText("");

			}
		});
		
		
		final ImageButton searchItem  = (ImageButton) v.findViewById(R.id.searchItem);
		searchItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				//onSearchRequestListener.onSearchRequest();	
			}
		});
		

		return v;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {

			onNewItemAddedListener = (OnNewItemAddedListener) activity;
		//	onSearchRequestListener= (onSearchRequestListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnNewItemAddedListener");
		}
	}

	/*
	 * to encapsulate the functionality of the fragment
	 */
	public interface OnNewItemAddedListener {
		public void onNewItemAdd(String newItem);
	}
	
	
	public interface onSearchRequestListener{
		public void onSearchRequest();
	}

}
