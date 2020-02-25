package mx.com.giraud.giraudadmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import mx.com.giraud.giraudadmin.Callbacks.SelectedItemsCallback;
import mx.com.giraud.giraudadmin.Models.OperationModel;
import mx.com.giraud.giraudadmin.R;

public class SelectedOperationsAdapter extends RecyclerView.Adapter<SelectedOperationsAdapter.ViewHolder> {


    public Activity activity;
    private ArrayList<OperationModel> operations;
    private OperationsAdapter operationsAdapter;

    public SelectedOperationsAdapter(Activity activity,
                                     ArrayList<OperationModel> operations) {
        this.activity = activity;
        this.operations = operations;
    }

    @Override
    public SelectedOperationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View homeView = inflater.inflate(R.layout.item_reference_selector, parent, false);

        // Return a new holder instance
        return new SelectedOperationsAdapter.ViewHolder(homeView);
    }

    @Override
    public void onBindViewHolder(SelectedOperationsAdapter.ViewHolder holder, int position) {
        holder.tvReference.setText(operations.get(position).getNumReferencia());

        holder.llContainer.setOnClickListener(v -> {
            if (operationsAdapter != null) {
                operationsAdapter.scrollTopPosition(operations.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvReference;
        private LinearLayout llContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            tvReference = itemView.findViewById(R.id.tv_reference);
            llContainer = itemView.findViewById(R.id.ll_container);
        }
    }

    public void setOperationsAdapter(OperationsAdapter operationsAdapter) {
        this.operationsAdapter = operationsAdapter;
    }
}