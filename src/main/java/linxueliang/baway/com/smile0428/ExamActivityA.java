package linxueliang.baway.com.smile0428;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import linxueliang.baway.com.smile0428.adapter.MyAdapter;
import linxueliang.baway.com.smile0428.bean.NetData;
import linxueliang.baway.com.smile0428.utils.NetworkUtils;
import linxueliang.baway.com.smile0428.utils.UrlUtils;


public class ExamActivityA extends AppCompatActivity implements View.OnClickListener {
    private ConnectivityManager manager;
    private ListView main_listview;
    private SharedPreferences sp;
    private boolean netFlag;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Gson gson = new Gson();
                    String content = (String) msg.obj;
                    NetData netData = gson.fromJson(content, NetData.class);
                    SharedPreferences.Editor edit = sp.edit();
                    if (sp.getBoolean("flag", true)) {
                        try {
                            PrintWriter printWriter = new PrintWriter(getCacheDir() + File.separator + "content.json");
                            printWriter.println(content);
                            printWriter.flush();
                            printWriter.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        edit.putBoolean("flag", false);
                        edit.commit();
                    }
                    List<NetData.ResultBean.RowsBean> rows = netData.getResult().getRows();
                    MyAdapter myAdapter = new MyAdapter(rows, ExamActivityA.this);
                    main_listview.setAdapter(myAdapter);
                    break;
                case 2:
                    List<NetData.ResultBean.RowsBean> rows1 = (List<NetData.ResultBean.RowsBean>) msg.obj;
                    Collections.sort(rows1, new Comparator<NetData.ResultBean.RowsBean>() {
                        @Override
                        public int compare(NetData.ResultBean.RowsBean o1, NetData.ResultBean.RowsBean o2) {
                            int price1 = o1.getInfo().getPrice();
                            int price2 = o2.getInfo().getPrice();
                            return new Double(price1).compareTo(new Double(price2));
                        }
                    });
                    MyAdapter myAdapter1 = new MyAdapter(rows1, ExamActivityA.this);
                    main_listview.setAdapter(myAdapter1);
                    break;
                case 3:
                    List<NetData.ResultBean.RowsBean> rows2 = (List<NetData.ResultBean.RowsBean>) msg.obj;
                    Collections.sort(rows2, new Comparator<NetData.ResultBean.RowsBean>() {
                        @Override
                        public int compare(NetData.ResultBean.RowsBean o1, NetData.ResultBean.RowsBean o2) {
                            int price1 = o1.getInfo().getPrice();
                            int price2 = o2.getInfo().getPrice();
                            return new Double(price2).compareTo(new Double(price1));
                        }
                    });
                    MyAdapter myAdapter2 = new MyAdapter(rows2, ExamActivityA.this);
                    main_listview.setAdapter(myAdapter2);
                    break;
                case 4:
                    List<NetData.ResultBean.RowsBean> rows3 = (List<NetData.ResultBean.RowsBean>) msg.obj;
                    Collections.sort(rows3, new Comparator<NetData.ResultBean.RowsBean>() {
                        @Override
                        public int compare(NetData.ResultBean.RowsBean o1, NetData.ResultBean.RowsBean o2) {
                            int price1 = o1.getInfo().getPrice();
                            int price2 = o2.getInfo().getPrice();
                            return new Double(price1).compareTo(new Double(price2));
                        }
                    });
                    MyAdapter myAdapter3 = new MyAdapter(rows3, ExamActivityA.this);
                    main_listview.setAdapter(myAdapter3);
                    break;
                case 5:
                    List<NetData.ResultBean.RowsBean> rows4 = (List<NetData.ResultBean.RowsBean>) msg.obj;
                    Collections.sort(rows4, new Comparator<NetData.ResultBean.RowsBean>() {
                        @Override
                        public int compare(NetData.ResultBean.RowsBean o1, NetData.ResultBean.RowsBean o2) {
                            int price1 = o1.getInfo().getPrice();
                            int price2 = o2.getInfo().getPrice();
                            return new Double(price2).compareTo(new Double(price1));
                        }
                    });
                    MyAdapter myAdapter4 = new MyAdapter(rows4, ExamActivityA.this);
                    main_listview.setAdapter(myAdapter4);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_listview = (ListView) findViewById(R.id.main_listview);
        sp = getSharedPreferences("save", MODE_PRIVATE);
        Button main_btn_low = (Button) findViewById(R.id.main_btn_low);
        Button main_btn_high = (Button) findViewById(R.id.main_btn_high);
        main_btn_low.setOnClickListener(this);
        main_btn_high.setOnClickListener(this);
        try {
            netFlag = checkNetworkState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkState() throws Exception {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            // 没有网络
            List<NetData.ResultBean.RowsBean> rows = getRowsBeen();
            MyAdapter myAdapter = new MyAdapter(rows, ExamActivityA.this);
            main_listview.setAdapter(myAdapter);
        } else {
            NetworkUtils.requestData(handler, 1, UrlUtils.URLPATH);
        }
        return flag;
    }

    private List<NetData.ResultBean.RowsBean> getRowsBeen() {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(getCacheDir() + File.separator + "content.json"));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        NetData netData = gson.fromJson(stringBuffer.toString(), NetData.class);
        return netData.getResult().getRows();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_low:
                if (netFlag) {
                    NetworkUtils.requestDataList(handler, 2, UrlUtils.URLPATH);
                } else {
                    List<NetData.ResultBean.RowsBean> rowsBeen = getRowsBeen();
                    Message message = new Message();
                    message.obj = rowsBeen;
                    message.what = 4;
                    handler.sendMessage(message);
                }
                break;
            case R.id.main_btn_high:
                if (netFlag) {
                    NetworkUtils.requestDataList(handler, 3, UrlUtils.URLPATH);
                } else {
                    List<NetData.ResultBean.RowsBean> rowsBeen = getRowsBeen();
                    Message message = new Message();
                    message.obj = rowsBeen;
                    message.what = 5;
                    handler.sendMessage(message);
                }
                break;
        }
    }
}
