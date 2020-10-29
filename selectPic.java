package com.example.pdfview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.DirectoryAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class selectPic extends AppCompatActivity {

    //private GridView mGridView = null;
    //private Button mButton = null;

    public static String loginResult = "";
    //
    public static String getPicturesInfo = "http:/10.20.170.52/sample/get_point_pictures_information.php";
    public static String deletePicturesInfo = "http:/10.20.170.52/sample/delete_picture.php";

    public static ArrayList<String> picturesName;// = new ArrayList<String>();
    public static ArrayList<String> path;// = new ArrayList<String>();
    public static int picNum;
    public static ArrayList<String> deletePicturesName;

    public static ScrollView scrollView;
    public static LinearLayout linearLayout_main;

    public static GridView gridView;

    public static ArrayList<Bitmap> bitmapArrayList;
    public static ArrayList<Integer> selectFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);

        //scrollView = new ScrollView(this);
        //scrollView = findViewById(R.id.scrollView);
        //setContentView(scrollView);

        //pointId
        Intent intent1 = getIntent();
        String reportPointId = intent1.getStringExtra("pointID");
        //System.out.println(reportPointId);
        create cr = new create();
        cr.execute(getPicturesInfo, reportPointId);

        gridView = (GridView) findViewById(R.id.gridView1);
    }

    private class create extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public ArrayList<Bitmap> doInBackground(String... params) {
            //http接続を行うHttpURLConnectionオブジェクトを宣言
            //finallyで解放するためにtry外で宣言
            HttpURLConnection con = null;
            //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言（try外）
            InputStream is = null;
            //返却用の変数
            StringBuffer conResult = new StringBuffer();
            String sw = params[0];
            //String sw = "http:/10.20.170.52/sample/get_point_pictures_information.php";
            String checkResult = "";
            picturesName = new ArrayList<String>();
            path = new ArrayList<String>();
            switch (sw) {
                case "http:/10.20.170.52/sample/delete_picture.php":
                    String localHostUrl = "http://10.20.170.52/sample/delete_picture.php";
                    HttpURLConnection httpURLConnection = null;
                    for(int i = 0; i < deletePicturesName.size(); i++){
                        try {
                            String pictures_information = deletePicturesName.get(i);
                            //String pass_word = params[2];
                            URL url = new URL(localHostUrl);
                            httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            String post_data = URLEncoder.encode("pictures_name", "UTF-8") + "=" + URLEncoder.encode(pictures_information, "UTF-8");// + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
                            bufferedWriter.write(post_data);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                loginResult += line;
                            }
                            bufferedReader.close();
                            inputStream.close();
                            httpURLConnection.disconnect();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(loginResult);

                    }
                    //String test = "test";
                    return null;

                case "http:/10.20.170.52/sample/get_point_pictures_information.php":
                    try {
                        String report_place_id = params[1];
                        URL url = new URL(sw);
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        OutputStream outputStream = con.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        //POSTデータの編集
                        String post_data = URLEncoder.encode("report_place_id", "UTF-8") + "=" + URLEncoder.encode(report_place_id, "UTF-8");// + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
                        System.out.println(post_data);
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = con.getInputStream();
                        String encoding = con.getContentEncoding();
                        if (null == encoding) {
                            encoding = "UTF-8";
                        }
                        InputStreamReader inReader = new InputStreamReader(inputStream, encoding);
                        BufferedReader bufferedReader = new BufferedReader(inReader);
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            conResult.append(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        inputStream.close();
                        con.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("con" + conResult);
            }


            //事前情報の取得
            String regex_pictures_Name = "\"pictures_name\":.+?\",";
            //String regex_date = "\"date\":.+?\",";
            //String regex_recorder = "\"recorder\":.+?\",";
            //String regex_fileName = "\"fileName\":.+?\",";
            String regex_path = "\"path\":.+?\",";
            //String regex_comments = "\"comments\":.+?]";
            Pattern p_projectName = Pattern.compile(regex_pictures_Name);
            checkPicturesName(p_projectName, conResult.toString());
            System.out.println(picturesName);
            Pattern p_path = Pattern.compile(regex_path);
            checkPath(p_path, conResult.toString());
            System.out.println(path);

            //画像の抽出
            //ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
            bitmapArrayList = new ArrayList<>();
            selectFlag = new ArrayList<>();
            Bitmap bmp = null;

            //System.out.println("getImage" + address);
            int i = 0;
            picNum = picturesName.size();
            while (i < picturesName.size()) {
                selectFlag.add(0);
                String address = path.get(i);
                System.out.println(address);
                HttpURLConnection urlConnection = null;

                try {
                    URL url = new URL(address);
                    // HttpURLConnection インスタンス生成
                    urlConnection = (HttpURLConnection) url.openConnection();
                    // タイムアウト設定
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(20000);
                    // リクエストメソッド
                    urlConnection.setRequestMethod("GET");
                    // リダイレクトを自動で許可しない設定
                    urlConnection.setInstanceFollowRedirects(false);
                    // 接続
                    urlConnection.connect();
                    int resp = urlConnection.getResponseCode();
                    switch (resp) {
                        case HttpURLConnection.HTTP_OK:
                            try (InputStream ips = urlConnection.getInputStream()) {
                                bmp = BitmapFactory.decodeStream(ips);
                                bitmapArrayList.add(bmp);
                                ips.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    Log.d("debug", "downloadImage error");
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                i++;
            }
            return bitmapArrayList;
        }

        @Override
        public void onPostExecute(final ArrayList<Bitmap> bitmapArrayList) {
            if(bitmapArrayList != null){
                System.out.println(bitmapArrayList);

           /* ImageView imageView = findViewById(R.id.imageview);
            imageView.setImageBitmap(bitmapArrayList.get(0));*/
                gridView.setAdapter(new ImageAdapter(selectPic.this));

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        final View v = view;
                        final int pos = position;
                        ImageView bitImage = new ImageView(selectPic.this);
                        bitImage.setImageBitmap(bitmapArrayList.get(position));
                        AlertDialog.Builder builder = new AlertDialog.Builder(selectPic.this);
                        builder.setView(bitImage)
                                //.setMessage("ユーザー名、パスワード、権限を入力してください")
                                //.setView(layout)
                                .setPositiveButton("選択", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(selectFlag.get(pos) == 1){
                                            v.setBackgroundColor(Color.WHITE);
                                            selectFlag.set(pos, 0);
                                            System.out.println(selectFlag.get(pos));
                                        }

                                        else{
                                            v.setBackgroundColor(R.drawable.border_bisque);
                                            selectFlag.set(pos, 1);
                                            System.out.println(selectFlag.get(pos));
                                        }
                                    }
                                })
                                .setNegativeButton("戻る", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                        //Toast.makeText(selectPic.this, "" + position, Toast.LENGTH_LONG).show();
                    }
                });

                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if(selectFlag.get(position) == 1){
                            view.setBackgroundColor(Color.WHITE);
                            selectFlag.set(position, 0);
                            System.out.println(selectFlag.get(position));
                        }

                        else{
                            view.setBackgroundColor(R.drawable.border_bisque);
                            selectFlag.set(position, 1);
                            System.out.println(selectFlag.get(position));
                        }
                        return true;
                    }
                });

                System.out.println(selectFlag);
                System.out.println(picturesName);

            }else{
                finish();
                startActivity(getIntent());
            }
            }

    }


        private static void checkPicturesName(Pattern p, String target) {
            Matcher m = p.matcher(target);
            while (m.find()) {
                String pName = m.group();
                picturesName.add(pName.substring(18, pName.length() - 2));
                System.out.println(m.group());
            }
        }

        private static void checkPath(Pattern p, String target) {
            Matcher m = p.matcher(target);
            while (m.find()) {
                String pName = m.group();
                path.add(pName.substring(9, pName.length() - 2));
                System.out.println(m.group());
            }
        }

    public void deleteClick(View view){
        deletePicturesName = new ArrayList<>();
        String projects_name = "sample1";
        String delete = "1598935107684.jpg";
        for(int i = 0; i < selectFlag.size(); i++){
            if (selectFlag.get(i) == 1){
                deletePicturesName.add(String.valueOf(picturesName.get(i)));
            }
        }
        create cr = new create();
        cr.execute(deletePicturesInfo);

        System.out.println(deletePicturesName);

    }
    }
