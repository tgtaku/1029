<?php
if(isset($_POST['company_name'])){
  session_start();
  $companies_id = $_SESSION['company_id'];
  $p_com = $_POST["company_name"];
  $p_add = $_POST["address"];
  $p_tel = $_POST["tel"];
  print_r($p_com);
  print_r($p_add);
  print_r($p_tel);
  require "conn.php";
  $mysql_qry = "UPDATE companies_information_1 SET companies_name='$p_com', street_address='$p_add', tel='$p_tel' WHERE companies_id = '$companies_id' ;";
  $result = mysqli_query($conn, $mysql_qry);
  //echo "<script type='text/javascript'>window.close();</script>";
}
?>