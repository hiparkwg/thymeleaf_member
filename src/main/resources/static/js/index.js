$(function(){
    // 자동 실행
    $.ajax({
        url  : "/list",
        type : "GET",
        data : { "findStr" : ""},
        success : (resp) =>{
            let temp = $(resp).find('.change');
            $('.change').html(temp);
            search();
        }
    })
});

let registerR=()=>{
    let frm = document.frm;

    let frmData = new FormData(frm);
    $.ajax({
        url  : "/registerR",
        type : "POST",
        contentType : false,
        processData : false,
        data : frmData,
        success : (resp) =>{
           list();
        }
    })
}

function register(){
    
    let btnRegisterR = document.querySelector('.btnRegisterR');
    let btnList = document.querySelector('.btnList');

    btnRegisterR.addEventListener('click', ()=>{
        registerR();
    });
    btnList.addEventListener('click', ()=>{
        list();
    });

}

function search(){
    let btnRegister = document.querySelector(".btnRegister")
    let btnSearch = document.querySelector(".btnSearch")

    btnRegister.addEventListener('click', ()=>{
        $.ajax({
            url  : "/register",
            type : "GET",
            success : (resp) =>{
                let temp = $(resp).find('.register');
                $('.change').html(temp);
                register();
            }
        })
    })


    btnSearch.addEventListener("click", ()=>{
        let findStr = $('.findStr').val();
        $.ajax({
            url  : "/search",
            type : "GET",
            data : { "findStr" : findStr},
            success : (resp) =>{
                let temp = $(resp).find('.items');
                $('.items').html(temp);
            }
        })
        
    })
}

function list(findStr){
    $.ajax({
        url  : "/list",
        type : "GET",
        data : { "findStr" : findStr},
        success : (resp) =>{
            let temp = $(resp).find(".change");
            $('.change').html(temp);
            search();
        }
    })
}

let view = (id)=>{
    let cnt=0
    console.log("view..."+ (cnt++))
    let param = "id=" + id;

    $.ajax({
        url : "/view",
        type : "GET",
        data : {"id" : id},
        success : (resp)=>{
            let temp = $(resp).find('.view');
            $('.change').html(temp);

            let btnModify = document.querySelector(".btnModify")
            let btnDelete = document.querySelector(".btnDelete")
            let btnList = document.querySelector(".btnList")
    
            btnList.addEventListener("click", ()=>{
                list(id);
            })
    
            btnModify.addEventListener('click', ()=>{
                $.ajax({
                    url  : "/modify",
                    type : "GET",
                    data : { "id" : id},
                    success : (resp) =>{
                        let temp = $(resp).find(".update");
                        $('.change').html(temp);
                        modify(id);
                    }
                })
            })
    
            btnDelete.addEventListener('click', ()=>{
                console.log('delete')
                let yn=confirm('정말?');
                if( !yn ) return;
    
                $.ajax({
                    url  : "/deleteR",
                    type : "GET",
                    data : { "id" : id},
                    success : (resp) =>{
                        list("")
                        setTimeout(()=>{
                            alert(resp)
                        }  , 200);
                    }
                })
            })
    
        }

    });

}

let modify=(id)=>{
    btnUpdate = document.querySelector('.btnUpdate');
    btnList = document.querySelector('.btnList');

    btnList.addEventListener('click', ()=>{
        list(id);
    });
}