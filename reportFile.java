package com.example.pdfview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class reportFile extends AppCompatActivity {
    public static ArrayList<Bitmap> bitmapReportPic;
    public static ArrayList<String> reportPic;
    public static String[] comment;
    String reportName;
    String reporter;
    public static ArrayList<String> date;
    String now;
    String projectId;
    String userId;
    String companyId;
    String place;
    int num;
    String updateReportInfo = "http://10.20.170.52/sample/update_report_information.php";
    String updateReportName = "http://10.20.170.52/sample/update_report_name.php";

    public GradientDrawable drawable1, drawable2;
    EditText[] editComment;

    ScrollView main;
    LinearLayout ver, hor1, hor2, hor3;
    TextView t1, t2, t3, t4, t5, t6;
    TextView[] createDate, t7;
    ImageView[] imagePic;
    View space;
    View[] spaces;
    Button enter;
    LinearLayout[] hor4;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_report_file);

        //記載情報の取得
        //画像配列
        bitmapReportPic = new ArrayList<>();
        bitmapReportPic = CreateReportFile.bitmapReportArrayList;
        //写真名
        reportPic = new ArrayList<>();
        reportPic = getIntent().getStringArrayListExtra("picturesName");
        //写真の日付
        date = new ArrayList<>();
        date = getIntent().getStringArrayListExtra("date");
        //報告者
        reporter = getIntent().getStringExtra("reporter");
        //ユーザーID
        userId = MainActivity.userId;
        //プロジェクトID
        projectId = MyPage.projects_id;
        //会社ID
        companyId = MainActivity.companyId;
        //施工箇所名
        place = showPDF.title;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        //現在の日時を取得。
        Date time = new Date(System.currentTimeMillis());
        //取得した日時データを「yyyyMMddHHmmss」形式に整形した文字列を生成。
        now = dateFormat.format(time);
        //タイトル
        //reportName = now + "_" + place + "_" + reporter;
        reportName = now + "_" + place + "_" + showPDF.companyName;
        //レイアウト数の整理
        num = bitmapReportPic.size();
        //コメント数の定義
        comment = new String[num];
        editComment = new EditText[num];
        createDate = new TextView[num];
        imagePic = new ImageView[num];
        /*
        System.out.println(companyId);
        System.out.println(projectId);
        System.out.println(userId);
        System.out.println(reporter);
        System.out.println(now);
        System.out.println(reportPic);
        System.out.println(reportName);
        System.out.println(bitmapReportPic);
        System.out.println(reportName);
*/
        //背景の定義①
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
        //main.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        ver = new LinearLayout(this);
        ver.setOrientation(LinearLayout.VERTICAL);

        hor1 = new LinearLayout(this);
        hor1.setOrientation(LinearLayout.HORIZONTAL);

        hor2 = new LinearLayout(this);
        hor2.setOrientation(LinearLayout.HORIZONTAL);

        hor3 = new LinearLayout(this);
        hor3.setOrientation(LinearLayout.HORIZONTAL);

        hor4 = new LinearLayout[num];
        spaces = new View[num];
        //hor4.setOrientation(LinearLayout.HORIZONTAL);

        enter = new Button(this);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reports rp = new reports();
                rp.execute(updateReportInfo, updateReportName);
            }
        });
        enter.setGravity(Gravity.CENTER);
        enter.setText("登録");

        t1 = new TextView(this);
        t1.setText("施工箇所");
        t1.setGravity(Gravity.CENTER);
        t1.setBackground(drawable1);
        t1.setFocusable(true);
        t1.setFocusableInTouchMode(true);
        t1.setFocusedByDefault(true);

        t2 = new TextView(this);
        t2.setText(place);
        t2.setBackground(drawable2);

        t3 = new TextView(this);
        t3.setText("作成日");
        t3.setGravity(Gravity.CENTER);
        t3.setBackground(drawable1);

        t4 = new TextView(this);
        t4.setText(now);
        t4.setBackground(drawable2);

        t5 = new TextView(this);
        t5.setText("作成者");
        t5.setGravity(Gravity.CENTER);
        t5.setBackground(drawable1);

        t6 = new TextView(this);
        t6.setText(reporter);
        t6.setBackground(drawable2);

        t7 = new TextView[num];

        space = new View(this);
        space.setBackgroundColor(Color.LTGRAY);

        hor1.addView(t1, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));
        hor1.addView(t2, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3));

        hor2.addView(t3, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));
        hor2.addView(t4, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3));

        hor3.addView(t5, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1));
        hor3.addView(t6, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                3));

        ver.addView(hor1, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ver.addView(hor2, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ver.addView(hor3, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        ver.addView(space, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                30
        ));

        for(int j = 0; j < num; j++){
            imagePic[j] = new ImageView(reportFile.this);
            imagePic[j].setImageBitmap(bitmapReportPic.get(j));
            imagePic[j].setPadding(10,10,10,10);
            createDate[j] = new TextView(reportFile.this);
            createDate[j].setText(date.get(j));
            createDate[j].setBackground(drawable2);
            editComment[j] = new EditText(reportFile.this);
            editComment[j].setHint("コメント");
            t7[j] = new TextView(reportFile.this);
            t7[j].setGravity(Gravity.CENTER);
            t7[j].setText("撮影日時");
            t7[j].setBackground(drawable1);
            spaces[j] = new View(reportFile.this);
            spaces[j].setBackgroundColor(Color.LTGRAY);
            hor4[j] = new LinearLayout(reportFile.this);
            hor4[j].setOrientation(LinearLayout.HORIZONTAL);
            hor4[j].addView(t7[j], new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));
            hor4[j].addView(createDate[j], new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3
            ));
            ver.addView(hor4[j]);
            ver.addView(imagePic[j]);
            ver.addView(editComment[j]);
            if(j != num -1){
            ver.addView(spaces[j], new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    30
            ));
            }
        }

        ver.addView(enter, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        ver.setGravity(Gravity.CENTER);
        main.addView(ver);


    }

    private class reports extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params){
            String judge = "";
            String params0_url = params[0];
            String params1_url = params[1];
            HttpURLConnection con = null;
            //http接続のレスポンスデータとして取得するInputStreamオブジェクトを宣言（try外）
            InputStream is = null;
            //返却用の変数
            StringBuffer conResult;// = new StringBuffer();
            for(int i = 0; i < num; i++) {
                conResult = new StringBuffer();
                try {
                    //comment[i] = "test";
                    //String project_information = params[0];
                    //String dates = "2020-05-13";
                    URL url = new URL(params0_url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    //POSTデータの編集
                    comment[i] = editComment[i].getText().toString();
                    String post_data = URLEncoder.encode("reports_name", "UTF-8") + "=" + URLEncoder.encode(reportName, "UTF-8") + "&" +
                            URLEncoder.encode("pictures_id", "UTF-8") + "=" + URLEncoder.encode(reportPic.get(i), "UTF-8") + "&" +
                            URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comment[i], "UTF-8");
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
            }
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
                String post_data = URLEncoder.encode("reports_name", "UTF-8") + "=" + URLEncoder.encode(reportName, "UTF-8") + "&" +
                        URLEncoder.encode("users_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
                        URLEncoder.encode("projects_id", "UTF-8") + "=" + URLEncoder.encode(projectId, "UTF-8") + "&" +
                        URLEncoder.encode("reporter", "UTF-8") + "=" + URLEncoder.encode(reporter, "UTF-8") + "&" +
                        URLEncoder.encode("company_id", "UTF-8") + "=" + URLEncoder.encode(companyId, "UTF-8");
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

            return judge;
        }

        @Override
        public void onPostExecute(String string) {
            System.out.println("--------------");
            Toast.makeText(reportFile.this, "登録完了", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reportFile.this, showPDF.class);
            startActivity(intent);
            //finish();
        }


    }
}
