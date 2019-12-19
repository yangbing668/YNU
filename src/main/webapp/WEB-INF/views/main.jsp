<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/Index.css">
    <script src="css/Count.js" type="text/javascript" rel="script"></script>
    <title>Title</title>
</head>
<body>
    <div class="Search">
        <form action="download" method="post">
        选择类别：
        	<select id = "type" class="category" name="type" onclick="choose()">
        		<option value="EI">EI</option>
        		<option value="SCI">SCI</option>
        		<option value="CPCI">CPCI</option>
        	</select>
        	<br>
           选择年份：
           	<select id = "startYear" name="startYear" class="Startyear">
           		<option>--请选择--</option>
           	</select>
           	<script type="text/javascript">
           		var startYear = document.getElementById("startYear");
				var opt1 = null;
				var now = new Date()
				var nowYear = now.getFullYear()
				for(var i = 2013; i <= nowYear; i++)
				{
					opt1 = document.createElement("option");
					opt1.value = i;
					opt1.innerHTML = i;
					startYear.appendChild(opt1);
				}
           	</script>
           	<label>——</label>
           	
            <select id = "endYear" name="endYear" class="Endyear">
            	<option>--请选择--</option>
            </select>
            <script type="text/javascript">
            	var endYear = document.getElementById("endYear");
				var opt2 = null;
				for(var i = 2013; i <=2019; i++)
				{
					opt2 = document.createElement("option");
					opt2.value = i;
					opt2.innerHTML = i;
					endYear.appendChild(opt2);
				}
            </script>
            <br>
            选择作者：
            <select id ="level" name="level" class="level" onclick="authorLevel()">
                <option value=0>全部</option>
            	<option value=1>第一作者</option>
            	<option value=2>通讯作者</option>
            </select>
            作者：
            <input type="text" class="author" name="author" id="author"/>
            <br>
<%--            <input type="submit" value="查询" class="Find">--%>
            <input type="submit" value="下载" class="Download" id="Download" onclick="download()">
        </form>
    </div>
    <div class="Count" id="Count">
        <p>总共<b>x</b>条</p>
    </div>
</body>
</html>