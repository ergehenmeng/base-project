$(".drop-btn").on("click", function (event) {
    let $showAdw = $(".show-adw");
    if ($showAdw.is(":hidden")) {
        $showAdw.show();
    } else {
        $showAdw.hide();
    }
});
$(".close").on("click", function () {
    $(".show-adw").hide();
});
