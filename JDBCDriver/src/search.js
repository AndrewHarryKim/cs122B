var input = document.getElementById("dbSearchBox");

function submitSearch(){
	document.location.search = "search="+input.value;
};
