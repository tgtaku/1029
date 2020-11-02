<?php
if(isset($_POST['p_user_id'])){
  session_start();
  $user_id = $_SESSION['user_id'];
  $p_user_id = $_POST["p_user_id"];
  $p_user_name = $_POST["p_user_name"];
  $p_pass = $_POST["p_pass"];
  //print_r($p_user_id);
  //print_r($p_user_name);
  //print_r($p_pass);
  require "conn.php";
  $mysql_qry = "UPDATE users_information_1 SET users_id='$p_user_id', users_name='$p_user_name', password='$p_pass' WHERE users_id = '$user_id' ;";
  $result = mysqli_query($conn, $mysql_qry);
  //echo "<script type='text/javascript'>window.close();</script>";
}
?>