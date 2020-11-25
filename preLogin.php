<?php
require "conn.php";
$owner_code = $_POST["owner"];
$mysql_qry = "select * from owner where owner_code = '$owner_code';";
$result = mysqli_query($conn, $mysql_qry);
if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){
		$connection = $row['connection'];
}
echo $connection;
}
else{
echo "login not success";
}
?>