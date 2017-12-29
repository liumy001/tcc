<!DOCTYPE html>
<html>
<head>
    <title>pull to refresh</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="../../static/css/reset.css" type="text/css"/>

    <!--必要样式-->
    <link rel="stylesheet" href="../../static/css/pullToRefresh.css" type="text/css"/>

    <script type="text/javascript" src="../../static/js/iscroll.js"></script>
    <script type="text/javascript" src="../../static/js/pullToRefresh.mini.js"></script>

    <style type="text/css" media="all">

        a {
            text-decoration: none;
        }

        a:link {
            color: red;
        }

        a:visited {
            color: orange;
        }

        a:hover {
            color: green;
        }

        a:active {
            color: black;
        }

        body, html {
            padding: 0;
            margin: 0;
            height: 100%;
            font-family: Arial, Microsoft YaHei;
            color: #111;
        }

        .scroller li {
            height: 60px;
            border-bottom: 1px solid #eee;
            background-color: #fff;
            font-size: 14px;
        }

        .pullDownLabel img {
            width: 13px;
            height: 13px;
            margin-top: -1px;
            vertical-align: -2px;
            margin-right: 5px;
        }

        #wrapper ul li img {
            width: 60px;
            float: left;
            margin-left: 10px;
        }

        .game-info {
            text-align: left;
            float: left;
            margin-left: 10px;
            width: 210px;
            overflow: hidden;
            height: 90px;
        }

        .game-info h1 {
            font-size: 16px;
            margin-bottom: 8px;
        }

        .game-info p:nth-child(2) {
            font-size: 12px;
            color: #B6B6B6;
        }

        .game-info p:nth-child(3) {
            font-size: 12px;
            color: #9D9D9D;
        }

        #wrapper ul li button {
            position: absolute;
            right: 20px;
            margin-top: 10px;
            background-color: #F8CD0C;
            border: 0;
            color: #fff;
            font-family: Microsoft YaHei;
            padding: 5px 14px;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<!--must content ul li,or shoupi-->
<a style="display: block;margin-bottom: 10px;" href="${basePath}/index">返回首页</a>
<div id="wrapper">
    <ul>


    <#if data?exists>
        <#list data as key>
            <li>
                <img src="../../static/img/game1.png">
                <div class="game-info">
                    <h1>${key.billName}</h1>
                    <p>消费时间     ${key.consumTime?string("yyyy-MM-dd HH:mm:ss")}</p>
                    <p>金额         ${key.amount/100.0}</p>
                    <p>备注         ${key.remark}</p>
                    <p>         ${key.remark}</p>
                </div>
            </li>
        <#-- <button>下载</button>-->
        </#list>
    </#if>


    </ul>
</div>

<script type="text/javascript">
    refresher.init({
        id: "wrapper",//<------------------------------------------------------------------------------------┐
        pullDownAction: Refresh,
        pullUpAction: Load
    });

    function Refresh() {
        setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
            var el, li, i;
            el = document.querySelector("#wrapper ul");
            document.getElementById("wrapper").querySelector(".pullDownIcon").style.display = "none";
            document.getElementById("wrapper").querySelector(".pullDownLabel").innerHTML = "<img src='css/ok.png'/>刷新成功";
            setTimeout(function () {
                wrapper.refresh();
                document.getElementById("wrapper").querySelector(".pullDownLabel").innerHTML = "";
            }, 1000);//模拟qq下拉刷新显示成功效果
            /****remember to refresh after  action completed！ ---yourId.refresh(); ----| ****/
        }, 3000);
    }

    function Load() {
        setTimeout(function () {// <-- Simulate network congestion, remove setTimeout from production!
            var el, li, i;
            el = document.querySelector("#wrapper ul");
            for (i = 0; i < 2; i++) {
                li = document.createElement('li');
                li.innerHTML = ""
                el.appendChild(li, el.childNodes[0]);
            }
            wrapper.refresh();
        }, 3000);
    }
</script>

</body>
</html>