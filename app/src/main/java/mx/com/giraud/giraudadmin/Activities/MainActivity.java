package mx.com.giraud.giraudadmin.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import jdroidcoder.ua.paginationrecyclerview.PaginationRecyclerView;
import mx.com.giraud.giraudadmin.Adapters.OperationsAdapter;
import mx.com.giraud.giraudadmin.Adapters.SelectedOperationsAdapter;
import mx.com.giraud.giraudadmin.Callbacks.BaseCallback;
import mx.com.giraud.giraudadmin.Callbacks.OperationsCallback;
import mx.com.giraud.giraudadmin.Callbacks.SelectedItemsCallback;
import mx.com.giraud.giraudadmin.Models.OperationModel;
import mx.com.giraud.giraudadmin.Models.UserModel;
import mx.com.giraud.giraudadmin.R;
import mx.com.giraud.giraudadmin.Responses.GetOperationsResponse;
import okhttp3.ResponseBody;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static mx.com.giraud.giraudadmin.Utils.Constants.START_REGISTERING;
import static mx.com.giraud.giraudadmin.Utils.Utils.getSavedUser;
import static mx.com.giraud.giraudadmin.Utils.Utils.logOut;
import static mx.com.giraud.giraudadmin.Utils.WebServices.wsGetOperations;
import static mx.com.giraud.giraudadmin.Utils.WebServices.wsPostOperations;

public class MainActivity extends BaseActivity {

    private PaginationRecyclerView rvInvoices;
    private ArrayList<OperationModel> operationModels;
    private ArrayList<OperationModel> selectedOperations;
    private ArrayList<OperationModel> filteredOperations;
    private RecyclerView rvSelected;
    private SelectedOperationsAdapter selectedOperationsAdapter;
    private LinearLayout llEmptySpace;
    private SwipeRefreshLayout srlInvoices;
    private LinearLayout llLogOut;
    private FloatingActionButton flButton;
    private TextView tvUsername;
    private OperationsAdapter operationsAdapter;
    private EditText etSearch;
    private TextView tvEmptySpace;
    private UserModel userModel;
    private int selectedItems;
    private LinearLayoutManager selectedOperationsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvInvoices = findViewById(R.id.rv_invoices);
        srlInvoices = findViewById(R.id.srl_invoices);
        etSearch = findViewById(R.id.et_search);
        llLogOut = findViewById(R.id.ll_logout);
        llEmptySpace = findViewById(R.id.ll_empty_space);
        rvSelected = findViewById(R.id.rv_selected_items);
        tvUsername = findViewById(R.id.tv_username);
        tvEmptySpace = findViewById(R.id.tv_empty_space);
        flButton = findViewById(R.id.fab);

        userModel = getSavedUser().getUser();
        tvUsername.setText(userModel.getNombre());

        selectedOperations = new ArrayList<>();

        srlInvoices.setOnRefreshListener(() -> this.getOperations(false));
        flButton.setImageDrawable(getDrawable(R.drawable.ic_checkmark));

        selectedOperationsAdapter = new SelectedOperationsAdapter(MainActivity.this, selectedOperations);
        rvSelected.setAdapter(selectedOperationsAdapter);

        selectedOperationsManager = new LinearLayoutManager(this);
        selectedOperationsManager.setReverseLayout(true);
        selectedOperationsManager.setStackFromEnd(true);

        rvSelected.setLayoutManager(selectedOperationsManager);

