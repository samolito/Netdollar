package com.wallet.netdollar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallet.netdollar.R;
import com.wallet.netdollar.Transactions.Transactions;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionsViewHolder> {

    private Context ctx;
    private List<Transactions>transactionslist;

    public TransactionAdapter(Context ctx, List<Transactions> transactionslist) {
        this.ctx = ctx;
        this.transactionslist = transactionslist;
    }

    @Override
    public TransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(ctx).inflate(R.layout.item_transactions,parent,false);
    return new TransactionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionsViewHolder holder, int position) {
    Transactions trans=transactionslist.get(position);
    holder.txttype.setText(trans.getEmbedded().getRecords().get(position).getId());
    holder.txtmontant.setText(trans.getEmbedded().getRecords().get(position).getOperationCount());
    holder.txtdate.setText(trans.getEmbedded().getRecords().get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return transactionslist.size();
    }
    class TransactionsViewHolder extends RecyclerView.ViewHolder
    {
        TextView txttype;
        TextView txtdate;
        TextView txtmontant;

      public  TransactionsViewHolder(View itemView)
        {
            super(itemView);
            txttype=itemView.findViewById(R.id.txttypetrans);
            txtdate=itemView.findViewById(R.id.txtdatetrans);
            txtmontant=itemView.findViewById(R.id.txtmontanttrans);

        }
    }
}
