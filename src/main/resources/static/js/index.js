$(function(){
    // 자동 실행
    list();
});

function list(){
    let findStr="";
    if(sessionStorage.getItem("findStr") != null){
        findStr = sessionStorage.getItem("findStr");
    }

    $.ajax({
        url  : "/search",
        type : "GET",
        data : { "findStr" : findStr},
        success : (resp) =>{
            let temp = $(resp).find(".change");
            $('.change').html(temp);
            search();

        }
    })
}


function search(){
    let btnRegister = document.querySelector(".btnRegister")
    let btnSearch = document.querySelector(".btnSearch")
    let findStr = sessionStorage.getItem("findStr");
    if(findStr != null){
        $(".findStr").val(findStr);
    }
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
        sessionStorage.setItem("findStr", findStr);
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

let fileChange=(tag)=>{
    let repre=document.querySelector(".repre");
    repre.innerHTML = '';//하위태그 모두 삭제
    let legend = document.createElement("legend");
    legend.textContent="대표이미지를 선택해 주세요";
    repre.appendChild(legend);

    for(f of tag.files){
        console.log(f.name)
        let chkbox = document.createElement("input");
        let label = document.createElement("label");
        let br = document.createElement("br");

        chkbox.type="radio";
        chkbox.name="photo";
        chkbox.value = f.name;

        label.textContent=f.name;
        label.prepend(chkbox);

        repre.appendChild(label);
        repre.appendChild(br)
    }

}

let repreImage="";
let change = (tag, photo)=>{
    let allImg = document.querySelectorAll(".photo_list img");
    allImg.forEach( (img)=>{
        img.style.border="5px solid #444";
    })
    tag.style.border="5px solid #aaa";
    repreImage = photo;
}

let view = (id)=>{
    $.ajax({
        url : "/view",
        type : "GET",
        data : {"id" : id},
        success : (resp)=>{
            let temp = $(resp).find('.view');

            $('.change').html(temp);

            let btnModify = document.querySelector(".btnModify")
            let btnDelete = document.querySelector(".btnDelete")
            let btnChangePhoto = document.querySelector(".btnChangePhoto")
            let btnList = document.querySelector(".btnList")
    
            btnList.addEventListener("click", ()=>{
                list(id);
            })

            // 선택된 대표 이미지 정보만 수정
            btnChangePhoto.addEventListener('click', ()=>{
                $.ajax({
                    url     : "/changePhoto",
                    type    : "GET",
                    data    : {"id" : id , "photo" : repreImage},
                    success : (resp)=>{
                        alert(resp);
                    }
                })
            });

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
    btnModifyR = document.querySelector('.btnModifyR');
    btnList = document.querySelector('.btnList');

    btnList.addEventListener('click', ()=>{
        list();
    });

    btnModifyR.addEventListener('click', ()=>{
        let frm = document.frm;

        let frmData = new FormData(frm);
        $.ajax({
            url  : "/modifyR",
            type : "POST",
            contentType : false,
            processData : false,
            data : frmData,
            success : (resp) =>{
            list();
            }
        })
    })
}


/* 파일 삭제 체크 박스가 체크 되었을 때 */
function checkUp(box){
    let p = box.parentNode;
    if(box.checked){
        p.style.textDecoration="line-through"
        p.style.color="#f00"
    }else{
        p.style.textDecoration="none"
        p.style.color=""
    }
}