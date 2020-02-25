package mx.com.giraud.giraudadmin.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import mx.com.giraud.giraudadmin.Adapters.DetailOperationsPagerAdapter;
import mx.com.giraud.giraudadmin.Callbacks.BaseCallback;
import mx.com.giraud.giraudadmin.Dialogs.LoaderDialog;
import mx.com.giraud.giraudadmin.Models.OperationModel;
import mx.com.giraud.giraudadmin.Models.UserModel;
import mx.com.giraud.giraudadmin.R;
import mx.com.giraud.giraudadmin.Utils.WebServices;
import okhttp3.ResponseBody;

import static mx.com.giraud.giraudadmin.Utils.Utils.getSavedUser;
import static mx.com.giraud.giraudadmin.Utils.WebServices.wsPostOperations;

public class DetailActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvStart, tvInRoute, tvLate, tvDespachado, tvDelivered, tvRevalidated;
    private UserModel userModel;
    private ArrayList<OperationModel> operationsModel;
    private LoaderDialog loaderDialog;
    private ViewPager vpReferences;
    private BottomSheetDialog mBottomSheetDialog;
    private DetailOperationsPagerAdapter detailOperationsPagerAdapter;
    private TabLayout tbItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_state);

        userModel = getSavedUser().getUser();

        ivBack = findViewById(R.id.iv_back);
        tvStart = findViewById(R.id.tv_start);
        tvInRoute = findViewById(R.id.tv_in_route);
        tvLate = findViewById(R.id.tv_late);
        tvDespachado = findViewById(R.id.tv_despachado);
        tvDelivered = findViewById(R.id.tv_delivered);
        tvRevalidated = findViewById(R.id.tv_revalidated);
        vpReferences = findViewById(R.id.vp_references);

        tbItems = findViewById(R.id.tb_items);

        ivBack.setOnClickListener(v -> finish());

        tvRevalidated.setOnClickListener(v -> showBottomDialog("Por favor agrega tus observaciones","revalidado", "Revalidado"));
        tvStart.setOnClickListener(v -> showBottomDialog("Por favor agrega tus observaciones","inicioDespacho", "Inicio Despacho"));
        tvInRoute.setOnClickListener(v -> showBottomDialog("Indicar el número de sello y nombre de la línea transportista que lo llevará a destino","mercanciaRuta", "En Ruta"));
        tvLate.setOnClickListener(v -> showBottomDialog("Por favor agrega tus observaciones","demora", "Demora"));
        tvDespachado.setOnClickListener(v -> showBottomDialog("Por favor agrega tus observaciones","despachado", "Despachado"));
        tvDelivered.setOnClickListener(v -> showBottomDialog("Anotar el nombre de la persona que recibio, si la entrega fue en una línea transportista foránea deberá anotar el nombre de esta y el número de guía","mercanciaEntregada", "Entregado"));

        operationsModel = (ArrayList<OperationModel>) this.getIntent().getSerializableExtra("item_detail");
        vpReferences.setVisibility(View.VISIBLE);
        detailOperationsPagerAdapter = new DetailOperationsPagerAdapter(DetailActivity.this, operationsModel);
        vpReferences.setOffscreenPageLimit(1);
        vpReferences.setAdapter(detailOperationsPagerAdapter);
        tbItems.setupWithViewPager(vpReferences);
    }


    private void showBottomDialog(String topInfo, String operation, String name) {
        mBottomSheetDialog = new BottomSheetDialog(DetailActivity.this, R.style.AppBottomSheetDialogTheme);
        mBottomSheetDialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottom_comments, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        TextView tvAccept;
        TextView tvCancel;
        TextView tvTopText;
        TextView tvState;
        EditText etObservations;

        tvTopText = mBottomSheetDialog.findViewById(R.id.tv_top_text);
        tvAccept = mBottomSheetDialog.findViewById(R.id.tv_accept);
        tvCancel = mBottomSheetDialog.findViewById(R.id.tv_cancel);
        tvState = mBottomSheetDialog.findViewById(R.id.tv_state);

        tvTopText.setText(topInfo);

        tvState.setText(name);
        etObservations = mBottomSheetDialog.findViewById(R.id.et_observations);

        tvCancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        tvAccept.setOnClickListener(v -> {
            loaderDialog = new LoaderDialog(DetailActivity.this);
            loaderDialog.setCancelable(false);
            loaderDialog.setCanceledOnTouchOutside(false);
            loaderDialog.show();

            callWs(operation, etObservations.getText().toString());
        });


    }

    private void callWs(String operation, String observation) {
        wsPostOperations(
                DetailActivity.this,
                operationsModel.get(0).getId(),
                operation,
                observation,
                userModel.getId(),
                false,
                new BaseCallback() {
                    @Override
                    public void baseResponse(ResponseBody responseBody) {
                        checkFinished(operation, observation);
                    }

                    @Override
                    public void baseError() {
                        errorWs();
                    }
                });
    }


    private void checkFinished(String operation, String observation) {
        operationsModel.remove(0);
        if (operationsModel.size() > 0) {
            callWs(operation, observation);
        } else {
            loaderDialog.dismiss();
            mBottomSheetDialog.dismiss();
            setResult(Activity.RESULT_OK);
            DetailActivity.this.finish();
            Toast.makeText(DetailActivity.this, "Actividades registradas exitosamente", Toast.LENGTH_SHORT).show();
        }
    }


    private void errorWs() {
        loaderDialog.dismiss();
        detailOperationsPagerAdapter = new DetailOperationsPagerAdapter(DetailActivity.this, operationsModel);
        vpReferences.setOffscreenPageLimit(1);
        vpReferences.setAdapter(detailOperationsPagerAdapter);
        tbItems.setupWithViewPager(vpReferences);
        Toast.makeText(this, "Ocurrio un error al registrar algunas referencias, porfavor intentalo nuevamente+", Toast.LENGTH_SHORT).show();
    }
}



