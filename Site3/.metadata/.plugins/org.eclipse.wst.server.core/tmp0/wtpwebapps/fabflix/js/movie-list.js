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
			queryString += "year=" + search.value + "&";
		}
	}
	document.location.search = queryString;
	
};
function addToCart(movieid){
	var queryString = document.location.search;
	queryString += "addToCart=" + movieid + "&";
	document.location.search = queryString;
	
};
function updateQueryStringParameter(key, newValue) {
	queryString = document.location.search;
	var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	var separator = queryString.indexOf('?') !== -1 ? "&" : "?";
	if (queryString.match(re)) {
		document.location.search = queryString.replace(re, '$1' + key + "=" + newValue + '$2');
	}
	else {
		document.location.search = queryString + separator + key + "=" + newValue;
	}
}
function incrementQueryStringParameter(key, currentValue) {
	queryString = document.location.search;
	currentValue = parseInt(currentValue)+1;
	var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	var separator = queryString.indexOf('?') !== -1 ? "&" : "?";
	if (queryString.match(re)) {
		document.location.search = queryString.replace(re, '$1' + key + "=" + currentValue + '$2');
	}
	else {
		document.location.search = queryString + separator + key + "=" + currentValue;
	}
}
function decrementQueryStringParameter(key, currentValue) {
	queryString = document.location.search;
	currentValue = parseInt(currentValue)-1;
	var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	var separator = queryString.indexOf('?') !== -1 ? "&" : "?";
	if (queryString.match(re)) {
		document.location.search = queryString.replace(re, '$1' + key + "=" + currentValue + '$2');
	}
	else {
		document.location.search = queryString + separator + key + "=" + currentValue;
	}
}