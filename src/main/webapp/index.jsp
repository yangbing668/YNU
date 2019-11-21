<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/Index.css">
    <script src="js/Count.js" type="text/javascript" rel="script"></script>
    <title>Title</title>
</head>
<body>
    <div class="Index-Header">
    	<a href="https://www.ynu.edu.cn/" target="_blank">Yunnan University</a>
		<style type="text/css">
			a{text-decoration: none;}
		</style>    
    </div>
    <div class="Search">
        <form>
            输入年份：<input type="text" class="Startyear" placeholder=" 起始年份">—
            <input type="text" class="Endyear" placeholder=" 结束年份">
            <input type="submit" value="查询" class="Find" id="Find" onclick="check()">
            <input type="button" value="下载" class="Download" onclick="download()">
        </form>
    </div>
    <div class="Count" id="Count">
        <p>总共<b>x</b>条</p>
    </div>
    <div class="Result">
        <div class="Article">
            <b class="Title"> asdasd </b>
            <b class="date"> asdasd </b>
        </div>
        <div class="Article">
            <b class="Title"> asdasd </b>
            <b class="date"> asdasd </b>
        </div>
        <div class="Article">
            <b class="Title">asdasd</b>
            <b class="date">asdasd</b>
        </div>
    </div>
</body>
</html>