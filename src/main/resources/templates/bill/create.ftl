<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1,maximum-scale=1, minimum-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>form</title>
    <style>
        img {
            border: 0;
        }

        body {
            background: #f7f7f7;
            color: #666;
            font: 12px/150% Arial, Verdana, "microsoft yahei";
        }

        html, body, div, dl, dt, dd, ol, ul, li, h1, h2, h3, h4, h5, h6, p, blockquote, pre, button, fieldset, form, input, legend, textarea, th, td {
            margin: 0;
            padding: 0;
        }

        article, aside, details, figcaption, figure, footer, header, main, menu, nav, section, summary {
            display: block;
            margin: 0;
            padding: 0;
        }

        audio, canvas, progress, video {
            display: inline-block;
            vertical-align: baseline;
        }

        a {
            text-decoration: none;
            color: #08acee;
        }

        a:active, a:hover {
            outline: 0;
        }

        button {
            outline: 0;
        }

        mark {
            color: #000;
            background: #ff0;
        }

        small {
            font-size: 80%;
        }

        img {
            border: 0;
        }

        button, input, optgroup, select, textarea {
            margin: 0;
            font: inherit;
            color: inherit;
            outline: none;
        }

        li {
            list-style: none;
        }

        i {
            font-style: normal;
        }

        a {
            color: #666;
        }

        a:hover {
            color: #eee;
        }

        em {
            font-style: normal;
        }

        h2, h3 {
            font-family: "microsoft yahei";
            font-weight: 100;
        }

        /* ------------------- */
        ::-moz-placeholder {
            color: #9fa3a7;
        }

        ::-webkit-input-placeholder {
            color: #9fa3a7;
        }

        :-ms-input-placeholder {
            color: #9fa3a7;
        }

        .pc-kk-form {
            padding: 15px 20px;
        }

        .pc-kk-form-list {
            background: #fff;
            border: 1px solid #e5e5e5;
            border-radius: 5px;
            height: 44px;
            line-height: 44px;
            margin-bottom: 10px;
        }

        .pc-kk-form-list input {
            width: 100%;
            border: none;
            background: none;
            color: #9fa3a7;
            font-size: 14px;
            height: 36px;
            padding: 4px 10px;
        }

        .pc-kk-form-list textarea {
            background: none;
            border: none;
            height: 60px;
            padding: 10px;
            resize: none;
            width: 94%;
            line-height: 22px;
            color: #9fa3a7;
            font-size: 14px;
        }

        .nice-select {
            position: relative;
            background: #fff url(../../static/img/a2.jpg) no-repeat right center;
            background-size: 18px;
            width: 47%;
            float: left;
            border: 1px solid #e5e5e5;
            border-radius: 5px;
            height: 44px;
            line-height: 44px;
        }

        .nice-select ul {
            width: 100%;
            display: none;
            position: absolute;
            left: -1px;
            top: 44px;
            overflow: hidden;
            background-color: #fff;
            max-height: 150px;
            overflow-y: auto;
            border: 1px solid #b9bcbf;
            z-index: 9999;
            border-radius: 5px;

        }

        .nice-select ul li {
            padding-left: 10px;
        }

        .nice-select ul li:hover {
            background: #f8f4f4;
        }

        .pc-kk-form-list-clear {
            background: none;
            border: none;
        }

        .pc-kk-form-btn button {
            background: #ec5224;
            color: #fff;
            border: none;
            width: 100%;
            height: 50px;
            line-height: 50px;
            font-size: 16px;
            border-radius: 50px;
        }
    </style>
</head>
<body>

