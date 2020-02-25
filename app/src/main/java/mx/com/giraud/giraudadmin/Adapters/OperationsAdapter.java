package mx.com.giraud.giraudadmin.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.giraud.giraudadmin.Activities.DetailActivity;
import mx.com.giraud.giraudadmin.Activities.LoginActivity;
import mx.com.giraud.giraudadmin.Activities.MainActivity;
import mx.com.giraud.giraudadmin.Callbacks.SelectedItemsCallback;
import mx.com.giraud.giraudadmin.Models.OperationModel;
import mx.com.giraud.giraudadmin.R;
import mx.com.giraud.giraudadmin.SplashActivity;

import static mx.com.giraud.giraudadmin.Utils.Constants.START_REGISTERING;
import static mx.com.giraud.giraudadmin.Utils.Utils.getSavedUser;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {


    public MainActivity activity;
    private ArrayList<OperationModel> operations;
    private ArrayList<OperationModel> selectedOperations;
    private SelectedItemsCallback selectedItemsCallback;
    private SelectedOperationsAdapter selectedOperationsAdapter;
    private LinearLayoutManager selectedOperationsManager;

    public OperationsAdapter(MainActivity activity,
                             ArrayList<OperationModel> operations,
                             ArrayList<OperationModel> selectedOperations,
                             SelectedItemsCallback selectedItemsCallback,
                             SelectedOperationsAdapter selectedOperationsAdapter,
                             LinearLayoutManager selectedOperationsManager) {
        this.activity = activity;
        this.operations = operations;
        this.selectedOperations = selectedOperations;
        this.selectedItemsCallback = selectedItemsCallback;
        this.selectedOperationsAdapter = selectedOperationsAdapter;
        this.selectedOperationsManager = selectedOperationsManager;

        selectedOperationsAdapter.setOperationsAdapter(this);
    }

    @Override
    public OperationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View homeView = inflater.inflate(R.layout.item_operation, parent, false);

        // Return a new holder instance
        return new OperationsAdapter.ViewHolder(homeView);
    }


    @Override
    public void onBindViewHolder(OperationsAdapter.ViewHolder holder, int position) {
        holder.tvDescription.setText(operations.get(position).getMercancia());
        holder.tvTitle.setText(operations.get(position).getNumReferencia());


        holder.cbSelected.setChecked(operations.get(position).isSelected());
        holder.tvName.setText(operations.get(position).getCliente().getNombre());
        holder.tvLastActivity.setText("Última actividad: " + operations.get(position).getUltimaActividad());

        holder.cbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.cbSelected.isPressed()) {
                if (isChecked) {
                    operations.get(position).setSelected(true);
                    selectedOperations.add(operations.get(position));
                } else {
                    operations.get(position).setSelected(false);
                    selectedOperations.remove(operations.get(position));
                }
                selectedOperationsAdapter.notifyDataSetChanged();
                selectedOperationsManager.scrollToPosition(selectedOperations.size() - 1);
                selectedItemsCallback.selected(selectedOperations.size());
            }
        });

        holder.llCheckBox.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }


    public void scrollTopPosition(OperationModel selectedOperation) {

        for (int i = 0; i < operations.size(); i++) {
            if (selectedOperation.getId() == operations.get(i).getId()) {
                activity.scrollInvoicesToPosition(i);
            }
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvName;
        private TextView tvLastActivity;
        private TextView tvDescription;
        private LinearLayout llItem;
        private LinearLayout llCheckBox;
        private CheckBox cbSelected;
        private LinearLayout llMainContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvLastActivity = itemView.findViewById(R.id.tv_last_activity);
            tvName = itemView.findViewById(R.id.tv_name);
            llItem = itemView.findViewById(R.id.ll_item);
            llCheckBox = itemView.findViewById(R.id.ll_checkbox);
            cbSelected = itemView.findViewById(R.id.cb_selected);
            llMainContainer = itemView.findViewById(R.id.ll_main_container);

        }

        public LinearLayout getLlMainContainer() {
            return llMainContainer;
        }
    }

}
