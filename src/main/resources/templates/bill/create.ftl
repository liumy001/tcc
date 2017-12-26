<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../static/css/date.css" media="all">
    <title>填写记录</title>
    <style>
        body,input{font:normal 14px "Microsoft Yahei";margin:0;padding:0}
        .odform-tit{font-weight:normal;font-size:25px;color:#595757;line-height:40px;text-align:center;border-bottom:1px solid #c9cacb;margin:0;padding:5% 0}
        .odform-tit img{height:40px;vertical-align:middle;margin-right:15px}
        .odform{padding:5%}
        .input-group{margin-bottom:5%;position:relative}
        .input-group label{padding:2% 0;position:absolute;color:#595757}
        .input-group input{margin-left:5em;padding:3% 5%;box-sizing:border-box;background:#efeff0;border:0;border-radius:5px;color:#595757;width:75%}
        .odform button{background:#8ec31f;color:#fff;text-align:center;border:0;border-radius:10px;padding:3%;width:100%;font-size:16px}
        .odform .cal{background-image:url(../static/img/daetixian-cal.png);background-repeat:no-repeat;background-position:95% center;background-size:auto 50%}
        .odform .xl{background-image:url(../static/img/daetixian-xl.png);background-repeat:no-repeat;background-position:95% center;background-size:auto 20%}
    </style>


</head>

<body>
<h1 class="odform-tit"><img src="../static/img/daetixian-tit.png" alt="填写记录">填写记录</h1>
<div class="odform">
    <form action="#">
        <div class="input-group">
            <label for="wdname">账单名称</label>
            <input type="text" class="xl" id="wdname" value="账单名称">
        </div>
        <div class="input-group">
            <label for="khname">金额</label>
            <input type="text" id="amount" placeholder="请输入您金额">
        </div>
            <div class="iDate full">
                <label for="khname">消费时间</label>
                <input type="text" class="cal" id="consumTime" name="consumTime" placeholder="请选择消费时间">
                <#--<button type="button" class="addOn"></button>
            </div>

        <div class="input-group">
            <label for="khname">附加备注</label>
            <input type="text" id="remark" placeholder="备注">
        </div>
        <button style="font:normal 14px "Microsoft Yahei";margin:0;padding:0">提交</button>
    </form>
</div>
<script type="text/javascript" src="../static/js/jquery.min.js"></script>
<script type="text/javascript" src="../static/js/moment.js"></script>
<script type="text/javascript" src="../static/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        if($(".iDate.full").length>0){
            $(".iDate.full").datetimepicker({
                locale: "zh-cn",
                format: "YYYY-MM-DD a hh:mm",
                dayViewHeaderFormat: "YYYY年 MMMM"
            });
        }
    })
</script>
</body>
</html>