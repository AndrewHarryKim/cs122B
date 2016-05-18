<script type="text/javascript" src="js/jquery-1.12.3.js"></script>
	<script type="text/javascript" >
	function autoComplete(){
		var keyWord = 'key=' +  $('#searchBox').val();
		$.ajax({
			url: 'SearchBox',
			data: keyWord,
			success: function(data){ 
				$('#searchDiv').html(data);
			}
		});
		$('#searchDiv').mouseleave(function (){
			$('#searchDiv').html("");
		});
	}

	$(document).ready(function (){
		$('#submitKeyWords').click(autoComplete);
		$('#searchBox').keyup(autoComplete);
		$('#searchBox').mousedown(autoComplete);
	});
</script>
<input type="text" id="searchBox" placeholder="quick-search"/>
<br/>
<div id="searchDiv"></div>
