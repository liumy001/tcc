/*
 * 
 *  url = "/devops/?page=user.user"
 *  paramter = {id:id,user:user}
 *  
*/
function post(paramter , url){
    var result = "";
    $.ajax({
            type:"POST",
            url:url,
            data: paramter,
            async:false,
            success:function(data){
                    result = data;
            }
    });
    return result;
}

//获取from数据
function get_form_data(){
 var result = {}
 forch = ["input","textarea","select"]
 for(i=0;i<forch.length;i++){
   $.each($("form "+forch[i]),
      function(name,object){
        result[$(object).attr("name")] = $(object).val()
     }
   );
 }
 return result;
}
/**
 * 防止重复提交
 */
function needStopRepeatClick(arrays){
    if(arrays.length == 0){
        arrays.push(new Date().getTime());
        return false;
    }
    var t = arrays[arrays.length-1];
    var t2 = new Date().getTime();
    //1秒内阻止
    if(t2-t < 1000){
        console.log("stop repeat...")
        return true;
    }
    arrays.push(t2);
}