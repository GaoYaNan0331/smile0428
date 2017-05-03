package linxueliang.baway.com.smile0428;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import linxueliang.baway.com.smile0428.adapter.ViewPagerAdapter;

public class ExamActivityB extends AppCompatActivity {
    private String[] strArr = new String[]{
            "http://pic.58pic.com/58pic/11/30/16/25Y58PIC76i.jpg",
            "http://pic.58pic.com/58pic/12/86/18/99u58PICSZt.jpg",
            "http://t-1.tuzhan.com/078439066590/c-1/l/2012/09/21/09/8c0e60ab5bf049169e63e3812417e306.png",
            "http://iphone.images.paojiao.cn/iphone/paper/20117/4/90302713/paojiao_7532a3d1_280x420.jpg",
            "http://pic36.nipic.com/20131220/2457331_152519640118_2.jpg",
            "http://pic.58pic.com/58pic/15/31/42/60g58PICuxz_1024.gif",
            "http://img1.cache.netease.com/catchpic/E/EF/EF83399E73DDEBAB7898BDE7B1B8D0B8.jpg"
    };
    private int index;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            view_pager.setCurrentItem(index++);
            sendEmptyMessageDelayed(1, 1000);
        }
    };
    private ViewPager view_pager;
    private DisplayImageOptions options;
    private RadioGroup radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_b);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        initPicCache();
        ArrayList<ImageView> listI = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            ImageView imageView = new ImageView(ExamActivityB.this);
            imageView.setImageResource(R.mipmap.native_picture);
            ImageLoader.getInstance().displayImage(strArr[i], imageView, options);
            listI.add(imageView);
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(listI);
        view_pager.setAdapter(viewPagerAdapter);
        handler.sendEmptyMessage(1);
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int num = position % 3;
                for (int i = 0; i < 3; i++) {
                    RadioButton btn = (RadioButton) radio_group.getChildAt(i);
                    if (num == i) {
                        btn.setChecked(true);
                    } else {
                        btn.setChecked(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPicCache() {
        options = new DisplayImageOptions
                .Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }
}
