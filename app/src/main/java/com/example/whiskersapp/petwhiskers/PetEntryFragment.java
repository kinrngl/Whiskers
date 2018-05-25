package com.example.whiskersapp.petwhiskers;

import android.app.DatePickerDialog;
<<<<<<< HEAD
import android.app.ProgressDialog;
=======
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
>>>>>>> temp_acain
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
=======
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
>>>>>>> temp_acain
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.whiskersapp.petwhiskers.Model.Pet;
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
=======

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
>>>>>>> temp_acain

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

<<<<<<< HEAD

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
=======
public class PetEntryFragment extends Fragment{
    private EditText name;
    private EditText breed;
    private EditText eyecolor;
    private EditText furcolor;
    private EditText desc;
    private EditText bday;

    private Button proceed;

    private DatePickerDialog.OnDateSetListener mDatePickerDialog;
    private Spinner petGen, petCat;
>>>>>>> temp_acain

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


<<<<<<< HEAD
        });
=======
        petGen = view.findViewById(R.id.pet_gender);
        petCat = view.findViewById(R.id.pet_category);
        bday = view.findViewById(R.id.pet_bday);
        name = view.findViewById(R.id.pet_name);
        breed = view.findViewById(R.id.pet_breed);
        eyecolor = view.findViewById(R.id.pet_eyecolor);
        furcolor = view.findViewById(R.id.pet_furcolor);
        desc = view.findViewById(R.id.pet_desc);
        proceed = view.findViewById(R.id.pet_proceedAdd);


        List<String> petGenItem = new ArrayList<String>();
        List<String> petCatItem = new ArrayList<String>();

        petCatItem.add("Dog");
        petCatItem.add("Cat");

        petGenItem.add("Male");
        petGenItem.add("Female");

        ArrayAdapter<String> dataAdapterGen = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, petGenItem
        );

        ArrayAdapter<String> dataAdapterCat = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, petCatItem
        );

        petGen.setAdapter(dataAdapterGen);
        petCat.setAdapter(dataAdapterCat);

        bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                       getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatePickerDialog, year, month, day
                );

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDatePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                bday.setText(year+"-"+month+"-"+day);
            }
        };

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedRec();
            }
        });
    }

    public void proceedRec() {
        String petName = name.getText().toString();
        String petBreed = breed.getText().toString();
        String petEyecolor = eyecolor.getText().toString();
        String petFurcolor = furcolor.getText().toString();
        String petDesc = desc.getText().toString();
        String category = petCat.getSelectedItem().toString();
        String gender = petGen.getSelectedItem().toString();
        String petBday = bday.getText().toString();

        if (!petName.isEmpty() && !petBreed.isEmpty() && !petEyecolor.isEmpty() && !petFurcolor.isEmpty()
                && !petDesc.isEmpty() && !category.isEmpty() && !gender.isEmpty() && !petBday.isEmpty()) {

            Fragment fragment = new AddPetPhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name",petName);
            bundle.putString("breed", petBreed);
            bundle.putString("eyecolor", petEyecolor);
            bundle.putString("furcolor", petFurcolor);
            bundle.putString("desc", petDesc);
            bundle.putString("category", category);
            bundle.putString("gender", gender);
            bundle.putString("bday", petBday);

            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        } else {
            Toast.makeText(getContext(), "Please fill up the fields.", Toast.LENGTH_SHORT).show();
        }
>>>>>>> temp_acain
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
