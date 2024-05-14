
let btnSearch = document.querySelector(".btnSearch")
btnSearch.addEventListener("click", ()=>{
    let findStr = $('.findStr').val();
    console.log('search', findStr);

    $('.items').load("/search", "findStr="+findStr);

    /*
    $.ajax({
        url : "/search",
        type :"GET",
        data : { "findStr" : findStr},
        success : (data) =>{
            console.log(data);
            $('.items').html(data);
        }
    })
    */
})

let view = (id)=>{
    let param = "id=" + id;
    $('#index').load("/view", param);
}