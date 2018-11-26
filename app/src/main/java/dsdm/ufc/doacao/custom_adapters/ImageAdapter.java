package dsdm.ufc.doacao.custom_adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import dsdm.ufc.doacao.R;

public class ImageAdapter extends BaseAdapter {

    private List<Bitmap> bitmaps;
    private Context context;

    public ImageAdapter( Context context ) {
        super();
        this.context = context;
        this.bitmaps = new ArrayList<Bitmap>();
    }

    public void add(Bitmap bitmap) {
        bitmaps.add( bitmaps.size(), bitmap );
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.simple_list_item_image, null);
        ImageView imageView = view.findViewById(R.id.image1);

        Log.w("ADAPTER", ((Integer) position).toString());

        Bitmap bitmap = bitmaps.get(position);
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        imageView.setImageBitmap(bitmap);

        return view;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void add(List<Bitmap> bitmaps) {
        this.bitmaps.addAll(bitmaps);
    }
}
