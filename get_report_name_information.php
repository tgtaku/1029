<html>
<head><title>PHP get_pictures_name_information</title></head>
<body>

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

$projects_id = $_POST["projects_id"];

//�������utf8�ɐݒ�
mysqli_set_charset($link , 'utf8');

//pdf�e�[�u���̎擾
$result_pictures_information  = mysqli_query($link ,"SELECT * FROM report_name_1 WHERE projects_id ='$projects_id';");
if (!$result_pictures_information) {
    die('Failed query'.mysql_error());
}
//�f�[�^�i�[�p�z��̎擾
$row_array_pictures_information = array();
$i = 0;
while ($row = mysqli_fetch_assoc ($result_pictures_information)) {
    $row_array_pictures_information[$i] = $row;
    $i++;
    }

/* �f�[�^�̊i�[���ꂽ�z����AJSON�`���ɂ��ēf���o�� */
header('Content-Type: application/json; charset=utf-8');
echo json_encode($row_array_pictures_information, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES); //JSON�`���ɂ��ĕԂ�

// MySQL�ɑ΂��鏈��
$close_flag = mysqli_close($link);

?>
</body>
</html>