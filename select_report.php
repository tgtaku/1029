<?php
//検索結果格納用配列
$row_array_project_name = [];
$row_array_company = [];
$row_array_report_place = [];
$row_array_date = [];
$row_array_report_name = [];

if(isset($_POST['search_pro'])){
    require "conn.php";
    /*$project = $_POST["project"];
    $address = $_POST["address"];
    $overview = $_POST["overview"];*/

    //全部なし
    //if($project =="" && $address == "" && $overview == ""){
        $mysql_qry = "select * from reports_name_1 inner join projects_information_1 on reports_name_1.projects_id = projects_information_1.projects_id inner join companies_information_1 on reports_name_1.company_id = companies_information_1.companies_id inner join projects_kanri_1 on reports_name_1.projects_id = projects_kanri_1.projects_id;";
        $result = mysqli_query($conn, $mysql_qry);
        if(mysqli_num_rows($result) > 0){
            $i = 0;
            while($row = mysqli_fetch_assoc($result)){
                array_push($row_array_project_name ,$row['projects_name']);
                array_push($row_array_company, $row['companies_name']);
                array_push($row_array_report_place, $row['reports_place']);
                array_push($row_array_date, $row['update_date']);
                array_push($row_array_report_name, $row['reports_name']);
                $i++;
            }
        }
    //}
}else{
}
$json_array_project_name = json_encode($row_array_project_name);
$json_array_company = json_encode($row_array_company);
$json_array_report_place = json_encode($row_array_report_place);
$json_array_date = json_encode($row_array_date);
$json_array_report_name = json_encode($row_array_report_name);

?>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>報告書選択</title>
        <link rel="stylesheet" href = "style.css">
    </head>
    <body>
    <main>
        <div class="main-container">
            <div class="sidebar">
                <h1>menu</h1>
                <ul class="subnav">
                    <li>現場情報管理</li>
                    <li><a href="p_entry.php" >-現場登録</a></li>
                    <li><a href="p_edit.php" >-現場編集</a></li>
                    <li>施工会社管理</li>
                    <li><a href="c_entry.php">-施工会社登録</a></li>
                    <li><a href="c_edit.php" >-施工会社/ユーザ編集</a></li>
                    <li>施工状況確認</li>
                    <li><a href="select_report.php"　style="background-color:gray">-報告書確認</a></li>
                </ul>
            </div>
            <div class="maincol">
                <div class="maincol-container">
    

    <h2>表示する報告書を選択してください。</h2>

    <p>
        <form action="select_report.php" method = "post">
        管理者ID<input type = "text" name = "kanri_id" value = ""><br />
        現場名<input type ="text" name="project" value = ""><br />
        施工会社<input type ="text" name="com" value = ""><br />
        施工箇所<input type ="text" name="report_place" value = ""><br />
        日付<input type ="text" name="date" value = ""><br />
        
        <input type = "submit" id = "search_pro" name="search_pro" value = "検索">
        </form>
    </p>
    <table id = "pro_info" name = "table_com">
                <tr>
                <th style="WIDTH: 50px" id="no">No</th>
                    <th style="WIDTH: 200px" id="t_project">現場名</th>
                    <th style="WIDTH: 200px" id="t_com">施工会社</th>
                    <th style="WIDTH: 200px" id="t_report_place">施工箇所</th>
                    <th style="WIDTH: 100px" id="t_date">日付</th>
                    <th style="WIDTH: 200px" id="t_report_name">報告書名</th>
                    <th style="WIDTH: 100px" id="editButton"></th>
                </tr>
               
            </table>
            <!--<input type = "button" id = "pro_button" name="editpro" value = "現場編集" onclick="editpro()">-->
    </div>
            </div>
        </div>
</main>
<script>

if(<?php echo $json_array_project_name; ?> != ""){
        //テーブル表示
        //phpから配列の取得
        var array_project_name = <?php echo $json_array_project_name; ?>;
        //console.log(array_project_name);
        var array_company = <?php echo $json_array_company; ?>;
        var array_report_place = <?php echo $json_array_report_place; ?>;
        var array_date = <?php echo $json_array_date; ?>;
        var array_report_name = <?php echo $json_array_report_name; ?>;
        
        
        //テーブル情報
        var table = document.getElementById("pro_info");
        var tableLength = array_company.length;
        var cell1 = [];
        var cell2 = [];
        var cell3 = [];
        var cell4 = [];
        var cell5 = [];
        var cell6 = [];
        var cell7 = [];

            //会社名
            for(var j = 0; j < tableLength; j++){
                var row = table.insertRow(-1);
                cell1.push(row.insertCell(-1));
                cell2.push(row.insertCell(-1));
                cell3.push(row.insertCell(-1));
                cell4.push(row.insertCell(-1));
                cell5.push(row.insertCell(-1));
                cell6.push(row.insertCell(-1));
                cell7.push(row.insertCell(-1));
                cell1[j].innerHTML = table.rows.length-1;
                cell2[j].innerHTML = array_project_name[j];
                cell2[j].id = j + 1 +"company";
                cell3[j].innerHTML = array_company[j];
                cell3[j].value = "address";
                cell4[j].innerHTML = array_report_place[j];
                cell4[j].value = "TEL";
                cell5[j].innerHTML = array_date[j];
                cell6[j].innerHTML =array_report_name[j];
                cell7[j].innerHTML = '<input type = "button" value = "表示" onclick="show_report(this)"/>';
                //cell4[j].innerHTML = '<input type = "submit" id = "p_project" name="p_project" value = "編集">';
        }
        }
</script>
    </body>
</html>