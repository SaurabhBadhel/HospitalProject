package com.example.patient_module.Doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patient_module.Module.History;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HistoryAdapter extends FirestoreRecyclerAdapter<History, HistoryAdapter.HistoryHolder> {

    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<History> history) {
        super( history );
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryHolder historyHolder, int i, @NonNull History history) {
        historyHolder.nameList2.setText( history.getName() );
        historyHolder.dobList2.setText( history.getDate());

    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( com.example.patient_module.R.layout.list_history,parent,false );
        return new HistoryAdapter.HistoryHolder(v);
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView nameList2;
        TextView dobList2;
        public HistoryHolder(@NonNull View itemView) {

            super( itemView );
            nameList2=itemView.findViewById( com.example.patient_module.R.id.nameList1 );
            dobList2=itemView.findViewById( com.example.patient_module.R.id.dateList1 );

        }
    }
}
