$(function() {
	var $foo = $("span"); 
	console.log($foo);
	var bar = document.getElementsByTagName("span");
	console.log(bar);
	$foo.on("click",function(){
		alert($(this).text());
	});

	for (var i = 0; i < bar.length; i++) {
		bar[i].addEventListener("click",function() {
			alert(this.innerHTML);
		});
	}
		
	
});