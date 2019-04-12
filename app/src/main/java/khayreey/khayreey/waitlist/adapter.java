package khayreey.khayreey.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import  khayreey.khayreey.waitlist.data.Contract.entry;
import  khayreey.khayreey.waitlist.adapter.guest;
public class adapter extends RecyclerView.Adapter <guest> {

    Context context;
    Cursor cursor;

    public adapter(Context context, Cursor cursor)
    {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public guest onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new  guest(view);
    }

    @Override
    public void onBindViewHolder(@NonNull guest holder, int position)
    {
       if (!cursor.moveToPosition(position))
       {
           return;
       }
    String name=cursor.getString(cursor.getColumnIndex(entry.COLUMN_GUEST_NAME));
    String size=cursor.getString(cursor.getColumnIndex(entry.COLUMN_PARTY_SIZE));
    long id=cursor.getLong(cursor.getColumnIndex(entry._ID));
     holder.name.setText(name);
     holder.number.setText(size);
     holder.itemView.setTag(id);

    }


    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }

    public void swapCursor (Cursor newCursor)
    {
        if (cursor!=null)
        {
            cursor.close();
        }
        cursor=newCursor;
        if (newCursor!=null)
        {
            this.notifyDataSetChanged();
        }

    }

   public  class guest extends RecyclerView.ViewHolder
{
    TextView name,number;
    public guest(View itemView)
    {
        super(itemView);
        name= (TextView) itemView.findViewById(R.id.name);
        number= (TextView) itemView.findViewById(R.id.number);

    }
}
}