<div class="pc-kk-form">
    <form action="">
        <div class="pc-kk-form-list">
            <input type="text" id="billName" placeholder="请输入账单名称">
        </div>
        <div class="pc-kk-form-list">
            <input type="text" id="amount" onblur="checkAmount()" placeholder="请输入金额">
        </div>


        <div class="pc-kk-form-list pc-kk-form-list-clear">
            <div class="nice-select" name="nice-select">
                <input type='text' id="dates" value=选择日期" readonly>
                <ul id="date-list"></ul>

            </div>
            <div class="nice-select" name="nice-select" style="float:right">
                <input type="text" id="time" value="选择时间" readonly>
                <ul>
                    <li data-value="00:00:00">00:00:00</li>
                    <li data-value="01:00:00">01:00:00</li>
                    <li data-value="02:00:00">02:00:00</li>
                    <li data-value="03:00:00">03:00:00</li>
                    <li data-value="04:00:00">04:00:00</li>
                    <li data-value="05:00:00">05:00:00</li>
                    <li data-value="06:00:00">06:00:00</li>
                    <li data-value="07:00:00">07:00:00</li>
                    <li data-value="08:00:00">08:00:00</li>
                    <li data-value="09:00:00">09:00:00</li>
                    <li data-value="10:00:00">10:00:00</li>
                    <li data-value="12:00:00">11:00:00</li>
                    <li data-value="13:00:00">13:00:00</li>
                    <li data-value="14:00:00">14:00:00</li>
                    <li data-value="15:00:00">15:00:00</li>
                    <li data-value="16:00:00">16:00:00</li>
                    <li data-value="17:00:00">17:00:00</li>
                    <li data-value="18:00:00">18:00:00</li>
                    <li data-value="19:00:00">19:00:00</li>
                    <li data-value="20:00:00">20:00:00</li>
                    <li data-value="21:00:00">21:00:00</li>
                    <li data-value="22:00:00">22:00:00</li>
                    <li data-value="23:00:00">23:00:00</li>
                </ul>

            </div>
        </div>
        <div class="pc-kk-form-list pc-kk-form-list-clear">
            <div class="nice-select" name="nice-select" style="width:100%" id="cateSelect">
                <input type="text" id="bigCategory" relValue="" value="选择类别" readonly>
                <ul>
                <#if data?exists>
                    <#list data as key>
                        <tr>
                            <li data-value="${key.id}">${key.categoryName}</li>
                        </tr>
                    </#list>
                </#if>
                </ul>
            </div>
        </div>
        <div class="pc-kk-form-list pc-kk-form-list-clear">
            <div class="nice-select" name="nice-select" style="width:100%" id="zCateSelect">
                <input type="text" id="smallCategory" relValue="" value="选择子类" readonly>
                <ul id="zC">

                </ul>
            </div>
        </div>
        <div class="pc-kk-form-list" style="height:auto">
            <textarea id="remark" onfocus="textClear()">请输入备注内容</textarea>
        </div>
    </form>
    <div class="pc-kk-form-btn">
        <button  id="save" onclick="toSubmit()">保存</button>
    </div>
</div>


<script type="text/javascript" src="../../static/js/jquery-1.11.1.min.js"></script>

