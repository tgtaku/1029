<?php
require "conn.php";

$files_name  = $_POST["files_name"];
$users_name  = $_POST["users_name"];
$projects_name = $_POST["projects_name"];
$page = $_POST["page"];
$point_x = $_POST["point_x"];
$point_y = $_POST["point_y"];
$date = $_POST["date"];
$path = $_POST["path"];

$sql = "INSERT INTO local_pictures_information VALUES ('', '$files_name', '$users_name', '$projects_name', '$page','$point_x','$point_y','$date','$path', '', '', '');";
$result  = mysqli_query($conn, $sql);
if($result){
echo "Data Inserted";
}
else{
echo "Failed";
}
?>