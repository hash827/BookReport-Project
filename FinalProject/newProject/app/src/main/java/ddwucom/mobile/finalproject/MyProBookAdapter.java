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

public class MyProBookAdapter extends BaseAdapter {

    public static final String TAG = "MyBookAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<MyBook> list;
    private NaverNetworkManager networkManager = null;
    private ImageFileManager imageFileManager = null;


    public MyProBookAdapter(Context context, int resource, ArrayList<MyBook> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        imageFileManager = new ImageFileManager(context);
        networkManager = new NaverNetworkManager(context);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public MyBook getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor = view.findViewById(R.id.tvAuthor);
            viewHolder.ivImage = view.findViewById(R.id.ivImage);
            viewHolder.tvIsbn = view.findViewById(R.id.tvIsbn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        MyBook book = list.get(position);
        viewHolder.tvTitle.setText(book.getTitle());
        viewHolder.tvAuthor.setText(book.getAuthor());
        viewHolder.tvIsbn.setText(book.getIsbn());

        /*작성할 부분*/
//         dto의 이미지 주소에 해당하는 이미지 파일이 내부저장소에 있는지 확인
//         ImageFileManager 의 내부저장소에서 이미지 파일 읽어오기 사용
//         이미지 파일이 있을 경우 bitmap, 없을 경우 null 을 반환하므로 bitmap 이 있으면 이미지뷰에 지정
//         없을 경우 GetImageAsyncTask 를 사용하여 이미지 파일 다운로드 수행
        if(book.getImage() == null){
            viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);//없으면 default이미지
            return view;
        }
        // 파일에 있는지 확인
        // dto 의 이미지 주소 정보로 이미지 파일 읽기
        Bitmap savedbitmap = imageFileManager.getBitmapFromTemporary(book.getImage()); //파일 이름
        if(savedbitmap !=null){
            viewHolder.ivImage.setImageBitmap(savedbitmap);
            Log.d(TAG,"Image loading from file");
        }
        else{
            viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);
            new GetImageAsyncTask(viewHolder).execute(book.getImage());
            Log.d(TAG,"Image loading from network");
        }
        return view;
    }


    public void setList(ArrayList<MyBook> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvAuthor = null;
        public ImageView ivImage = null;
        public TextView tvIsbn = null;
    }


    /* 책 이미지를 다운로드 후 내부저장소에 파일로 저장하고 이미지뷰에 표시하는 AsyncTask */
    // 1. 네트워크에서 이미지 다운
    // 2. 뷰홀더에 이미지 설정
    // 3. 이미지 파일 저장
    class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ViewHolder viewHolder;
        String imageAddress;
        //매개변수가 집어넣어야할 viewHolder를 받아옴
        public GetImageAsyncTask(ViewHolder holder) {
            viewHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageAddress = params[0];
            Bitmap result = null;
            result = networkManager.downloadImage(imageAddress);


            return result;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            /*작성할 부분*/
            /*네트워크에서 다운 받은 이미지 파일을 ImageFileManager 를 사용하여 내부저장소에 저장
             * 다운받은 bitmap 을 이미지뷰에 지정*/
            if (bitmap != null){
                viewHolder.ivImage.setImageBitmap(bitmap);
                imageFileManager.saveBitmapToTemporary(bitmap,imageAddress);
            }

        }



    }

}
