<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>title</title>

    <meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/floating-labels.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">

  </head>
  <body>

    <form class="form-horizontal form-search" id="myform" name="myform"  action="download" method="post">
      <div class="row form-group">
        <label class="col-sm-2 control-label">选择类别：</label>
        <div class="col-sm-4">
          <select id = "type" name="type" class="form-control type">
            <option value="EI" selected="true">EI</option>
            <option value="SCI">SCI</option>
            <option value="CPCI">CPCI</option>
          </select>
        </div>
      </div>

      <div class="row form-group">
        <label class="col-sm-2 control-label">选择年份：</label>
        <div class="col-sm-4">
          <select id = "startYear" name="startYear" class="Startyear form-control" onchange="chooseStartYear(this.options[this.options.selectedIndex].value)">
            <option>--请选择--</option>
          </select>
        </div>
        <label class="col-sm-2" style="text-align: center;">至</label>
        <div class="col-sm-4">
          <select id = "endYear" name="endYear" class="Endyear form-control" onchange="chooseEndYear(this.options[this.options.selectedIndex].value)">
            <option>--请选择--</option>
          </select>
        </div>
      </div>

      <div class="row form-group">
        <label class="col-sm-2 control-label">选择作者：</label>
        <div class="col-sm-4">
          <select id ="level" name="level" class="form-control author-type">
            <option value=0 onclick="checkAuthor1()">第一或通讯</option>
            <option value=1 onclick="checkAuthor()">第一作者</option>
            <option value=2 onclick="checkAuthor()">通讯作者</option>
            <option value=3 onclick="checkAuthor()">任意作者</option>
          </select>
        </div>
        <label class="col-sm-2" style="text-align: center;">作者：</label>
        <div class="col-sm-4">
          <input type="text" class="form-control" placeholder='请输入作者名' name="author" id="author" disabled="true">
        </div>
      </div>
      
      <div style="display:none;" class="row form-group">
        <div class="col-sm-2"></div>
        <label><input class="blue" name="isImportant" id="isImportant" type="radio" checked>全部期刊</label>
        <label><input class="blue" type="radio" name="isImportant" id="isImportant">重点期刊</label>
      </div>
      
      <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-3">
           <input type="submit" value="下载" class="Download">
        </div>
      </div>
    </form>

  </body>
  
  <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <script src="js/hullabaloo.js"></script>
  <script src="js/Count.js"></script>

</html>