<script>
    $(function () {
        $(document).on("click", function () {
            $('[name="nice-select"] ul').hide();
        });

        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var now_date = year + "-" + month + "-" + day;
        $("#date").val(now_date);
        var html = " <li data-value='" + now_date + "'>" + now_date + "</li> ";
        for (var i = 0; i < 7; i++) {
            now.setTime(now.getTime() - 1000 * 60 * 60 * 24)
            year = now.getFullYear();       //年
            month = now.getMonth() + 1;     //月
            day = now.getDate();            //日
            now_date = year + "-" + month + "-" + day;
            html += " <li data-value='" + now_date + "'>" + now_date + "</li>";
        }
        $("#date-list").append(html);

        $('[name="nice-select"]').click(function (e) {
            $('[name="nice-select"]').find('ul').hide();
            $(this).find('ul').show();
            e.stopPropagation();
        });

        $('[name="nice-select"] li').hover(function (e) {
            $(this).toggleClass('on');
            e.stopPropagation();
        });

        $('[name="nice-select"] li').click(function (e) {
            var val = $(this).text();
            var relVal = $(this).attr('data-value');
            $(this).parents('[name="nice-select"]').find('input').val(val);
            $(this).parents('[name="nice-select"]').find('input').prop("relValue", relVal);
            console.log("relVal=" + relVal)
            $('[name="nice-select"] ul').hide();
            e.stopPropagation();
        });

        $('#cateSelect li').on("click", function () {
            var bigCategory = $("#bigCategory").prop("relValue");
            if (!bigCategory) {
                alert('获取父类失败');
                return;
            }
            console.log("最终值：" + bigCategory)
            $.ajax({
                url: "/category/getCategory",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true,//请求是否异步，默认为异步，这也是ajax重要特性
                data: {"categoryId": bigCategory},    //参数值
                type: "post",   //请求方式
                beforeSend: function () {
                    console.log("请求前")
                },
                success: function (req) {
                    if (req.status == 'ok') {
                        var chtml = "  ";
                        for (var i = 0; i < req.data.length; i++) {
                            chtml += " <li data-value='" + req.data[i].id + "'>" + req.data[i].categoryName + "</li>"
                        }
                        $("#zC").html(chtml)
                    } else {
                        alert(req.message)
                        return;
                    }
                },
                complete: function () {
                    console.log("请求完成")
                },
                error: function () {
                    console.log("请求异常")
                }
            });
        });

        $('#zC').delegate('li', "click", function (e) {
            console.log("响应子类事件");
            var val = $(this).text();
            var relVal = $(this).attr('data-value');
            $(this).parents('[name="nice-select"]').find('input').val(val);
            $(this).parents('[name="nice-select"]').find('input').prop("relValue", relVal);
            console.log("relVal=" + relVal)
            $('[name="nice-select"] ul').hide();
            e.stopPropagation();
        });


    });

    function toSubmit() {
        console.log("响应表单提交请求")
        var billName = $("#billName").val();
        var amount = $("#amount").val();
        var bigCategory = $("#bigCategory").prop("relValue");
        var smallCategory = $("#smallCategory").prop("relValue");
        var date = $("#dates").prop('relValue');
        var time = $("#time").prop('relValue');
        var remark = $("#remark").val();
        console.log(billName+","+amount+","+bigCategory+","+smallCategory+","+date+","+time+","+remark)
        if (!billName) {
            alert('请输入账单名称')
            return
        }
        if (!bigCategory) {
            alert('请选择大类')
            return
        }
        if (!smallCategory) {
            alert('请选择小类')
            return
        }
        if (!date) {
            alert('请输入账单名称')
            return
        }
        if (!time) {
            alert('请选择日期')
            return
        }
        if (!amount) {
            alert('请输入金额')
            return
        }
        var reg = new RegExp(/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/);
        if (!reg.test(amount)) {
            alert('数字格式错误')
            $('#amount').val('');
            return
        }

        $.ajax({
            url: "/bill/create",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                "categoryId": smallCategory,
                "amount": amount,
                "billName": billName,
                "consumTime": date + " " + time,
                "remark": remark
            },    //参数值
            type: "post",   //请求方式
            beforeSend: function () {
                console.log("请求前")
            },
            success: function (req) {
                if (req.status == 'ok') {
                    alert('保存成功');
                    window.location.href = "${basePath}/bill/toCreate"
                } else {
                    alert(req.message)
                    return;
                }
            },
            complete: function () {
                console.log("请求完成")
            },
            error: function () {
                console.log("请求异常")
            }
        });

    }

    function checkAmount() {
        var amount = $('#amount').val();
        if (!amount) {
            return
        }
        var reg = new RegExp(/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/);
        if (!reg.test(amount)) {
            alert('数字格式错误')
            $('#amount').val('');
            return
        }
    }

    function textClear() {
        var remark = $("#remark").val();
        if (remark == '请输入备注内容') {
            $("#remark").val('');
        }
    }
</script>
</body>
</html>