getAnnouncments(1);
let announcement;
let images = {};

function getFullAnnouncment(id) {
    let announcment = {
        "id": id,
    };
    let req = JSON.stringify({
        "action" : "GET_ANNOUNCEMENT",
        "announcements" : [announcment]
    });
    $.ajax({
        type: 'POST',
        url: document.URL,
        data: req,
        dataType: 'json'
    }).done(function(data) {
        console.log(data);
    }).fail(function(data){
        alert( 'Sorry.' );
    });
}


function createAnnouncment() {
    let announcment = {
        "title": $('#title').val(),
        "description": $('#description').val(),
        "price" : Number($('#price').val()),
        "contact" : $('#contact').val()
    };
    if (images.length > 0) {
        announcment["photos"] = images;
    }
    let type = { id : Number($('#carTypeSelect').val()) };
    announcment["type" ] = type;
    announcment["brand" ] = {id : Number($('#carBrandSelect').val()) };
    announcment["model" ] = {id : Number($('#carModelSelect').val()) };
    announcment["carEngine" ] = {id : Number($('#carEngineSelect').val()) };
    announcment["carBody" ] = {id : Number($('#carBodySelect').val()) };
    let req = JSON.stringify({
        "action" : "ADD_ANNOUNCEMENTS",
        "announcements" : [announcment]
    });
    $.ajax({
        type: 'POST',
        url: "http://localhost:8181/cars_sales_war/announcement.do",
        data: req,
        dataType: 'json'
    }).done(function(data) {
        window.location.replace("http://localhost:8181/cars_sales_war/announcement.do");
    }).fail(function(data){
        alert( 'Sorry.' );
    });
}



function uploadPhotos(){
    let files = document.getElementById('file').files;
    if (files.length > 0 && files.length < 3) {
        let fd = new FormData();
        for (let i = 0; i < files.length; i++) {
            fd.append( 'file['+i+']', files[i] );
        }

        $.ajax({
            url: "http://localhost:8181/cars_sales_war/upload.do",
            type: "POST",
            data: fd,
            processData: false,
            contentType: false,
        }).done(function(data) {
            if(data.length > 0) {
                images = data;
                data.forEach((element) => {
                    $('#imgPrevie').append('<td><img src="http://localhost:8181/cars_sales_war/upload.do?id=' + element.id + '&name='+ element.name +'" width="100px" height="100px"/></td>');
                })
            }
        }).fail(function(data){
            alert( 'Sorry.' );
        });
    } else {
        alert("Файлов должно быть не более трёх!");
    }
}


function getAnnouncments(page) {
    let req = JSON.stringify({
        "page" : page,
        "action" : "GET_ANNOUNCEMENTS"
    });
    $.ajax({
        type: 'POST',
        url: "http://localhost:8181/cars_sales_war/announcement.do",
        data: req,
        dataType: 'json'
    }).done(function(data) {
        if (data['status'] == 'OK') {
            let items = data['announcements'];
            if(items.length) {
                for (let i = 0; i < items.length; i++) {
                    let evalStr = '<tr><td>'
                        + items[i].id
                        + '</td><td>'
                        + items[i].title
                        + '</td><td>'
                        + items[i].description
                        + '</td><td>'
                        + items[i].createTime
                        + '</td><td>'
                        + items[i].price
                        + '</td><td>'
                        + '<button onClick="editAnnoucment(' + items[i].id + ')" type="submit" class="btn btn-primary">Подробнее</button>'
                        + '</td></tr>';

                    $('#itemsList').append(evalStr);
                }
            }
        }
    }).fail(function(err) {
        console.log(err);
    });
}

function changeAnnouncment() {
    let announcment = {
        "id": Number($('#id').text()),
        "title": $('#title').val(),
        "description": $('#description').val(),
        "price" : Number($('#price').val()),
        "contact" : $('#contact').val(),
        "status" : $('#announctmentStatus').val(),
    };

    if (images.length > 0) {
        announcment["photos"] = images;
    }
    let type = { id : Number($('#carTypeSelect').val()) };
    announcment["type" ] = type;
    announcment["brand" ] = {id : Number($('#carBrandSelect').val()) };
    announcment["model" ] = {id : Number($('#carModelSelect').val()) };
    announcment["carEngine" ] = {id : Number($('#carEngineSelect').val()) };
    announcment["carBody" ] = {id : Number($('#carBodySelect').val()) };
    let req = JSON.stringify({
        "action" : "EDIT_ANNOUNCEMENTS",
        "announcements" : [announcment]
    });
    $.ajax({
        type: 'POST',
        url: "http://localhost:8181/cars_sales_war/announcement.do",
        data: req,
        dataType: 'json'
    }).done(function(data) {
        window.location.replace("http://localhost:8181/cars_sales_war/announcement.do");
    }).fail(function(data){
        alert( 'Sorry.' );
    });
}


function editAnnoucment(id) {
    window.location.replace("http://localhost:8181/cars_sales_war/addAnnouncement.do?id="+id);
}