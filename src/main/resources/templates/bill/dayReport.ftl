<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>html5图表</title>

    <script type="text/javascript" src="../../static/js/jQuery.js"></script>
    <script type="text/javascript" src="../../static/js/jqplot.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var data = [[1, 2, 3, 4, 5, 6, 7, 8, 9]];
            var data_max = 30; //Y轴最大刻度
            var line_title = ["支付情况"]; //曲线名称
            var y_label = "金额 元"; //Y轴标题
            var x_label = "天"; //X轴标题
            var x = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]; //定义X轴刻度值
            var title = "最近七天报表"; //统计图标标题
            //j.jqplot.diagram.base("chart2", data, line_title, "这是统计标题", x, x_label, y_label, data_max, 2);

            $.ajax({
                url: "/billReport/getDayData",    //请求的url地址
                dataType: "json",   //返回格式为json
                async: true,//请求是否异步，默认为异步，这也是ajax重要特性
                data: {},    //参数值
                type: "post",   //请求方式
                beforeSend: function () {
                    console.log("请求前")
                },
                success: function (req) {
                    if (req.status == 'ok') {
                        console.log("数据请求成功")
                        var tArray = new Array();
                        tArray[0] = new Array();
                        for (var i = 0; i < req.data.data.length; i++) {
                            tArray[0][i] = req.data.data[i];
                        }
                        data = tArray;
                        data_max = req.data.data_max;
                        var cArray = new Array();
                        for (var j = 0; j < req.data.x.length; j++) {
                            cArray[j] = req.data.x[j];
                        }
                        x = cArray;

                    } else {
                        alert(req.message)
                        return;
                    }
                },
                complete: function () {
                    j.jqplot.diagram.base("chart1", data, line_title, title, x, x_label, y_label, data_max, 1);
                    console.log("请求完成")
                },
                error: function () {
                    console.log("请求异常")
                }
            });


        });
    </script>

</head>
<body>

<div id="chart1"></div>
<!-- 	<div id="chart2"></div> -->

</body>
</html>

