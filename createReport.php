<html>
<head><title>PHP TEST</title></head>
<body>

<?php
//mysql�Ƃ̐ڑ�
$link = mysqli_connect('localhost', 'testuser', 'testuser');
if (!$link) {
    die('Failed connecting'.mysqli_error());
}
//print('<p>Successed connecting</p>');

//DB�̑I��
$db_selected = mysqli_select_db($link , 'test_db');
if (!$db_selected){
    die('Failed Selecting table'.mysql_error());
}

$date = $_POST["date"];

//�������utf8�ɐݒ�
mysqli_set_charset($link , 'utf8');

//pdf�e�[�u���̎擾
$result_report_information  = mysqli_query($link ,"SELECT * FROM pic WHERE date = '$date' ");
if (!$result_report_information) {
    die('Failed query'.mysql_error());
}

//�f�[�^�i�[�p�̔z��̎擾
$row_array_pic = array();
$i = 0;
while ($row = mysqli_fetch_assoc ($result_report_information)) {
    $row_array_pic[$i] = $row;
    $i++;
}


/* �f�[�^�̊i�[���ꂽ�z����AJSON�`���ɂ��ēf���o�� */
header('Content-Type: application/json; charset=utf-8');
echo json_encode($row_array_pic, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES); //JSON�`���ɂ��ĕԂ�

// MySQL�ɑ΂��鏈��

$close_flag = mysqli_close($link);


?>
</body>
</html>