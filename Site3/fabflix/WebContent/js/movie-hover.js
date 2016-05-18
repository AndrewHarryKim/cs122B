var hoverOpen = false;


$(document).ready(function (){
	$('a.movieHover').mouseover(function(){
		var id = $(this).attr('movieid');
		var movieID = 'id=' + id;
		
			$.ajax({
				url: 'MovieHover',
				data: movieID,
				success: function(data){ 
					if(!hoverOpen)
					{
						$('div.movieHoverDiv').html("");
						$('div.movieHoverDiv').css("padding","0");
						$('div.movieHoverDiv').css("border","none");
						$('div.movieHoverDiv').css("width","auto");
						$('div.movieHoverDiv').css("box-shadow","none");
					
						$('#movieHoverDiv'+id).html(data);
						$('#movieHoverDiv'+id).css("padding","1%");
						$('#movieHoverDiv'+id).css("border","1px solid black");
						$('#movieHoverDiv'+id).css("width","200px");
						$('#movieHoverDiv'+id).css("box-shadow","-3px 3px 4px rgba(0, 0, 0, .2), 3px 3px 4px rgba(0, 0, 0, .2) ");
						

						hoverOpen = true;
					}
					else
					{
						hoverOpen = false;
					}
				}
			});
			
			$('div.movieInfoDiv, .movieName').mouseleave(function (){
				hoverOpen = false;
				$('#movieHoverDiv'+id).html("");
				$('#movieHoverDiv'+id).css("padding","0");
				$('#movieHoverDiv'+id).css("border","none");
				$('#movieHoverDiv'+id).css("width","auto");
				$('#movieHoverDiv'+id).css("box-shadow","none");
			});
		
	});
});