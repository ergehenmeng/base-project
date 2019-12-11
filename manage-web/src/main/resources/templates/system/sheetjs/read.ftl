<input type="file" id="updateExcel" value="导入excel" onchange="imports(this);"/>
<script src="http://oss.sheetjs.com/js-xlsx/xlsx.full.min.js"></script>
<script type="text/javascript">

    function imports(obj){
        if(!obj.files){
            return false;
        }
        var f = obj.files[0];
        var reader = new FileReader();
        reader.onload = function(e){
            var data = e.target.result;
            try {
                var wb = XLSX.read(data,{"type":"binary"});
                var str = XLSX.utils.sheet_to_csv(wb.Sheets[wb.SheetNames[0]]);
                var splits = str.split("\n");
                var newArray = [];
                var flag = checkAndFormat(splits,newArray);
                if(flag){
                    var strIds = newArray.join(",\n");
                    alert(strIds);
                }else{
                    //清空值防止二次选择时,文件名一样导致无法触发change事件
                    obj.value = "";
                }
            }catch (e) {
                obj.value = "";
                alert("文件格式错误");
            }
        };
        reader.readAsBinaryString(f);
    }

    /**
     * 检查splits数组是否合法,并将结果放入到arr中
     * @param splits 待检查的数组
     * @param arr 新数组
     * @returns {boolean} 如果数据不合法则返回false
     */
    function checkAndFormat(splits,arr){
        var length = splits.length;
        for(var i = 0 ; i < length ; i++){
            var v = splits[i];
            if(v){
                var uids = v.split(",");
                var uid = uids[0];
                if(!isNumber(uid)){
                    alert("第" + (i + 1) + "行格式错误");
                    return false;
                }
                arr.push(uid);
            }
        }
        return true;
    }

    /**
     * 字符串是否为数字
     * @param str
     * @returns {*|boolean}
     */
    function isNumber(str){
        var reg = /^[0-9]+.?[0-9]*$/;
        return reg.test(str);
    }
</script>

