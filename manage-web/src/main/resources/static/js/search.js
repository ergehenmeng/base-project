$(".dropBtn").on("click",function(event){
    var $showAdw = $(".showAdw");
	if($showAdw.is(":hidden")){
        $showAdw.show();
	}else{
        $showAdw.hide();
	}
});
$(".close").on("click",function(){
	$(".showAdw").hide();
});
