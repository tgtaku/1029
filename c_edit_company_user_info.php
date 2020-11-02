<?php
if(isset($_GET['com'])){
  $name = $_GET['com'];
  $add = $_GET['add'];
  $tel = $_GET['tel'];
//会社IDを取得
require "conn.php";
$mysql_qry = "select * from companies_information_1 where companies_name = '$name' AND street_address = '$add' AND tel = '$tel';";
$result = mysqli_query($conn, $mysql_qry);
  if(mysqli_num_rows($result) > 0){
      $i = 0;
      while($row = mysqli_fetch_assoc($result)){
          $companies_id = $row['companies_id'];
          //$flag = $row['flag'];
          $i++;
      }
      session_start();
      $_SESSION['company_id'] = $companies_id;
      //$_SESSION['flag'] = $flag;
  }

//会社IDからユーザ情報を取得
$users_id = [];
$users_name = [];
$user_pass = [];
$company_id = $_SESSION['company_id'];
require "conn.php";
$mysql_qry = "select * from users_information_1 where companies_id = '$company_id';";
$result = mysqli_query($conn, $mysql_qry);
  if(mysqli_num_rows($result) > 0){
      $i = 0;
      while($row = mysqli_fetch_assoc($result)){
          array_push($users_id,$row['users_id']);
          array_push($users_name,$row['users_name']);
          array_push($user_pass,$row['password']);
          $i++;
      }
  }
    $json_array_users_id = json_encode($users_id);
    $json_array_users_name = json_encode($users_name);
    $json_array_user_pass = json_encode($user_pass);
  //print_r($users_name);
}
if(isset($_POST['change'])){
  session_start();
  $companies_id = $_SESSION['company_id'];
  $p_com = $_POST["company_name"];
  $p_add = $_POST["address"];
  $p_tel = $_POST["tel"];
  //print_r($p_com);
  //print_r($p_add);
  //print_r($p_tel);
  require "conn.php";
  $mysql_qry = "UPDATE companies_information_1 SET companies_name='$p_com', street_address='$p_add', tel='$p_tel' WHERE companies_id = '$companies_id' ;";
  $result = mysqli_query($conn, $mysql_qry);
  echo "<script type='text/javascript'>window.close();</script>";
}
?>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ユーザー情報編集</title>
</head>
<body>
<h2>削除するユーザを選択、登録するユーザ情報を入力してください。</h2>

<h4><?php echo $name; ?></h4>
<form id="user_form" method = "post">
    <table id = "user_info" name = "table_com">
                <tr>
                    <th style="WIDTH: 50px" id="project">No</th>
                    <th style="WIDTH: 200px" id="user_id">ユーザID</th>
                    <th style="WIDTH: 200px" id="user_name">ユーザ名</th>
                    <th style="WIDTH: 200px" id="password">パスワード</th>
                    <th style="WIDTH: 20px" id="check"></th>
                    <th style="WIDTH: 100px" id="edit"></th>
                </tr>
            </table>
            <!--<input type = "button" id = "pro_button" name="editpro" value = "現場編集" onclick="editpro()">-->
    </form>

<form method="post" id = "new">
    <h4>ユーザ登録欄</h4>
    <h6>No.1</h6>
  ユーザID<input type = "text" id = "in_user_id_1" name = "user_id" value = ""><br />
  ユーザ名<input type = "text" id = "in_user_name_1" name = "user_name" value = ""><br />
  パスワード<input type ="password" id = "in_password_1" name="password" value = ""><br />
  パスワード（再入力）<input type = "password" id = "re_password_1" name="re_password" value = ""><br />
  </form>
  <p><input type="button" name="change" value="ユーザ入力欄追加" onclick = add() /></p>
  <p><input type="button" name="change" value="追加/削除" onclick = change() /></p>

<script>
var i = 2;
    if(<?php echo $json_array_users_name; ?> != ""){
        //テーブル表示
        //phpから配列の取得
        var array_user_id = <?php echo $json_array_users_id; ?>;
        var array_user_name = <?php echo $json_array_users_name; ?>;
        var array_pass = <?php echo $json_array_user_pass; ?>;
        
        //テーブル情報
        var table = document.getElementById("user_info");
        var tableLength = array_user_name.length;
        var cell1 = [];
        var cell2 = [];
        var cell3 = [];
        var cell4 = [];
        var cell5 = [];
        var cell6 = [];

            //会社名
            for(var j = 0; j < tableLength; j++){
                var row = table.insertRow(-1);
                cell1.push(row.insertCell(-1));
                cell2.push(row.insertCell(-1));
                cell3.push(row.insertCell(-1));
                cell4.push(row.insertCell(-1));
                cell5.push(row.insertCell(-1));
                cell6.push(row.insertCell(-1));
                cell1[j].innerHTML = table.rows.length-1;
                cell2[j].innerHTML = array_user_id[j];
                cell2[j].id = "user";
                cell3[j].innerHTML = array_user_name[j];
                cell4[j].innerHTML = array_pass[j];
                cell4[j].value = "pass";
                cell5[j].innerHTML = '<input type = "checkbox" name = "ch"/>';;
                cell5[j].value = "check";
                cell6[j].innerHTML = '<input type = "button" value = "ユーザー情報編集" onclick="edit_user_info(this)"/>';
                
        }
        }
