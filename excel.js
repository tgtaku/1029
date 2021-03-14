//パスでファイル指定
//シート名と、

function appendScript(URL) {
	var el = document.createElement('script');
	el.src = URL;
	document.body.appendChild(el);
};
appendScript("https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.16.2/xlsx.full.min.js");
appendScript("https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js");

let selectedFile;
console.log("1=" + window.XLSX);
document.getElementById('input').addEventListener("change", (event) => {
    selectedFile = event.target.files[0];
    console.log(selectedFile);
})

//let selectedFile;
document.getElementById('button').addEventListener("click", () => {
    if(selectedFile){
        
    //console.log("1=" + window.XLSX);
    //document.getElementById('input').addEventListener("change", (event) => {
    //selectedFile = event.target.files[0];
//})
        let fileReader = new FileReader();
        fileReader.readAsBinaryString(selectedFile);
        fileReader.onload = (event)=>{
         let data = event.target.result;
         let workbook = XLSX.read(data,{type:"binary"});
         console.log(workbook);
         workbook.SheetNames.forEach(sheet => {
              var rowObject = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheet]);
              console.log(rowObject);
              //テーブル作成処理実行
              buildtable(rowObject);
         });
         
        }
    }
    

    function buildtable(data){
        //console.log(data);/*JSONデータ確認*/
    
        var dataLength = data.length;
        const keys = Object.keys(data[0]);
        //console.log(keys);/*JSON Key確認*/
        var keyLength = keys.length;
        //console.log(dataLength);/*JSONデータの数確認*/
        //console.log(keyLength);/*JSON Keyの数確認*/
        
        //table要素の作成
        var table = document.createElement("table");
        table.setAttribute("id","table");
        fa.appendChild(table);/*div要素の下に挿入*/

        //tbody要素の作成
        var tbody = document.createElement("tbody");
        tbody.setAttribute("id","tbody");
        table.appendChild(tbody);
        
        //テーブルラベルの挿入
        var index = table.insertRow(-1);
        for(var i = 0; i < keyLength; i++){
            var th = document.createElement("th");
            th.setAttribute("id","th");
            th.innerHTML = keys[i];
            index.appendChild(th);
        }

        //要素の挿入
        for(var j = 0; j < dataLength; j++){
            var row = table.insertRow(-1);
            var data_array = data[j];
            for(key in data_array){
                var td = row.insertCell(-1);
                //console.log('key = ' + key);
                var value = data_array[key];
                //console.log(value);
                td.innerHTML = value;
            }
        }
    }
});