<html>
<head><title>PHP checkFileName</title></head>
<body>
pasokon
<?php
//mysql�Ƃ̐ڑ�
$link = mysqli_connect('localhost', 'root', '');
if (!$link) {
    die('Failed connecting'.mysqli_error());
}
//print('<p>Successed connecting</p>');

//DB�̑I��
$db_selected = mysqli_select_db($link , 'test_db');
if (!$db_selected){
    die('Failed Selecting table'.mysql_error());
}

$name = $_POST["name"];
//�������utf8�ɐݒ�
mysqli_set_charset($link , 'utf8');

//pdf�e�[�u���̎擾
$result_file  = mysqli_query($link ,"SELECT * FROM users_information_1 where users_id = '$name';");
if (!$result_file) {
    die('Failed query'.mysql_error());
}
//�f�[�^�i�[�p�z��̎擾
$row_array_file = array();
$i = 0;
while ($row = mysqli_fetch_assoc ($result_file)) {
    $row_array_file[$i] = $row;
    $i++;
    //print('<p>');
    //print('projectName='.$row['projectName']);
    //print(',path='.$row['path']);
    //print(',fileName='.$row['fileName']);
    //print('</p>');
}



/* �f�[�^�̊i�[���ꂽ�z����AJSON�`���ɂ��ēf���o�� */
header('Content-Type: application/json; charset=utf-8');
echo json_encode($row_array_file, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES); //JSON�`���ɂ��ĕԂ�

// MySQL�ɑ΂��鏈��
$close_flag = mysqli_close($link);

?>
</body>
</html>