package tops.technologies.ptc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class UserRecyclerAdapter extends RecyclerView.Adapter <UserRecyclerAdapter.ViewHolder>
{

    private List<Model> models;
    private Context context;

    public UserRecyclerAdapter(List<Model> models) {
        this.models = models;
    }

    public void updateList(List<Model> list){
        models = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(models.get(position).getCategory());
        holder.reference.setText(models.get(position).getReference());
        holder.size.setText(models.get(position).getSize());
        holder.quantity.setText(String.valueOf(models.get(position).getQuantity()));
        holder.location.setText(models.get(position).getLocation());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, editdata.class);
                intent.putExtra("id",models.get(position).getId());
                context.startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView category,reference,size,quantity,location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category2);
            reference = itemView.findViewById(R.id.reference2);
            size = itemView.findViewById(R.id.size2);
            quantity = itemView.findViewById(R.id.quantity2);
            location = itemView.findViewById(R.id.location2);

        }
    }
}
