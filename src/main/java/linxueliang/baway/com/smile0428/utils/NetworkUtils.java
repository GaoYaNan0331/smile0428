package linxueliang.baway.com.smile0428.utils;


import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import linxueliang.baway.com.smile0428.bean.NetData;
import okhttp3.Call;

/**
 * 1.类的用途:
 * 2.@author:吝雪亮
 * 3.@date:2017/5/3.
 */

public class NetworkUtils {
    public static void requestData(final Handler handler, final int num, String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Message message = new Message();
                message.obj = response;
                message.what = num;
                handler.sendMessage(message);
            }
        });
    }

    public static void requestDataList(final Handler handler, final int num, String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Message message = new Message();
                Gson gson = new Gson();
                NetData netData = gson.fromJson(response, NetData.class);
                List<NetData.ResultBean.RowsBean> rows = netData.getResult().getRows();
                message.obj = rows;
                message.what = num;
                handler.sendMessage(message);
            }
        });
    }

}
