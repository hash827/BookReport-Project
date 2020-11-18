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
import java.util.concurrent.TimeoutException;

public class MyReportAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<MyReport> list;

    public MyReportAdapter(Context context, int resource, ArrayList<MyReport> list) {
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
    public MyReport getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvDate = (TextView)view.findViewById(R.id.tv_report_date);
            viewHolder.tvPage = (TextView)view.findViewById(R.id.tv_report_page);
            viewHolder.tvReportContent = (TextView)view.findViewById(R.id.tv_report_content);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        MyReport dto = list.get(pos);
        viewHolder.tvDate.setText(dto.getDate());
        viewHolder.tvPage.setText(dto.getPage());
        viewHolder.tvReportContent.setText(dto.getReportContent());

        return view;
    }


    public void setList(ArrayList<MyReport> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvDate = null;
        public TextView tvPage = null;
        public TextView tvReportContent = null;

    }



}
