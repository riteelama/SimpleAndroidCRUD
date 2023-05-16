package Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdb.R;

import java.util.ArrayList;

import data.MyData;
import helper.MyDbHelper;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Activity context;
    ArrayList<MyData> data;
    MyDbHelper myDbHelper;


    public RecyclerViewAdapter(Activity context, ArrayList<MyData> data, MyDbHelper myDbHelper) {
        this.context = context;
        this.data = data;
        this.myDbHelper = myDbHelper;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItems = inflater.inflate(R.layout.recycler_view_items,parent, false);
        ViewHolder viewHolder = new ViewHolder(listItems);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        MyData current = data.get(position);
        holder.txtId.setText(String.valueOf(current.getId()));
        holder.txtName.setText(current.getName());
        holder.txtAddress.setText(current.getAddress());

        holder.deleteIv.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete items");
            builder.setMessage("Are you sure you want to delete the items ?");
            builder.setPositiveButton("Yes", (dialog, which) ->
            {
                myDbHelper.deleteData(current.getId() + "");
                Toast.makeText(context, "Items removed", Toast.LENGTH_LONG).show();
                data.remove(current);
                notifyDataSetChanged();
            });

            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        Log.d("Size count", "Total size is " + data.size());
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress, txtId;
        ImageView deleteIv;
//        int valueId;
        public ViewHolder(View viewItems) {
            super(viewItems);
            txtId = viewItems.findViewById(R.id.idTv);
//            valueId= Integer.parseInt(String.valueOf(txtId));
            txtName = viewItems.findViewById(R.id.nameTv);
            txtAddress = viewItems.findViewById(R.id.addressTv);
            deleteIv = viewItems.findViewById(R.id.deleteIv);
        }
    }
}
