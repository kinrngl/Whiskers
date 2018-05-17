package com.example.whiskersapp.petwhiskers;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class PetEntryFragment extends Fragment implements AdapterView.OnItemSelectedListener {



    EditText petPrice,petDesc,pBday;
    Spinner petGender,petSale,petType,petBreed;
    String[] gender ={"Male","Female"};
    String[] SaleType ={"Sale","Adoption"};
    String[] breedType ={"Dog","Cat"};
    String[] catBreed = {"Persian Cat","Maine Coon","Domestic Cat"};
    String[] dogBreed = {"Shih-tzu","Aspin","Siberian Husky","Pug","Shiba Inu","American Bully","French Bully"};
    Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    ProgressDialog progressDialog;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet_entry, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        petPrice = (EditText)getView().findViewById(R.id.pet_price);
        petDesc = (EditText)getView().findViewById(R.id.pet_desc);
        pBday= (EditText)getView().findViewById(R.id.birthday);
        mDatabase = FirebaseDatabase.getInstance().getReference("pet_entry");
        petGender =(Spinner)getView().findViewById(R.id.spinner_gender);
        petGender.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petGender.setAdapter(aa);

        petSale =(Spinner)getView().findViewById(R.id.spinner_Sale);
        petGender.setOnItemSelectedListener(this);
        ArrayAdapter ab = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,SaleType);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petSale.setAdapter(ab);

        petType =(Spinner)getView().findViewById(R.id.spinner_petType);
        petBreed =(Spinner)getView().findViewById(R.id.spinner_petBreed);

        ArrayAdapter ac = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,breedType);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petType.setAdapter(ac);
        petType.setOnItemSelectedListener(this);


        pBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.AppTheme, datePickerListener,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //  datePicker.setCancelable(false);
                datePicker.setTitle("Select the Birthdate");
                datePicker.show();

            }
        });
        progressDialog = new ProgressDialog(getActivity());


        Button button = (Button)getView().findViewById(R.id.add_petEntry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pBreed = petBreed.getSelectedItem().toString();
                String pCategory = petType.getSelectedItem().toString();
                String pGender = petGender.getSelectedItem().toString();
                String pPrice = petPrice.getText().toString();
                String petBday = pBday.getText().toString();
                String pDesc = petDesc.getText().toString();
                String pStatus = petSale.getSelectedItem().toString();
                progressDialog.setMessage("Adding pet...");
                progressDialog.show();

                if (!TextUtils.isEmpty(pBreed) && !TextUtils.isEmpty(pPrice) && !TextUtils.isEmpty(petBday)
                        && !TextUtils.isEmpty(pDesc)) {

                    String id = mDatabase.push().getKey();
                    String ownerId = mAuth.getUid();
                    Pet pet = new Pet(id,pBreed,pCategory,pPrice,petBday,pDesc,pGender,pStatus,ownerId);
                    mDatabase.child(id).setValue(pet);
                    Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Please Fill up Missing Entries", Toast.LENGTH_LONG).show();
                }
            }


        });
    }




                // TODO Auto-generated method stub
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(petType.getSelectedItem().equals("Dog")){

            ArrayAdapter ad = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,dogBreed);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            petBreed.setAdapter(ad);
        }else if(petType.getSelectedItem().equals("Cat")){
            ArrayAdapter ad = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,catBreed);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            petBreed.setAdapter(ad);

        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                String year1 = String.valueOf(selectedYear);
                String month1 = String.valueOf(selectedMonth);
                String day1 = String.valueOf(selectedDay);
            EditText bday1= (EditText)getView().findViewById(R.id.birthday);

            bday1.setText(month1+"/"+day1+"/"+year1);


        }
    };

        @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*public void addPetEntry(View view) {
        String pBreed = petBreed.getSelectedItem().toString();
        String pCategory = petType.getSelectedItem().toString();
        String pGender = petGender.getSelectedItem().toString();
        String pPrice = petPrice.getText().toString();
        String petBday = pBday.getText().toString();
        String pDesc = petDesc.getText().toString();
        String pStatus = petSale.getSelectedItem().toString();
        progressDialog.setMessage("Adding pet...");
        progressDialog.show();

        if (!TextUtils.isEmpty(pBreed) && !TextUtils.isEmpty(pPrice) && !TextUtils.isEmpty(petBday)
                && !TextUtils.isEmpty(pDesc)) {

                String id = mDatabase.push().getKey();
                String ownerId = mAuth.getUid();
                Pet pet = new Pet(id,pBreed,pCategory,pPrice,petBday,pDesc,pGender,pStatus,ownerId);
                mDatabase.child(id).setValue(pet);
                Toast.makeText(getActivity(), "Entry Added", Toast.LENGTH_LONG).show();
            }else{
            Toast.makeText(getActivity(), "Please Fill up Missing Entries", Toast.LENGTH_LONG).show();
        }
    }*/
}
