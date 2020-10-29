<?php
require "conn.php";

$projects_name  = $_POST["projects_name"];
$sql = "INSERT INTO project_information(project_name) VALUES ('projects_name')";
$result  = mysqli_query($conn, $sql);
if($result){
echo "Data Inserted";
}
else{
echo "Failed";
}
?>