</script>
<script>
    var parent = document.getElementById("new");

    function change(){
      //登録用の配列
      let p_user_id = [];
      let p_user_name = [];
      let p_password = [];
      //入力欄の内容を取得
      //console.log(parent.children.length);
      //postするかしないかのフラグ
      var post_flag = 0;
      //繰り返し回数の決定
      var num = 2;
      if(parent.children.length == 10){
        
      }else{
        var pre_num = (parent.children.length - 10) / 9;
        num = num + pre_num;
      }
      //console.log(num);
      for(var cnt = 1; cnt < num; cnt++){
        if(document.getElementById("in_user_id_"+cnt).value !== "" && document.getElementById("in_user_name_"+cnt).value !== "" && document.getElementById('in_password_'+cnt).value !== "" && document.getElementById('re_password_'+cnt).value !== ""){
          if(document.getElementById('in_password_'+cnt).value == document.getElementById('re_password_'+cnt).value){
            var p_id = document.getElementById("in_user_id_"+1).value;
            //console.log(p_id);
            p_user_id.push(document.getElementById("in_user_id_"+cnt).value);
            p_user_name.push(document.getElementById("in_user_name_"+cnt).value);
            p_password.push(document.getElementById("in_password_"+cnt).value);
            post_flag = 1;
          }
          //console.log(p_user_id);
        }else{
          console.log("NG");
        }
      }

      //削除ユーザの取得
      var delete_user_id = [];
      var delete_flag = 0;
      //console.log(table.rows.length);
      for(var p = 1; p < table.rows.length; p++){
        //console.log("test");
        if(table.rows[p].cells[4].children[0].checked == true){
          delete_user_id.push(table.rows[p].cells[1].innerHTML);
          delete_flag = 1;
          //console.log(table.rows[p].cells[1].innerHTML);
        }
      }
        if(delete_flag == 1){
            if(post_flag == 0 && delete_flag == 1){
            post_flag = 2;
          }else{
            post_flag = 3;
          }
        }
        console.log(delete_user_id);
        console.log(post_flag);
      
      


      if(post_flag == 1){
        //新規ユーザー情報をPOST
        fd = new FormData();
    //変更内容
    fd.append('p_user_id',p_user_id);
    fd.append('p_user_name',p_user_name);
    fd.append('p_pass',p_password);
    xhttpreq = new XMLHttpRequest();
    xhttpreq.onreadystatechange = function() {
        if (xhttpreq.readyState == 4 && xhttpreq.status == 200) {
            alert(xhttpreq.responseText);
        }
    };
    xhttpreq.open("POST", "insert_user.php", true);
    xhttpreq.addEventListener('load', (event) => {
        window.close();
    });
    xhttpreq.send(fd);
      }else if(post_flag == 2){
        //deleteのみ
        fd = new FormData();
    //変更内容
    fd.append('p_delete_user_id',delete_user_id);
    xhttpreq = new XMLHttpRequest();
    xhttpreq.onreadystatechange = function() {
        if (xhttpreq.readyState == 4 && xhttpreq.status == 200) {
            alert(xhttpreq.responseText);
        }
    };
    xhttpreq.open("POST", "insert_user.php", true);
    xhttpreq.addEventListener('load', (event) => {
        window.close();
    });
    xhttpreq.send(fd);
      }else if(post_flag == 3){
        //postとdelete
        fd = new FormData();
    //変更内容
    fd.append('p_user_id',p_user_id);
    fd.append('p_user_name',p_user_name);
    fd.append('p_pass',p_password);
    fd.append('p_delete_user_id',delete_user_id);
    xhttpreq = new XMLHttpRequest();
    xhttpreq.onreadystatechange = function() {
        if (xhttpreq.readyState == 4 && xhttpreq.status == 200) {
            alert(xhttpreq.responseText);
        }
    };
    xhttpreq.open("POST", "insert_user.php", true);
    xhttpreq.addEventListener('load', (event) => {
        window.close();
    });
    xhttpreq.send(fd);
      }

      //console.log(document.getElementById("in_user_name_2").value);
    }

    function add(){
      //ユーザー入力欄の追加
        var no = document.createElement("h6");
        var no_text = document.createTextNode("No." + i);
        no.appendChild(no_text);
        parent.appendChild(no);

        var text0 = document.createTextNode("ユーザID");
        var input0 = document.createElement("input");
        input0.setAttribute('type', 'text');
        input0.setAttribute('id', 'in_user_id_'+i);
        input0.setAttribute('value', '');
        parent.appendChild(text0);
        parent.appendChild(input0);
        parent.appendChild(document.createElement("br"));

        var text1 = document.createTextNode("ユーザ名");
        var input1 = document.createElement("input");
        input1.setAttribute('type', 'text');
        input1.setAttribute('id', 'in_user_name_'+i);
        parent.appendChild(text1);
        parent.appendChild(input1);
        parent.appendChild(document.createElement("br"));
        
    var text2 = document.createTextNode("パスワード");
        var input2 = document.createElement("input");
        input2.setAttribute('type', 'password');
        input2.setAttribute('id', 'in_password_'+i);
        parent.appendChild(text2);
        parent.appendChild(input2);
        parent.appendChild(document.createElement("br"));

    var text3 = document.createTextNode("パスワード（再入力）");
        var input3 = document.createElement("input");
        input3.setAttribute('type', 'password');
        input3.setAttribute('id', 're_password_'+i);
        parent.appendChild(text3);
        parent.appendChild(input3);
        parent.appendChild(document.createElement("br"));

        i = i + 1;
}

  function edit_user_info(tr){
    var row = tr.parentNode.parentNode;
    var no = row.cells[0].innerHTML;
    var user_id = row.cells[1].innerHTML;
    var user_name = row.cells[2].innerHTML;
    var pass = row.cells[3].innerHTML;
    var param = "userid="+user_id+"&username="+user_name+"&pass="+pass+"&no="+no;
    //console.log(row.cells[1].id);
    window.open("c_edit_user_info.php?" + param, "",'width=400, height=300');
  }
        
        
        
        
        
        
        
    
</script>
</body>
</html>