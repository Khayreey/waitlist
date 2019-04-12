package khayreey.khayreey.waitlist;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.Objects;

import khayreey.khayreey.waitlist.data.Contract;
import khayreey.khayreey.waitlist.data.DBHhelper;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add_user;
    ImageView imageView;
    RecyclerView list;

    DBHhelper dbHhelper;
    adapter adapter1;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_user = findViewById(R.id.fab);
        imageView = findViewById(R.id.image);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setHasFixedSize(true);
        list.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        dbHhelper = new DBHhelper(getApplicationContext());
        sqLiteDatabase = dbHhelper.getWritableDatabase();
        cursor = getAllGuest();
        adapter1 = new adapter(getApplicationContext(), cursor);
        list.setAdapter(adapter1);
        if (cursor.getCount() != 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
        {
          Long id= (Long) viewHolder.itemView.getTag();
          removeGuest(id);
          adapter1.swapCursor(getAllGuest());
          if (getAllGuest().getCount()==0)
          {
              imageView.setVisibility(View.VISIBLE);
          }
        }
    }).attachToRecyclerView(list);


    }
    private  long addNewGuest (String name ,String number)
    {

        ContentValues cv =new ContentValues();
        cv.put(Contract.entry.COLUMN_GUEST_NAME,name);
        cv.put(Contract.entry.COLUMN_PARTY_SIZE,number);

        return sqLiteDatabase.insert(Contract.entry.TABLE_NAME,null,cv);

    }

private Boolean removeGuest (Long id)
{

    return sqLiteDatabase.delete(Contract.entry.TABLE_NAME,Contract.entry._ID + "=" + id ,null)>0;
}
    private Cursor getAllGuest()
{
    return sqLiteDatabase.query(Contract.entry.TABLE_NAME,null,null,null,null,null
            ,Contract.entry.COLUMN_TIMESTAMP
);

}

private void showDialog()
{
    final Dialog dialog =new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.new_guest);
    dialog.getWindow().setBackgroundDrawable (new ColorDrawable(Color.TRANSPARENT));
    dialog.setCancelable(false);

    WindowManager.LayoutParams lp=new  WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width=WindowManager.LayoutParams.MATCH_PARENT;
    lp.height=WindowManager.LayoutParams.WRAP_CONTENT;

    final Button add_guest = (Button) dialog.findViewById(R.id.add_guest_btn);
    final Button back = (Button) dialog.findViewById(R.id.back_btn);
    final EditText guest_name=dialog.findViewById(R.id.guest_name);
    final EditText guest_number=dialog.findViewById(R.id.guest_number);

    add_guest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String name=guest_name.getText().toString();
            String number=guest_number.getText().toString();
            if (name.length()==0 || number.length()==0)
            {
                Toast.makeText(getApplicationContext(),"enter valid data",Toast.LENGTH_SHORT).show();

            }
            else
                {
                    addNewGuest(name,number);
                    adapter1.swapCursor(getAllGuest());
                    imageView.setVisibility(View.GONE);
                    dialog.dismiss();
                }
        }
    });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();

    }
});
    dialog.show();
    dialog.getWindow().setAttributes(lp);

}

}
