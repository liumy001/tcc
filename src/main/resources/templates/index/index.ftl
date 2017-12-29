<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title> New Document </title>

    <link rel="stylesheet" type="text/css" href="../../static/css/menu.css" media="all">

</head>

<body>

<div class="bg"><img src="../../static/img/home-default17.jpg" width="100%" height="100%"></div>

<div class="nav4">

    <nav>
        <div id="nav4_ul" class="nav_4">
            <ul class="box">
                <li>
                    <a href="javascript:;" class="off"><span>会员专区</span></a>
                    <dl>
                        <dd><a href="${basePath}/bill/toCreate"><span>创建账单</span></a></dd>
                        <dd><a href="${basePath}/billReport/toDay"><span>七日内报表</span></a></dd>
                        <dd><a href="${basePath}/bill/toList"><span>账单列表</span></a></dd>
                    </dl>
                </li>

            </ul>
        </div>
    </nav>

    <div id="nav4_masklayer" class="masklayer_div on">&nbsp;</div>

</div>

<script src="../../static/js/nav4.js"></script>
<script type="text/javascript">
    nav4.bindClick(document.getElementById("nav4_ul").querySelectorAll("li>a"), document.getElementById("nav4_masklayer"));
</script>

</body>
</html>
