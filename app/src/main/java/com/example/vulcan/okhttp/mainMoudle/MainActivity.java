package com.example.vulcan.okhttp.mainMoudle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.text.PrecomputedTextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.PrecomputedText;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.w3c.dom.Text;

import com.example.vulcan.okhttp.R;

import android.view.inputmethod.InputMethodManager;

import com.example.vulcan.okhttp.image.ImageConvert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    // private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    ImageView menu;
    ImageView rightmenu;
    PopupWindow popupWindow;


    private Button AlbumOpen;
    private Button Photography;
    private Button StyleTransfer;
    private Button StyleSelect;
    private ImageView ImageShow;
    public String filePath;
    public Context context = MainActivity.this;

    private static String styleID = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initWindow();
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
        navigationView = (NavigationView) findViewById(R.id.nav);
        menu = (ImageView) findViewById(R.id.main_menu);
        rightmenu = (ImageView) findViewById(R.id.right_menu);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        menu.setOnClickListener(this);
        rightmenu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().toString().equals("    反馈")){
                    Intent intent4 = new Intent(MainActivity.this,FeedbackActivity.class);
                    startActivity(intent4);

                }else {
                    Toast.makeText(MainActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });


        //保存图片的路径
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/360/8.png";
        AlbumOpen = (Button) findViewById(R.id.album_open);
        Photography = (Button) findViewById(R.id.photography);
        StyleTransfer = (Button) findViewById(R.id.style_transfer);
        StyleSelect = (Button) findViewById(R.id.style_select);
        ImageShow = (ImageView) findViewById(R.id.image_show);
        AlbumOpen.setOnClickListener(this);
        Photography.setOnClickListener(this);
        StyleTransfer.setOnClickListener(this);
        StyleSelect.setOnClickListener(this);
        ImageShow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ImageShow.setDrawingCacheEnabled(true);
                        // Bitmap imageBitmap = ImageShow.getDrawingCache();
                        Bitmap bm = ((BitmapDrawable) (ImageShow).getDrawable()).getBitmap();
                        new SaveImageTask().execute(bm);
