var starname = document.getElementById("nameSearchBox");
var title = document.getElementById("titleSearchBox");
var director = document.getElementById("directorSearchBox");
var year = document.getElementById("yearSearchBox");
function submitSearch(){
	var queryString = "";
	if(name != null)
		if(name.value != "")
			queryString += "starname=" + starname.value + "&";
	if(title != null)
		if(title.value != "")
			queryString += "title=" + title.value + "&";
	if(director != null)
		if(director.value != "")
			queryString += "director=" + director.value + "&";
	if(year != null)
		if(year.value != "")
			queryString += "year=" + year.value;
	document.location.search = queryString
	
};