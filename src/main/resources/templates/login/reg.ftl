<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>注册用户</title>

    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="../../static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../static/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../static/css/form-elements.css">
    <link rel="stylesheet" href="../../static/css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Favicon and touch icons -->
    <link rel="shortcut icon" href="../static/img/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="../../static/img/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="../../static/img/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="../../static/img/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../../static/img/ico/apple-touch-icon-57-precomposed.png">

</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>系统入口</strong>注册用户</h1>
                    <div class="description">
                        <p>
                            欢迎进入系统
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>注册</h3>
                            <p>请输入用户名和密码:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="" method="post" class="login-form">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">用户名</label>
                                <input type="text" name="username" placeholder="请输入用户名"
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">密码</label>
                                <input type="password" name="password" placeholder="请输入密码"
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">昵称</label>
                                <input type="text" name="nickName" placeholder="请输入昵称"
                                       class="form-username form-control" id="form-nickName">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">邮箱</label>
                                <input type="email" name="email" placeholder="请输入邮箱" class="form-username form-control"
                                       id="form-email">
                            </div>
                        </form>
                        <button onclick="toReg()" style="margin-left: 40px;" class="btn">注册</button>
                        <button onclick="toLog()" style="margin-right: 40px;" class="btn">去登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- Javascript -->
<script src="../../static/js/jquery-1.11.1.min.js"></script>
<script src="../../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../../static/js/jquery.backstretch.min.js"></script>
<script src="../../static/js/scripts.js"></script>

<!--[if lt IE 10]>
<script src="../../static/js/placeholder.js"></script>
<![endif]-->
<script>
    function toReg() {
        var userName = $("#form-username").val();
        var password = $("#form-password").val();
        var nickName = $("#form-nickName").val();
        var email = $("#form-email").val();
        if (!userName) {
            alert('请输入用户名')
            return
        }
        if (!password) {
            alert('请输入密码')
            return
        }
        if (!nickName) {
            alert('请输入昵称')
            return
        }
        if (!email) {
            alert('请输入邮箱')
            return
        }
        $.ajax({
            url: "/user/sendEmail",    //请求的url地址
            dataType: "json",   //返回格式为json
            async: true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                "userName": userName,
                "password": password,
                "nickName": nickName,
                "email": email
            },    //参数值
            type: "post",   //请求方式
            beforeSend: function () {
                console.log("请求前")
            },
            success: function (req) {
                if (req.status == 'ok') {
                    alert("注册邮件已发送请登录邮箱激活后登录")
                    return
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

    function toLog() {
        window.location.href = "${basePath}";
    }
</script>
</body>

</html>