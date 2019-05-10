<%--
  Created by IntelliJ IDEA.
  User: joy
  Date: 17-3-20
  Time: 上午11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../comm/comm.jspf" %>
<html>
<head>
    <title>上传与下载</title>
    <link rel="dns-prefetch" href="baidu.com" /> <!-- 开启域名预解析，浏览器会在空闲时提前解析声明需要预解析的域名 -->
    <link rel="stylesheet" type="text/css" href="../../../css/test/udLoad.css"/>
    <script type="text/javascript" src="../../../js/Jquery/jquery-3.2.0.min.js"></script>
    <script type="text/javascript">

        var srcPath = "../../../images/test/preview.jpg";

        $(function () {
            $(".thumbs a").click(function () {
                var largePath = $(this).attr("href");
                var largeAlt = $(this).attr("title");
                $("#largeImg").attr({
                    src: largePath,
                    alt: largeAlt
                });
                return false;  // 阻止默认事件效果
            });

            $("#previewImg").mousemove(function (e) {
                $("#large").css({
                    top: e.pageY,
                    left: e.pageX
                }).html('<img src = "' + srcPath + '" />').show();
            });
            $("#previewImg").mouseout(function () {
                $("#large").hide();
            });
        });
        // 下载文件
        function downloadFile() {
            // 获取下载文件名
            var str = $("#largeImg").attr("src").split("/");
            var fileName = str[str.length - 1];
            location.href = "/downloadServlet?fileName=" + fileName;
        }

        // 显示预览图片
        function showPreview(obj) {
            var file = obj.files[0];
            if (window.FileReader) { //需要支持 HTML5
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onloadend = function () {
                    if (this.readyState == FileReader.DONE) {
                        srcPath = this.result;
                        // console.log(file.type);

                        // document.getElementById("previewImg").innerHTML = "<img src='" + this.result + "'/>";
                        $("#previewImg").attr("src", reader.result);
                    }
                };
            }

            /* 因为安全设置，该方案不可行；
             imgPaht = obj.value;
             document.getElementById("previewImg").innerHTML="<img src='"+imgPaht+"'/>"; */
        }
    </script>
</head>
<body>
<img id="previewImg" src="../../../images/test/preview.jpg" width="80" height="80"/>

<!-- action : uploadServlet会已相对路径的形式提交 -->
<!-- enctype： multipart/form-data 以二进制的形式向服务器提交数据 -->
<form action="/uploadServlet" method="post" enctype="multipart/form-data">
    <input id="myfile" name="myfile" type="file" onchange="showPreview(this)"/>
    <br>
    <input id="myfile" name="myfile" type="file" onchange="showPreview(this)"/>
    <br>
    <input id="myfile" name="myfile" type="file" onchange="showPreview(this)"/>
    <br>
    <input type="submit" value="上传文件"/>&nbsp;${info}
</form>

<div id="large"></div>
<hr>
<h2>图片预览</h2>
<p><img id="largeImg" src="../../../images/test/img1-lg.jpg" alt="Large Image"/></p>
<p class="thumbs">
    <a href="/images/test/img2-lg.jpg" title="Image2"><img src="../../../images/test/img2-thumb.jpg"></a>
    <a href="/images/test/img3-lg.jpg" title="Image3"><img src="../../../images/test/img3-thumb.jpg"></a>
    <a href="/images/test/img4-lg.jpg" title="Image4"><img src="../../../images/test/img4-thumb.jpg"></a>
    <a href="/images/test/img5-lg.jpg" title="Image5"><img src="../../../images/test/img5-thumb.jpg"></a>
    <a href="/images/test/img6-lg.jpg" title="Image6"><img src="../../../images/test/img6-thumb.jpg"></a>
</p>
<!-- 该方法无法拿取this对象，如想获取this对象： <a href="javascript:void(0);" onclick = "xxxx(this)">下载</a> -->
<a href="javascript:downloadFile();">下载文件</a>&nbsp;${downloadInfo}
</body>
</html>
