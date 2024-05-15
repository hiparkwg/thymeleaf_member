$(function(){
    // 자동 실행
    $.ajax({
        url  : "/list",
        type : "GET",
        data : { "findStr" : ""},
        success : (resp) =>{
            let temp = $(resp).find('.change');
            console.log('search', temp)
            $('.change').html(temp);
            search();
        }
    })

      
});


function search(){
    let btnSearch = document.querySelector(".btnSearch")
    btnSearch.addEventListener("click", ()=>{
        let findStr = $('.findStr').val();
        $.ajax({
            url  : "/search",
            type : "GET",
            data : { "findStr" : findStr},
            success : (resp) =>{
                let temp = $(resp).find('.items');
                console.log('search', temp)
                $('.items').html(temp);
            }
        })
        
    })
}


let view = (id)=>{
    let param = "id=" + id;
    $('.change').load("/view", param, ()=>{
        
        let btnModify = document.querySelector(".btnModify")
        let btnDelete = document.querySelector(".btnDelete")
        let btnList = document.querySelector(".btnList")

        btnList.addEventListener("click", ()=>{
            console.log("btnList...")
            $.ajax({
                url  : "/list",
                type : "GET",
                data : { "findStr" : id},
                success : (resp) =>{
                    let temp = $(resp).find(".change");
                    console.log('temp', temp.html());
                    $('.change').html(temp);
                }
            })
        })

        btnModify.addEventListener('click', ()=>{
            $.ajax({
                url  : "/modify",
                type : "GET",
                data : { "id" : id},
                success : (resp) =>{
                    let temp = $(resp).find(".update");
                    console.log(temp)
                    $('.change').html(temp);
                }
            })
        })
    
    });

 
}