//                        if (imageBitmap != null) {
//                            new SaveImageTask().execute(imageBitmap);
//                        }

                    }
                });
                builder1.show();
                return true;
            }
        });

    }

    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#FF2400");
            //tintManager.setStatusBarTintColor(statusColor);
            //tintManager.setStatusBarTintEnabled(true);
        }
    }


    //长按保存图片
    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override

        protected String doInBackground(Bitmap... Params) {
            String result = getResources().getString(R.string.save_picture_failed);
            try {
                String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
                System.out.println(root);

                File file = new File(root + "/DCIM/Camera");
                if (!file.exists()) {
                    file.mkdirs();
                }


                String fileName = System.currentTimeMillis() + ".jpg";
                File imageFile = new File(file.getAbsolutePath(), fileName);
                System.out.println(imageFile.getAbsolutePath());
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = Params[0];
                image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
//                result = getResources().getString(R.string.save_picture_success, file.getAbsolutePath());
                try {
                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
                            imageFile.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(root + "/DCIM/Camera")));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            ImageShow.setDrawingCacheEnabled(false);
        }


    }

    //风格数据
    public List<ItemAdapter.DataHolder> iniDatas() {
        List<ItemAdapter.DataHolder> list = new ArrayList<ItemAdapter.DataHolder>();
        ItemAdapter.DataHolder dataHolder1 = new ItemAdapter.DataHolder("candy", R.drawable.candy);
        ItemAdapter.DataHolder dataHolder2 = new ItemAdapter.DataHolder("cubist", R.drawable.cubist);
        ItemAdapter.DataHolder dataHolder3 = new ItemAdapter.DataHolder("feathers", R.drawable.feathers);
        ItemAdapter.DataHolder dataHolder4 = new ItemAdapter.DataHolder("starry", R.drawable.starry);
        ItemAdapter.DataHolder dataHolder5 = new ItemAdapter.DataHolder("udnie", R.drawable.udnie);
        ItemAdapter.DataHolder dataHolder6 = new ItemAdapter.DataHolder("wave", R.drawable.wave);
        ItemAdapter.DataHolder dataHolder7 = new ItemAdapter.DataHolder("scream", R.drawable.scream);
        ItemAdapter.DataHolder dataHolder8 = new ItemAdapter.DataHolder("mosaic", R.drawable.mosaic);
        list.add(dataHolder1);
        list.add(dataHolder2);
        list.add(dataHolder3);
        list.add(dataHolder4);
        list.add(dataHolder5);
        list.add(dataHolder6);
        list.add(dataHolder7);
        list.add(dataHolder8);

        return list;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.main_menu://点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;

            case R.id.right_menu://点击右上角菜单，实现登录
                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                break;
            case R.id.album_open:
                //打开手机本地相册;
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0x1);
                break;
            case R.id.photography:
                File fos = null;
                try {
                    fos = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zhycheng.jpg");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Uri u = Uri.fromFile(fos);
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                // i.putExtra(MediaStore.EXTRA_OUTPUT, u);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fos));
                this.startActivityForResult(i, 7);


                //版本1
//                intent = new Intent();
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                //intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(filePath)));
//
//                startActivityForResult(intent, 0x3);
                break;

            case R.id.style_transfer:

//                Intent intent1 = new Intent(MainActivity.this, CameraImageActivity.class);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohui);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                final ImageTransfer imageTransfer = new ImageTransfer();
                System.out.println("风格为：" + styleID);
                Bitmap bitmap = imageTransfer.sendPostAndRecv(Photo.bitmap, styleID);
                ImageShow.setImageBitmap(bitmap);
                Photo.bitmap = null;
//                Resources resources = context.getResources();
//                Drawable drawable = resources.getDrawable(R.drawable.aaa);
//                Bitmap girlBytes= ImageConvert.drawableToBitmap(drawable);
//                ImageTransfer imageTransfer=new ImageTransfer();
//                Bitmap bitmap=imageTransfer.sendPostAndRecv(girlBytes);
//                ImageShow.setImageBitmap(bitmap);
                break;


            //选择风格
            case R.id.style_select:

                final ItemAdapter adapter = new ItemAdapter(this, iniDatas());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //设置标题
                builder.setTitle("请选择风格");
                //设置图标
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final ImageTransfer imageTransfer1 = new ImageTransfer();
                        styleID = Integer.toString(i);
                        Toast.makeText(getApplicationContext(), "你选择的是" + i, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create();
                builder.show();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Camera camera = Camera.open();


            Camera.Parameters params = camera.getParameters();
            params.setPictureSize(1024, 768);
            camera.setParameters(params);
            if (requestCode == 0x1) {
                if (data != null) {
                    Uri uri = data.getData();
                    getImg(uri);
                } else {
                    return;
                }
            }
            if (requestCode == 0x2) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    //得到图片
                    Bitmap bitmap = bundle.getParcelable("data");
                    //保存到图片到本地
                    saveImg(bitmap);
                    //设置图片
                    Photo.bitmap = bitmap;
                    ImageShow.setImageBitmap(bitmap);
                } else {
                    return;
                }
            }
            if (requestCode == 7) {
                File bb = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zhycheng.jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(bb.getPath());

//                Matrix matrix = new Matrix();
//                matrix.setScale(0.3f, 0.2f);
//                Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                        bitmap.getHeight(), matrix, true);
                Photo.bitmap = bitmap;
                //saveImg(bm);
//                Bitmap bb=data.getParcelableExtra("data");
                ImageShow.setImageBitmap(bitmap);


//            if (data != null) {
//
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
//                saveImg(bitmap);
//                Photo.bitmap=bitmap;
//                ImageShow.setImageBitmap(bitmap);
//            } else {
//                return;
//            }
            }
        }
    }


    //读取位图（图片）
    private Bitmap readImg() {
        File mfile = new File(filePath);
        Bitmap bm = null;
        if (mfile.exists()) {        //若该文件存在
            bm = BitmapFactory.decodeFile(filePath);
        }
        return bm;
    }

    //保存图片到本地，下次直接读取
    private void saveImg(Bitmap mBitmap) {
        File f = new File(filePath);
        try {
            //如果文件不存在，则创建文件
            if (!f.exists()) {
                f.createNewFile();
            }
            //输出流
            FileOutputStream out = new FileOutputStream(f);
            /** mBitmap.compress 压缩图片
             *
             *  Bitmap.CompressFormat.PNG   图片的格式
             *   100  图片的质量（0-100）
             *   out  文件输出流
             */
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, f.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getImg(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            //从输入流中解码位图
            // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //保存位图
            // img.setImageBitmap(bitmap);
            cutImg(uri);
            //关闭流
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //裁剪图片
    private void cutImg(Uri uri) {
        if (uri != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            //true:出现裁剪的框
            intent.putExtra("crop", "true");
            //裁剪宽高时的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //裁剪后的图片的大小
            intent.putExtra("outputX", 500);
            intent.putExtra("outputY", 500);
            intent.putExtra("return-data", true);  // 返回数据
            intent.putExtra("outputFormate", Bitmap.CompressFormat.PNG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, 0x2);
        } else {
            return;
        }
    }

}
