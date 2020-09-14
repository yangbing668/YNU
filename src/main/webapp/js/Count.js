function check() {
    var Count = document.getElementById("Count");
    Count.style.display="block";
}
function download() {
	
}
function choose(){
	
}
function authorLevel(){
	
}


function checkAuthor() { 
	if(myform.level.checked){ 
		document.getElementById("author").disabled=true; 
		} else{ 
			document.getElementById("author").disabled=false; 

		} 
	} 

function checkAuthor1() { 
	if(myform.level.checked){ 
		document.getElementById("author").disabled=false; 
		} else{ 
			document.getElementById("author").disabled=true; 

		} 
	} 


function StartYear(lastYear){
	var startYear = document.getElementById("startYear");
	startYear.innerHTML = "2016"
	var opt = null;
	for(var i = 2013; i <=lastYear; i++)
	{
		opt = document.createElement("option");
		opt.value = i;
		opt.innerHTML = i;
		startYear.appendChild(opt);
	}
}
function EndYear(lastYear){
	var endYear = document.getElementById("endYear");
	endYear.innerHTML = "2020"
	var opt = null;
	for(var i = 2013; i <=lastYear; i++)
	{
		opt = document.createElement("option");
		opt.value = i;
		opt.innerHTML = i;
		endYear.appendChild(opt);
	}
}

function changeType(type) {
	var now_date = new Date();
	var tYear = now_date.getFullYear();
	if (type == "SCI") {
		StartYear(tYear)
		EndYear(tYear)
	} else {
		StartYear(tYear + 1)
		EndYear(tYear + 1)
	}


	$("#important").hide()
	$("#all").hide()
}
changeType($("#typeSelector").val())

var hulla = new hullabaloo();

function chooseStartYear(startYear) {
	var endYear = $("#endYear").children("option:selected").val()
	
	if (typeof(startYear) != "undefined" && typeof(endYear) != "undefined") {
		// 都不为空，要判断
		if (parseInt(startYear) > parseInt(endYear)) {
        	hulla.send("开始年份不能大于结束年份", "danger");
        	document.getElementById("startYear").value="2013"; 
		}
	}	
}

function chooseEndYear(endYear) {
	var startYear = $("#startYear").children("option:selected").val()

	if (typeof(startYear) != "undefined" && typeof(endYear) != "undefined") {
		// 都不为空，要判断
		if (parseInt(startYear) > parseInt(endYear)) {
        	hulla.send("开始年份不能大于结束年份", "danger");
		}
	}	
}

function checkeAndSubmit() {
	var authorName = $("#author-name").val();
	var regex =  /^[a-zA-Z|',']*$/g
	if (!regex.test(authorName)) {
		hulla.send("作者名格式不正确", "danger")
	}
}