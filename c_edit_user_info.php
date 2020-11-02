<?php
if(isset($_GET['userid'])){
  $user_id = $_GET['userid'];
  $user_name = $_GET['username'];
  $pass = $_GET['pass'];
  $no = $_GET['no'];
    session_start();
    $_SESSION['user_id'] = $user_id;
  }
?>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ユーザ情報編集</title>
</head>
<body>
<h2>ユーザ情報編集</h2>
<form action="c_edit_company_info.php" method="post">
<p>
ユーザID<input type = "text" name = "company_name" id = "i_id" value = "<?php echo $user_id; ?>"><br />
ユーザ名<input type ="text" name="address" id = "i_name" value = "<?php echo $user_name; ?>"><br />
パスワード<input type = "password" name="tel" id = "i_pass" value = "<?php echo $pass; ?>"><br />
パスワード（再入力）<input type = "password" name="tel" id = "i_pass_re" value = "<?php echo $pass; ?>"><br />
</p>
<!--<p><input type="button" name="change" value="変更" onclick = change() /></p>-->
</form>
<p><input type="button" name="change" value="変更" onclick = change() /></p>
<script>
var no = <?php echo $no; ?>;
function change(){
    if(document.getElementById("i_pass").value == document.getElementById("i_pass_re").value){
        //表示内容を親windowに反映
    window.opener.document.getElementById("user_info").rows[no].cells[1].innerHTML = document.getElementById("i_id").value;
    window.opener.document.getElementById("user_info").rows[no].cells[2].innerHTML = document.getElementById("i_name").value;
    window.opener.document.getElementById("user_info").rows[no].cells[3].innerHTML = document.getElementById("i_pass").value;
    
    //変更内容をPOST
    fd = new FormData();
    //変更内容
    fd.append('p_user_id',document.getElementById("i_id").value);
    fd.append('p_user_name',document.getElementById("i_name").value);
    fd.append('p_pass',document.getElementById("i_pass").value);
    xhttpreq = new XMLHttpRequest();
    xhttpreq.onreadystatechange = function() {
        if (xhttpreq.readyState == 4 && xhttpreq.status == 200) {
            alert(xhttpreq.responseText);
        }
    };
    xhttpreq.open("POST", "update_users_info.php", true);
    xhttpreq.addEventListener('load', (event) => {
        window.close();
    });
    xhttpreq.send(fd);
    
    }
    
}
</script>
</body>
</html>
