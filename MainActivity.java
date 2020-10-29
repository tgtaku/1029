package com.example.pdfview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static String loginResult = "";
    EditText UserIdEt, PasswordEt, HostEt;
    public static String host;
    public static String userId;
    public static String companyId;
    public static String usersId;
    public static String flag;
    public static String usersName;
    public static ArrayList<String> projectsNum;
    String regex_users_id = "\"id\":.+?\",";
    String regex_users_name = "\"users_name\":.+?\",";
    String regex_projects_id = "\"projects_id\":.+?\",";
    String regex_companies_id = "\"companies_id\":.+?\",";
    String regex_flag = "\"flag\":.+?\",";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HostEt = (EditText)findViewById(R.id.editHost);
        UserIdEt = (EditText)findViewById(R.id.editUserName);
        PasswordEt = (EditText)findViewById(R.id.editPass);
    }
    public void loginClick(View view){
        userId = UserIdEt.getText().toString();
        String password = PasswordEt.getText().toString();
        host = HostEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(host, userId, password);

    }


    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        Context context;
        AlertDialog alertDialog;

        BackgroundWorker(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            if (params[0] == "sample1") {
                loginResult = "";
                //String localHostUrl = "http://10.20.170.52/sample/EX_upload.php";
                String localHostUrl = "http://10.20.170.52/sample/delete_picture.php";
                HttpURLConnection httpURLConnection = null;
                try {
                    String pictures_information = params[1];
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
                String test = "test";
                return test;

            } else {
                //発注元ID確認
                loginResult = "";
                String localHostUrl = "http://10.20.170.52/preLogin.php";
                HttpURLConnection httpURLConnection = null;
                try {
                    String user_name = params[1];
                    String pass_word = params[2];
                    URL url = new URL(localHostUrl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("owner", "UTF-8") + "=" + URLEncoder.encode(host, "UTF-8");
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

                if (!loginResult.equals("")) {
                    //企業IDを取得
                    localHostUrl = "http://10.20.170.52/sample/get_company_id.php";
                    httpURLConnection = null;
                    StringBuffer _conResult = new StringBuffer();

                    try {
                        System.out.println("usersID.php");
                        URL url = new URL(localHostUrl);
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        //POSTデータの編集
                        String post_data = URLEncoder.encode("name", "UTF-8")
                                + "=" + URLEncoder.encode(userId, "UTF-8");
                        System.out.println(post_data);
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        String encoding = httpURLConnection.getContentEncoding();
                        if (null == encoding) {
                            encoding = "UTF-8";
                        }
                        InputStreamReader inReader = new InputStreamReader(inputStream, encoding);
                        BufferedReader bufferedReader = new BufferedReader(inReader);
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            _conResult.append(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //JSONからデータの取得
                    System.out.println(_conResult.toString());
                    Pattern p_usersId = Pattern.compile(regex_users_id);
                    checkUsersId(p_usersId, _conResult.toString());
                    System.out.println(usersId);
                    Pattern p_companyId = Pattern.compile(regex_companies_id);
                    checkCompanyId(p_companyId, _conResult.toString());
                    System.out.println(companyId);
                    Pattern p_flag = Pattern.compile(regex_flag);
                    checkFlag(p_flag, _conResult.toString());
                    System.out.println(flag);
                    Pattern p_users_name = Pattern.compile(regex_users_name);
                    checkUsersName(p_users_name, _conResult.toString());
                    System.out.println(flag);


                    //companyIdから参加プロジェクトを取得
                    localHostUrl = "http://10.20.170.52/sample/get_assign_projects_id.php";
                    httpURLConnection = null;
                    _conResult = new StringBuffer();

                    try {
                        System.out.println("projectsID.php");
                        URL url = new URL(localHostUrl);
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        //POSTデータの編集
                        String post_data = URLEncoder.encode("companies_id", "UTF-8")
                                + "=" + URLEncoder.encode(companyId, "UTF-8")
                            /*+ "&" + URLEncoder.encode("users_id", "UTF-8")
                            + "=" + URLEncoder.encode(String.valueOf(usersId), "UTF-8")*/;

                        System.out.println(post_data);
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        String encoding = httpURLConnection.getContentEncoding();
                        if (null == encoding) {
                            encoding = "UTF-8";
                        }
                        InputStreamReader inReader = new InputStreamReader(inputStream, encoding);
                        BufferedReader bufferedReader = new BufferedReader(inReader);
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            _conResult.append(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //JSONからデータの取得
                    System.out.println(_conResult.toString());
                    Pattern p_projectsId = Pattern.compile(regex_projects_id);
                    checkProjectsId(p_projectsId, _conResult.toString());
                    System.out.println(projectsNum);


                    //ログイン処理
                    loginResult = "";
                    localHostUrl = "http://10.20.170.52/login.php";
                    httpURLConnection = null;
                    try {
                        String user_name = params[1];
                        String pass_word = params[2];
                        URL url = new URL(localHostUrl);
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8");
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
                    //return loginResult;
                }

            }
            return loginResult;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            //alertDialog.setMessage("result");
            //alertDialog.show();
            if(result.equals("login success!")) {
                if(flag.equals("1")){
                    Intent intent = new Intent(MainActivity.this, MyPage.class);
                    startActivity(intent);
                }else if(flag.equals("0")){
                    Intent intent = new Intent(MainActivity.this, MyPage_User.class);
                    startActivity(intent);
                }

            }else{

            }
            result = "";
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        LinearLayout r = findViewById(R.id.test1);
        Point view = getViewSize(r);

        StringBuilder text = new StringBuilder();
        text.append("ViewSize → X:" + view.x + " Y:" + view.y);
        //text.append("\nDisplaySize → X:" + disp.x + " Y:" + disp.y);
        //text.append("\nHardwareSize → X:" + real.x + " Y:" + real.y);
        System.out.println(text);

        //dipSize.setText(text.toString());

    }
    public static Point getViewSize(View View){
        Point point = new Point(0, 0);
        point.set(View.getWidth(), View.getHeight());

        return point;
    }


    public void testClick(View view){
        String projects_name = "sample1";
        String delete = "1598935107684.jpg";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(projects_name, delete);

    }


    private static void checkUsersId(Pattern p, String target){
        Matcher m = p.matcher(target);
        while(m.find()){
            String pName = m.group();
            usersId = (pName.substring(7, pName.length() - 2));
            System.out.println(m.group());
        }
    }

    private static void checkCompanyId(Pattern p, String target){
        Matcher m = p.matcher(target);
        while(m.find()){
            String pName = m.group();
            companyId = (pName.substring(17, pName.length() - 2));
            System.out.println(m.group());
        }
    }

    private static void checkProjectsId(Pattern p, String target){
        Matcher m = p.matcher(target);
        while(m.find()){
            String pName = m.group();
            projectsNum = new ArrayList<String>();
            projectsNum.add(pName.substring(16, pName.length() - 2));
            System.out.println(m.group());
        }
    }
    private static void checkFlag(Pattern p, String target){
        Matcher m = p.matcher(target);
        while(m.find()){
            String pName = m.group();
            flag = (pName.substring(9, pName.length() - 2));
            System.out.println(m.group());
        }
    }
    private static void checkUsersName(Pattern p, String target){
        Matcher m = p.matcher(target);
        while(m.find()){
            String pName = m.group();
            usersName = (pName.substring(15, pName.length() - 2));
            System.out.println(m.group());
        }
    }

}


