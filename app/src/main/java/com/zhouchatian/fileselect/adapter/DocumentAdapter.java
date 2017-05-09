package com.zhouchatian.fileselect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouchatian.fileselect.bean.DocumentBean;
import com.zhouchatian.fileselect.R;

import java.util.List;

/**
 * 文档选择 Adapter
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */

public class DocumentAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<DocumentBean> list;

    public DocumentAdapter(List<DocumentBean> list, Context context) {
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
            convertView = inflater.inflate(R.layout.simple_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_type);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.size = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box_doc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageResource(list.get(position).getType());
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.size.setText(list.get(position).getSize());
        viewHolder.checkBox.setChecked(list.get(position).isCheck());
        return convertView;
    }

    class ViewHolder {
        public CheckBox checkBox;
        public ImageView image;
        public TextView name;
        public TextView size;
    }
}

