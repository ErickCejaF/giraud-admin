package mx.com.giraud.giraudadmin.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import mx.com.giraud.giraudadmin.Models.OperationModel;
import mx.com.giraud.giraudadmin.R;

public class DetailOperationsPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<OperationModel> operations;
    private LayoutInflater layoutInflater;

    public DetailOperationsPagerAdapter(Context context, ArrayList<OperationModel> operations) {
        this.context = context;
        this.operations = operations;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return operations.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((View) o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_detail_operation, container, false);
        TextView tvReference = view.findViewById(R.id.tv_reference);
        TextView tvEnterprise = view.findViewById(R.id.tv_enterprise);

        tvEnterprise.setText(operations.get(position).getNumReferencia());
        tvReference.setText(operations.get(position).getCliente().getNombre());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}