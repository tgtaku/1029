package com.example.pdfview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class showCreatedReport extends AppCompatActivity {

    //php宛先
    //レポート名から取得
    String fromReportName = "http://10.20.170.52/sample/rep_reportName.php";
    //プロジェクト情報から取得
    String fromProjectId = "http://10.20.170.52/sample/rep_projectId.php";
    //会社IDから取得
    String fromCompanyId = "http://10.20.170.52/sample/rep_company.php";
    //報告書名から写真を取得
    String fromReportNameForPic = "http://10.20.170.52/sample/rep_reportName_pic.php";
    //写真名から日付と場所IDを取得
    String fromPictureName = "http://10.20.170.52/sample/rep_pictureName.php";
    //施工場所IDから施工場所名を取得
    String fromPlaceId = "http://10.20.170.52/sample/rep_placeId.php";

    //画面情報
    ScrollView main;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24;
    LinearLayout ver, h1,h2,h3,h4,h5,h6,h7,h8;
    View view1, view2;
    LinearLayout[] hor1, hor2,hor3;
    TextView[] textView1,textView2,textView3,textView4,textView5,textView6;
    ImageView[] imageView;
    View[] view;


    //現場情報
    static String g_id = MyPage.projects_id;
    static String g_name;
    static String g_address;
    static String g_overview;

    //施工者情報
    static String u_id = MainActivity.companyId;
    static String u_name;
    static String u_address;
    static String u_tel;
    static String u_reporter;

    //施工状況
    static ArrayList<String> c_id;
    static ArrayList<String> c_name;
    static ArrayList<String> c_date; //= new ArrayList<>();
    static ArrayList<String> c_pic_name;
    static ArrayList<String> c_comment;
    static ArrayList<Bitmap> c_pic;

    //報告書名
    String selectedReport;

    String regex_reporter = "\"reporter\":.+?\",";

    String regex_projects_name = "\"projects_name\":.+?\",";
    String regex_projects_street_address = "\"projects_street_address\":.+?\",";
    String regex_overview = "\"overview\":.+?\",";
    String regex_company_name = "\"companies_name\":.+?\",";
    String regex_street_address = "\"street_address\":.+?\",";
    String regex_tel = "\"tel\":.+?\",";
    String regex_pictures_name = "\"pictures_id\":.+?\",";
    String regex_comment = "\"comment\":.+?\",";
    String regex_place_id = "\"report_place_id\":.+?\",";
    String regex_date = "\"date\":.+?\",";
    String regex_place = "\"report_place_name\":.+?\",";

    public GradientDrawable drawable0, drawable1, drawable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_created_report);
        selectedReport = getIntent().getStringExtra("selectedReport");
        System.out.println(selectedReport);

        //背景の定義①
        drawable0 = new GradientDrawable();
        drawable0.setStroke(3, Color.parseColor("#000000"));
        drawable0.setCornerRadius(3);
        drawable0.setColor(Color.parseColor("#FFCC99"));


        drawable1 = new GradientDrawable();
        drawable1.setStroke(3, Color.parseColor("#000000"));
        drawable1.setCornerRadius(3);
        drawable1.setColor(Color.parseColor("#FFE4C4"));

        //背景の定義②
        drawable2 = new GradientDrawable();
        drawable2.setStroke(3, Color.parseColor("#000000"));
        drawable2.setCornerRadius(3);

        main = new ScrollView(this);
        setContentView(main);



        showingCreatedReport sh = new showingCreatedReport();
        sh.execute(fromReportName, fromProjectId, fromCompanyId, fromReportNameForPic, fromPictureName, fromPlaceId);
    }

    private class showingCreatedReport extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public String doInBackground(String... params){
            String params0_url = params[0];
            String params1_url = params[1];
            String params2_url = params[2];
            String params3_url = params[3];
            String params4_url = params[4];
            String params5_url = params[5];

            HttpURLConnection con = null;
            //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言（try外）
            InputStream is = null;
            //返却用の変数
            StringBuffer conResult;// = new StringBuffer();
            //レポート名からプロジェクトID、報告者、会社IDの取得
                conResult = new StringBuffer();
                try {
                    URL url = new URL(params0_url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("reports_name", "UTF-8") + "=" + URLEncoder.encode(selectedReport, "UTF-8");
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
                    System.out.println(conResult.toString());
                    bufferedReader.close();
                    inputStream.close();
                    con.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //結果の取得
            Pattern p_reporter= Pattern.compile(regex_reporter);
            checkReporter(p_reporter, conResult.toString());
            System.out.println(u_reporter);

            //プロジェクトIDから現場情報の取得
            conResult = new StringBuffer();
            try {
                URL url = new URL(params1_url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //POSTデータの編集
                //comment[i] = editComment[i].getText().toString();
                String post_data = URLEncoder.encode("projects_id", "UTF-8") + "=" + URLEncoder.encode(g_id, "UTF-8");
                        //URLEncoder.encode("users_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
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
                System.out.println(conResult.toString());
                bufferedReader.close();
                inputStream.close();
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //結果の取得
            Pattern p_projectName= Pattern.compile(regex_projects_name);
            checkRProjectsName(p_projectName, conResult.toString());
            System.out.println(g_name);
            Pattern p_projectsAddress= Pattern.compile(regex_projects_street_address);
            checkProjectsAddress(p_projectsAddress, conResult.toString());
            System.out.println(g_address);
            Pattern p_overview= Pattern.compile(regex_overview);
            checkOverview(p_overview, conResult.toString());
            System.out.println(g_overview);

            //会社IDから施工会社情報の取得
            conResult = new StringBuffer();
            try {
                URL url = new URL(params2_url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                //POSTデータの編集
                //comment[i] = editComment[i].getText().toString();
                String post_data = URLEncoder.encode("company_id", "UTF-8") + "=" + URLEncoder.encode(u_id, "UTF-8");
                //URLEncoder.encode("users_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
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
                System.out.println(conResult.toString());
                bufferedReader.close();
                inputStream.close();
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pattern p_companyName= Pattern.compile(regex_company_name);
            checkCompanyName(p_companyName, conResult.toString());
            System.out.println(u_name);
            Pattern p_companyAddress= Pattern.compile(regex_street_address);
            checkCompanyAddress(p_companyAddress, conResult.toString());
            System.out.println(u_address);
            Pattern p_tel= Pattern.compile(regex_tel);
            checkTel(p_tel, conResult.toString());
            System.out.println(u_tel);

            //レポート名から写真名、コメントの取得
            conResult = new StringBuffer();
            try {
                URL url = new URL(params3_url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                OutputStream outputStream = con.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("reports_name", "UTF-8") + "=" + URLEncoder.encode(selectedReport, "UTF-8");
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
                System.out.println(conResult.toString());
                bufferedReader.close();
                inputStream.close();
                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //結果の取得
            c_comment = new ArrayList<>();
            c_pic_name = new ArrayList<>();
            Pattern p_pictures_id= Pattern.compile(regex_pictures_name);
            checkPicturesName(p_pictures_id, conResult.toString());
            System.out.println(c_pic_name);
            Pattern p_comment= Pattern.compile(regex_comment);
            checkComment(p_comment, conResult.toString());
            System.out.println(c_comment);

            //画像bitmapの取得
            c_pic = new ArrayList<>();
            int i = 0;
            Bitmap bmp = null;
            while (i < c_pic_name.size()) {
                //selectFlag.add(0);
                String address = "http://10.20.170.52/sample/images/" + c_pic_name.get(i);
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
                                c_pic.add(bmp);
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
            System.out.println(c_pic);

            //画像名から施工場所ID、撮影日を取得
            c_date = new ArrayList<>();
            c_id = new ArrayList<>();
            for(int k = 0; k < c_pic_name.size(); k++){
                conResult = new StringBuffer();
                try {
                    URL url = new URL(params4_url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("pictures_name", "UTF-8") + "=" + URLEncoder.encode(c_pic_name.get(k), "UTF-8");
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
                    System.out.println(conResult.toString());
                    bufferedReader.close();
                    inputStream.close();
                    con.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Pattern p_place_id= Pattern.compile(regex_place_id);
                checkPlaceId(p_place_id, conResult.toString());
                System.out.println(c_id);
                Pattern p_date= Pattern.compile(regex_date);
                checkDate(p_date, conResult.toString());
                System.out.println(c_date);
            }

            //施工場所IDから施工場所名を取得
            c_name = new ArrayList<>();
            for(int k = 0; k < c_id.size(); k++){
                conResult = new StringBuffer();
                try {
                    URL url = new URL(params5_url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("report_places_id", "UTF-8") + "=" + URLEncoder.encode(c_id.get(k), "UTF-8");
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
                    System.out.println(conResult.toString());
                    bufferedReader.close();
                    inputStream.close();
                    con.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Pattern p_place= Pattern.compile(regex_place);
                checkPlace(p_place, conResult.toString());
                System.out.println(c_name);
            }

            return null;
        }

        @Override
        public void onPostExecute(String string){
            ver = new LinearLayout(showCreatedReport.this);
            ver.setGravity(Gravity.CENTER);
            ver.setOrientation(LinearLayout.VERTICAL);

            t1 = new TextView(showCreatedReport.this);
            t1.setText("現場情報");
            t1.setBackground(drawable0);
            t1.setGravity(Gravity.CENTER);
            ver.addView(t1, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            h1 = new LinearLayout(showCreatedReport.this);
            h1.setOrientation(LinearLayout.HORIZONTAL);
            t2 = new TextView(showCreatedReport.this);
            t2.setText("現場名");
            t2.setBackground(drawable1);
            t2.setGravity(Gravity.CENTER);
            t3 = new TextView(showCreatedReport.this);
            t3.setText(g_name);
            t3.setBackground(drawable2);
            h1.addView(t2, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h1.addView(t3, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            h2 = new LinearLayout(showCreatedReport.this);
            h2.setOrientation(LinearLayout.HORIZONTAL);
            t4 = new TextView(showCreatedReport.this);
            t4.setText("所在地");
            t4.setBackground(drawable1);
            t4.setGravity(Gravity.CENTER);
            t5 = new TextView(showCreatedReport.this);
            t5.setText(g_address);
            t5.setBackground(drawable2);
            h2.addView(t4, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h2.addView(t5, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            h3 = new LinearLayout(showCreatedReport.this);
            h3.setOrientation(LinearLayout.HORIZONTAL);
            t6 = new TextView(showCreatedReport.this);
            t6.setText("概要");
            t6.setBackground(drawable1);
            t6.setGravity(Gravity.CENTER);
            t7 = new TextView(showCreatedReport.this);
            t7.setText(g_overview);
            t7.setBackground(drawable2);
            h3.addView(t6, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h3.addView(t7, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            view1 = new View(showCreatedReport.this);
            view1.setBackgroundColor(Color.LTGRAY);
            view2 = new View(showCreatedReport.this);
            view2.setBackgroundColor(Color.LTGRAY);

            ver.addView(h1);
            ver.addView(h2);
            ver.addView(h3);
            ver.addView(view1, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    30
            ));

            t8 = new TextView(showCreatedReport.this);
            t8.setText("施工者情報");
            t8.setBackground(drawable0);
            t8.setGravity(Gravity.CENTER);
            ver.addView(t8, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            h4 = new LinearLayout(showCreatedReport.this);
            h4.setOrientation(LinearLayout.HORIZONTAL);
            t9 = new TextView(showCreatedReport.this);
            t9.setText("会社名");
            t9.setBackground(drawable1);
            t9.setGravity(Gravity.CENTER);
            t10 = new TextView(showCreatedReport.this);
            t10.setText(u_name);
            t10.setBackground(drawable2);
            h4.addView(t9, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h4.addView(t10, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            h5 = new LinearLayout(showCreatedReport.this);
            h5.setOrientation(LinearLayout.HORIZONTAL);
            t11 = new TextView(showCreatedReport.this);
            t11.setText("住所");
            t11.setBackground(drawable1);
            t11.setGravity(Gravity.CENTER);
            t12 = new TextView(showCreatedReport.this);
            t12.setText(u_address);
            t12.setBackground(drawable2);
            h5.addView(t11, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h5.addView(t12, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            h7 = new LinearLayout(showCreatedReport.this);
            h7.setOrientation(LinearLayout.HORIZONTAL);
            t15 = new TextView(showCreatedReport.this);
            t15.setText("報告者");
            t15.setBackground(drawable1);
            t15.setGravity(Gravity.CENTER);
            t16 = new TextView(showCreatedReport.this);
            t16.setText(u_reporter);
            t16.setBackground(drawable2);
            h7.addView(t15, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h7.addView(t16, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            h6 = new LinearLayout(showCreatedReport.this);
            h6.setOrientation(LinearLayout.HORIZONTAL);
            t13 = new TextView(showCreatedReport.this);
            t13.setText("TEL");
            t13.setBackground(drawable1);
            t13.setGravity(Gravity.CENTER);
            t14 = new TextView(showCreatedReport.this);
            t14.setText(u_tel);
            t14.setBackground(drawable2);
            h6.addView(t13, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            h6.addView(t14, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));

            t17 = new TextView(showCreatedReport.this);
            //t17.setText("施工状況");
            String text = "施工箇所" + c_name.get(0);
            t17.setText(text);
            t17.setBackground(drawable0);
            t17.setGravity(Gravity.CENTER);

            h8 = new LinearLayout(showCreatedReport.this);
            h8.setOrientation(LinearLayout.HORIZONTAL);
            t18 = new TextView(showCreatedReport.this);
            t18.setText("施工箇所");
                t18.setBackground(drawable1);
                t18.setGravity(Gravity.CENTER);
            t19 = new TextView(showCreatedReport.this);
                t19.setText(c_name.get(0));
                t19.setBackground(drawable2);
                h8.addView(t18,new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                h8.addView(t19,new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3));

            ver.addView(h4);
            ver.addView(h5);
            ver.addView(h6);
            ver.addView(h7);

            ver.addView(view2, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    30
            ));

            ver.addView(t17, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            //ver.addView(h8);

            int num = c_id.size();
            hor1 = new LinearLayout[num];
            hor2 = new LinearLayout[num];
            hor3 = new LinearLayout[num];
            textView1 = new TextView[num];
            textView2 = new TextView[num];
            textView3 = new TextView[num];
            textView4 = new TextView[num];
            textView5 = new TextView[num];
            textView6 = new TextView[num];
            view = new View[num];
            imageView = new ImageView[num];
            for(int j = 0; j < num; j++){
                hor1[j] = new LinearLayout(showCreatedReport.this);
                hor1[j].setOrientation(LinearLayout.HORIZONTAL);
                hor2[j] = new LinearLayout(showCreatedReport.this);
                hor2[j].setOrientation(LinearLayout.HORIZONTAL);
                hor3[j] = new LinearLayout(showCreatedReport.this);
                hor3[j].setOrientation(LinearLayout.HORIZONTAL);
                textView1[j] = new TextView(showCreatedReport.this);
                textView2[j] = new TextView(showCreatedReport.this);
                textView3[j] = new TextView(showCreatedReport.this);
                textView4[j] = new TextView(showCreatedReport.this);
                textView5[j] = new TextView(showCreatedReport.this);
                textView6[j] = new TextView(showCreatedReport.this);
                view[j] = new View(showCreatedReport.this);
                imageView[j] = new ImageView(showCreatedReport.this);

                /*textView1[j].setText("施工箇所");
                textView1[j].setBackground(drawable1);
                textView1[j].setGravity(Gravity.CENTER);
                textView2[j].setText(c_name.get(j));
                textView2[j].setBackground(drawable2);
                hor1[j].addView(textView1[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                hor1[j].addView(textView2[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3));*/

                textView3[j].setText("撮影日時");
                textView3[j].setBackground(drawable1);
                textView3[j].setGravity(Gravity.CENTER);
                textView4[j].setText(c_date.get(j));
                textView4[j].setBackground(drawable2);
                hor2[j].addView(textView3[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                hor2[j].addView(textView4[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3));

                textView5[j].setText("コメント");
                textView5[j].setBackground(drawable1);
                textView5[j].setGravity(Gravity.CENTER);
                textView6[j].setText(c_comment.get(j));
                textView6[j].setBackground(drawable2);
                hor3[j].addView(textView5[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));
                hor3[j].addView(textView6[j],new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        3));

                imageView[j].setImageBitmap(c_pic.get(j));
                imageView[j].setPadding(10,10,10,10);

                //ver.addView(hor1[j]);
                ver.addView(hor2[j]);
                ver.addView(hor3[j]);
                ver.addView(imageView[j]);

                if(num-1 != j){
                    view[j].setBackgroundColor(Color.LTGRAY);
                    ver.addView(view[j], new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            30
                    ));
                }





            }

            main.addView(ver);

        }

    }

    private static void checkReporter(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            u_reporter = m.group().substring(13, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }

    private static void checkRProjectsName(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            g_name = m.group().substring(18, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkProjectsAddress(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            g_address = m.group().substring(28, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkOverview(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            g_overview = m.group().substring(13, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkCompanyName(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            u_name = m.group().substring(19, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkCompanyAddress(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            u_address = m.group().substring(19, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkTel(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            u_tel = m.group().substring(8, m.group().length() - 2);
            //System.out.println(m.group());
        }
    }
    private static void checkComment(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            c_comment.add(m.group().substring(12, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }
    private static void checkPicturesName(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            c_pic_name.add(m.group().substring(16, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }
    private static void checkPlaceId(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            c_id.add(m.group().substring(20, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }
    private static void checkDate(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            c_date.add(m.group().substring(9, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }
    private static void checkPlace(Pattern p, String target) {
        Matcher m = p.matcher(target);
        while (m.find()) {
            c_name.add(m.group().substring(22, m.group().length() - 2));
            //System.out.println(m.group());
        }
    }
}
