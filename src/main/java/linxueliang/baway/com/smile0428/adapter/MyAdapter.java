package linxueliang.baway.com.smile0428.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import linxueliang.baway.com.smile0428.R;
import linxueliang.baway.com.smile0428.bean.NetData;

/**
 * 1.类的用途:
 * 2.@author:吝雪亮
 * 3.@date:2017/5/3.
 */

public class MyAdapter extends BaseAdapter {
    private List<NetData.ResultBean.RowsBean> list;
    private Context context;
    private DisplayImageOptions options;

    public MyAdapter(List<NetData.ResultBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
        initPicCache();
    }

    private void initPicCache() {

        options = new DisplayImageOptions
                .Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null);
        }
        ImageView item_image = (ImageView) convertView.findViewById(R.id.item_image);
        TextView item_text_title = (TextView) convertView.findViewById(R.id.item_text_title);
        TextView item_text_title2 = (TextView) convertView.findViewById(R.id.item_text_title2);
        TextView item_text_price = (TextView) convertView.findViewById(R.id.item_text_price);
        TextView item_text_address = (TextView) convertView.findViewById(R.id.item_text_address);

        item_image.setImageResource(R.mipmap.native_picture);
        ImageLoader.getInstance().displayImage(list.get(position).getInfo().getDefault_image(), item_image, options);
        item_text_title.setText(list.get(position).getInfo().getLoupan_name());
        item_text_title2.setText(list.get(position).getInfo().getRegion_title());
        item_text_price.setText(list.get(position).getInfo().getPrice()+"/万元");
        item_text_address.setText(list.get(position).getInfo().getSale_title());
        return convertView;
    }
}