        llLogOut.setOnClickListener(v -> {
            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.AppBottomSheetDialogTheme);
            mBottomSheetDialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
            View sheetView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
            mBottomSheetDialog.setContentView(sheetView);
            mBottomSheetDialog.show();

            TextView tvAccept;
            TextView tvCancel;
            TextView tvUsername;

            tvAccept = mBottomSheetDialog.findViewById(R.id.tv_accept);
            tvCancel = mBottomSheetDialog.findViewById(R.id.tv_cancel);
            tvUsername = mBottomSheetDialog.findViewById(R.id.tv_username);

            tvUsername.setText(userModel.getNombre());

            tvCancel.setOnClickListener(v1 -> mBottomSheetDialog.dismiss());
            tvAccept.setOnClickListener(v1 -> logOut(MainActivity.this));
        });

        flButton.setOnClickListener(v -> {
            if (selectedItems == 0) {
                operationsAdapter.notifyDataSetChanged();

                if (selectedOperations != null) {
                    selectedOperations.clear();
                }

            } else {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra("item_detail", selectedOperations);
                startActivityForResult(detailIntent, START_REGISTERING);
            }
        });

        flButton.setOnLongClickListener(v -> {
            if (selectedOperations != null) {
                selectedOperations.clear();
            }

            if (operationModels != null) {
                for (OperationModel operationModel : operationModels) {
                    operationModel.setSelected(false);
                }
            }
            operationsAdapter.notifyDataSetChanged();
            selectedItems = 0;
            return false;
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (operationModels != null && operationModels.size() > 0) {
                    if (s.length() > 0) {
                        filteredOperations = new ArrayList<>();
                        for (OperationModel operation : operationModels) {
                            if ((operation.getMercancia() != null &&
                                    operation.getMercancia().toLowerCase().contains(s.toString().toLowerCase())) ||
                                    operation.getNumReferencia() != null
                                            && operation.getNumReferencia().toLowerCase().contains(s.toString().toLowerCase())) {
                                filteredOperations.add(operation);
                            }
                            setOperations(filteredOperations, s.toString());
                        }
                    } else {
                        setOperations(operationModels, s.toString());
                    }
                }
            }
        });

        this.getOperations(true);

    }

    private void getOperations(boolean isNotRefreshing) {
        etSearch.getText().clear();

        wsGetOperations(MainActivity.this, isNotRefreshing, new OperationsCallback() {
            @Override
            public void baseResponse(GetOperationsResponse operationsResponse) {
                srlInvoices.setRefreshing(false);

                if (operationModels != null)
                    operationModels.clear();

                operationModels = new ArrayList<>();
                selectedOperations.clear();
                selectedItems = 0;
                selectedOperationsAdapter.notifyDataSetChanged();


                for (OperationModel datum : operationsResponse.getData()) {
                    if (datum.getCancelada_at() == null && datum.getFacturada_at() == null) {
                        operationModels.add(datum);
                    }
                }

                setOperations(operationModels, null);
            }

            @Override
            public void baseError() {
                rvInvoices.setVisibility(View.GONE);
                llEmptySpace.setVisibility(View.VISIBLE);

                tvEmptySpace.setText("Ocurrio un error al consultar sus operaciones, intentalo nuevamente");

                srlInvoices.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == START_REGISTERING) {
            if (resultCode == Activity.RESULT_OK) {
                if (srlInvoices != null) {
                    srlInvoices.setRefreshing(true);
                    getOperations(false);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void setOperations(ArrayList operationModels, String searchingTerm) {

        if (operationModels.size() > 0) {
            rvInvoices.setVisibility(View.VISIBLE);
            llEmptySpace.setVisibility(View.GONE);

            rvInvoices.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            operationsAdapter = new OperationsAdapter(MainActivity.this,
                    operationModels,
                    selectedOperations,
                    numberItems -> {
                        selectedItems = numberItems;
                    },
                    selectedOperationsAdapter,
                    selectedOperationsManager
            );
            rvInvoices.setAdapter(operationsAdapter);

        } else {
            rvInvoices.setVisibility(View.GONE);
            llEmptySpace.setVisibility(View.VISIBLE);
        }

        if (searchingTerm != null) {
            tvEmptySpace.setText("No se encontraror operaciones que contengan " + getString(R.string.quot) + searchingTerm + getString(R.string.quot));
        } else {
            tvEmptySpace.setText("No se encontraror operaciones, intentalo nuevamente");
        }

    }

    public void scrollInvoicesToPosition(int pos) {
        rvInvoices.smoothScrollToPosition(pos);
    }

}
