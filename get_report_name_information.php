<html>
<head><title>PHP get_pictures_name_information</title></head>
<body>

<?php
//mysqlとの接続
$link = mysqli_connect('localhost', 'root', '');
if (!$link) {
    die('Failed connecting'.mysqli_error());
}
//print('<p>Successed connecting</p>');

//DBの選択
$db_selected = mysqli_select_db($link , 'test_db');
if (!$db_selected){
    die('Failed Selecting table'.mysql_error());
}

$projects_id = $_POST["projects_id"];

//文字列をutf8に設定
mysqli_set_charset($link , 'utf8');

//pdfテーブルの取得
$result_pictures_information  = mysqli_query($link ,"SELECT * FROM report_name_1 WHERE projects_id ='$projects_id';");
if (!$result_pictures_information) {
    die('Failed query'.mysql_error());
}
//データ格納用配列の取得
$row_array_pictures_information = array();
$i = 0;
while ($row = mysqli_fetch_assoc ($result_pictures_information)) {
    $row_array_pictures_information[$i] = $row;
    $i++;
    }

/* データの格納された配列を、JSON形式にして吐き出す */
header('Content-Type: application/json; charset=utf-8');
echo json_encode($row_array_pictures_information, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES); //JSON形式にして返す

// MySQLに対する処理
$close_flag = mysqli_close($link);

?>
</body>
</html>