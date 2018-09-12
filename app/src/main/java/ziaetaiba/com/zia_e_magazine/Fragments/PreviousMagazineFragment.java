package ziaetaiba.com.zia_e_magazine.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ziaetaiba.com.zia_e_magazine.Activites.MainActivity;
import ziaetaiba.com.zia_e_magazine.Adapter.MagazineList_Adapter;
import ziaetaiba.com.zia_e_magazine.Calls.setOtherMagazine;
import ziaetaiba.com.zia_e_magazine.Connection.Connect_Server;
import ziaetaiba.com.zia_e_magazine.Globals.ConnectionStatus;
import ziaetaiba.com.zia_e_magazine.Globals.Constants;
import ziaetaiba.com.zia_e_magazine.Interface.ApiInterface;
import ziaetaiba.com.zia_e_magazine.Models.PreviousMagazineData_Model;
import ziaetaiba.com.zia_e_magazine.Models.PreviousMagazine_Model;
import ziaetaiba.com.zia_e_magazine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousMagazineFragment extends Fragment implements setOtherMagazine {

    public RecyclerView recyclerView;
    public MagazineList_Adapter adapter;
    public ProgressBar progressBar;
    public List<PreviousMagazineData_Model> lisItems;
    public Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public PreviousMagazineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(container != null){
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_previous_magazine, container, false);

        lisItems = new ArrayList<>();
        progressBar = view.findViewById(R.id.previousmagazine_progressBar);
        recyclerView = view.findViewById(R.id.previousmagazine_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(ConnectionStatus.getInstance(context).isOnline()){
            getMagazineList();
        }else{

            Toast.makeText(context,Constants.internet_require,Toast.LENGTH_SHORT).show();
            adapter = new MagazineList_Adapter(context,lisItems);
            recyclerView.setAdapter(adapter);
            adapter.setItemClickListener(PreviousMagazineFragment.this);

        }




        return view;
    }

    private void getMagazineList() {

        Retrofit retrofit = Connect_Server.getApiClient();
        ApiInterface clientAPIs = retrofit.create(ApiInterface.class);
        Call<PreviousMagazine_Model> call = clientAPIs.getPreviousMagazine(Constants.language,Constants.year,Constants.month);
        call.enqueue(new Callback<PreviousMagazine_Model>() {
            @Override
            public void onResponse(Call<PreviousMagazine_Model> call, Response<PreviousMagazine_Model> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    PreviousMagazine_Model previousMagazine_model = response.body();
                    if(previousMagazine_model != null){
                        lisItems.addAll(previousMagazine_model.getYearly());
                        adapter = new MagazineList_Adapter(context,lisItems);
                        recyclerView.setAdapter(adapter);
                        adapter.setItemClickListener(PreviousMagazineFragment.this);


                    }else{
                        Toast.makeText(context,Constants.null_data,Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context,Constants.server_error,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PreviousMagazine_Model> call, Throwable t) {
                Toast.makeText(context,Constants.networkerror,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view,PreviousMagazineData_Model previousMagazineData_model) {

        Constants.month_type = Integer.parseInt(previousMagazineData_model.getId())-1;
        Constants.month = previousMagazineData_model.getMonth();
        Constants.year = previousMagazineData_model.getYear();
        Constants.backStack = false;
        startActivity(new Intent(context, MainActivity.class));
        getActivity().finish();

    }

}
