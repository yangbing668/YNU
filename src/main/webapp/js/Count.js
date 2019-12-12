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
function StartYear(){
	var startYear = document.getElementById("startYear");
	var opt = null;
	for(var i = 2013; i <=2019; i++)
	{
		opt = document.createElement("option");
		opt.value = i;
		opt.innerHTML = i;
		startYear.appendChild(opt);
	}
}
function EndYear(){
	var endYear = document.getElementById("endYear");
	var opt = null;
	for(var i = 2013; i <=2019; i++)
	{
		opt = document.createElement("option");
		opt.value = i;
		opt.innerHTML = i;
		endYear.appendChild(opt);
	}
}
