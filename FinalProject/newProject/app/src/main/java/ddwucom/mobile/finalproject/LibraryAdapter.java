package ddwucom.mobile.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LibraryAdapter extends BaseAdapter {



    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<LibraryDto> list;




    public LibraryAdapter(Context context, int resource, ArrayList<LibraryDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public LibraryDto getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = view.findViewById(R.id.tv_libraryName);
            viewHolder.tvAddress = view.findViewById(R.id.tv_libraryAddress);
            viewHolder.tvTel = view.findViewById(R.id.tv_libraryTel);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        LibraryDto dto = list.get(position);
        viewHolder.tvName.setText(dto.getLibName());
        viewHolder.tvAddress.setText(dto.getAddress());
        viewHolder.tvTel.setText(dto.getTel());


        return view;
    }


    public void setList(ArrayList<LibraryDto> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvName= null;
        public TextView tvAddress = null;
        public TextView tvTel = null;
    }





}
