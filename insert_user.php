<?php
if(isset($_POST['p_delete_user_id'])){
    $array_del_user = preg_split("/[\s,]+/", $_POST['p_delete_user_id']);
    require "conn.php";
    for($i = 0; $i < count($array_del_user); $i++){
        $sql = "delete from users_information_1 where users_id = '$array_del_user[$i]';";
        $result  = mysqli_query($conn, $sql);
        if($result){
        echo "Data Delete";
        }
        else{
        echo "Delete Failed";
        }
    }
}

if(isset($_POST['p_user_id'])){
//各要素を配列に変更
$array_user_id = preg_split("/[\s,]+/", $_POST['p_user_id']); 
$array_user_name = preg_split("/[\s,]+/", $_POST['p_user_name']); 
$array_password = preg_split("/[\s,]+/", $_POST['p_pass']); 
//print_r($array_company_name);
//print_r($array_address);
//print_r($array_TEL);
session_start();
$companies_id = $_SESSION['company_id'];
//$flag = $_SESSION['flag'];
$flag ="1";

for($i = 0; $i < count($array_user_id); $i++){
    require "conn.php";
    $sql = "INSERT INTO users_information_1 VALUES ('', '$array_user_id[$i]', '$array_user_name[$i]', '$array_password[$i]', '$companies_id', '$flag', '');";
    $result  = mysqli_query($conn, $sql);
    if($result){
        echo "Data Inserted";
        exit;
        }
        else{
        echo "Insert Failed";
        }
}
}


?>