package com.wallet.netdollar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.netdollar.Adapters.TransactionAdapter;
import com.wallet.netdollar.R;
import com.wallet.netdollar.Transactions.Transactions;

import java.util.List;

public class Accueil_fragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public RecyclerView recyclerView;
    private List<Transactions> transList;
    private TransactionAdapter transAdapter;

    private int mPage;
    public static Accueil_fragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Accueil_fragment fragment = new Accueil_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);
         setHasOptionsMenu(true);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       // Call<Transactions> call = RetrofitTrans.getInstance().getApi().transactions();
       /* Call <TransactionsResponse> call= RetrofitTrans.getInstance().getApi().transactions();
        call.enqueue(new Callback<TransactionsResponse>() {
            @Override
            public void onResponse(Call<TransactionsResponse> call, Response<TransactionsResponse> response) {
             //   int i=response.body().getTrans();
                transList=response.body().getTrans();
                transAdapter=new TransactionAdapter(getActivity(),transList);
                recyclerView.setAdapter(transAdapter);
            }

            @Override
            public void onFailure(Call<TransactionsResponse> call, Throwable t) {

            }
        });*/
    }
}
