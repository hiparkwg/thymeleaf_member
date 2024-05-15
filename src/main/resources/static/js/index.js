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

let registerR=()=>{
    let frm = document.frm;
    let frmData = $(frm).serialize();
    alert(frmData)
    $.ajax({
        url  : "/registerR",
        type : "POST",
        data : frm,
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
                console.log('search', temp)
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
    let param = "id=" + id;
    $('.change').load("/view", param, ()=>{
        
        let btnModify = document.querySelector(".btnModify")
        let btnDelete = document.querySelector(".btnDelete")
        let btnList = document.querySelector(".btnList")

        btnList.addEventListener("click", ()=>{
            console.log("btnList...")
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
        })
    
    });
}

let modify=(id)=>{
    btnUpdate = document.querySelector('.btnUpdate');
    btnList = document.querySelector('.btnList');

    btnList.addEventListener('click', ()=>{
        list(id);
    });
}