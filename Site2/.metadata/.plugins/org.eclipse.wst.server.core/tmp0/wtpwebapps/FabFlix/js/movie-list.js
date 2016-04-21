/**
 * 
 */
var search = document.getElementById("dbSearchBox");
function submitSearch(){
	var queryString = "";
	if(search != null)
	{
		if(search.value != "")
		{
			queryString += "starname=" + search.value + "&";
			queryString += "title=" + search.value + "&";
			queryString += "director=" + search.value + "&";
			queryString += "year=" + search.value;
		}
	}
	document.location.search = queryString
	
};