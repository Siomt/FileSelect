package com.zhouchatian.fileselect.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.zhouchatian.fileselect.bean.PictureBean;
import com.zhouchatian.fileselect.R;

import java.util.List;

/**
 * GridView 的图片选择
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class PictureAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PictureBean> list;

    public PictureAdapter(List<PictureBean> list, Context context) {
        super();
        this.list = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if (null != list) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_item_image, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setChecked(list.get(position).getCheck());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap img = BitmapFactory.decodeFile(list.get(position).getImagePatch(), options);
        viewHolder.image.setImageBitmap(img);
        return convertView;
    }

    class ViewHolder {
        public CheckBox checkBox;
        public ImageView image;
    }
}



