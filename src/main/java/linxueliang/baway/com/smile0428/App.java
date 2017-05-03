package linxueliang.baway.com.smile0428;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 1.类的用途:
 * 2.@author:吝雪亮
 * 3.@date:2017/5/3.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        File cacheDir = StorageUtils.getCacheDirectory(this);

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//你要在内存当中要缓存多大像素的图片
                .diskCacheExtraOptions(480, 800, null)//你要在sdcard中缓存多大像素的图片
                .threadPoolSize(3)//设置线程池中有多少个线程
                .threadPriority(Thread.NORM_PRIORITY - 1)//设置线程优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO)//先进先出
                .denyCacheImageMultipleSizesInMemory()//内存缓存的最大值
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//设置内存缓存大小 2M
                .memoryCacheSize(2 * 1024 * 1024)//内存缓存的最大值
                .memoryCacheSizePercentage(10)//设置占用内存的百分比
                .diskCache(new UnlimitedDiskCache(cacheDir))//设置sdcard的缓存目录
                .diskCacheSize(50 * 1024 * 1024)//设置sdcard缓存的大小
                .diskCacheFileCount(100)//设置缓存文件数 例如：我只缓存100张图片
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//将文件名通过hashcode码算出，以防止同名
                .imageDownloader(new BaseImageDownloader(this))//默认，一个图片下载器
                .imageDecoder(new BaseImageDecoder(true))//默认的一个图片的渲染格式  RGB_565 (一个像素占用2个字节)  ARGB_8888(四字节)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())//设置图片的缓存项
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
