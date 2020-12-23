let filterVAl = false;
getCategories();
getItems(filterVAl);

function getCategories() {
    let req = JSON.stringify({
        "action" : "GETCATS"
    });
    $.ajax({
        type: 'POST',
        url: document.URL,
        data: req,
        dataType: 'json'
    }).done(function(data) {
        if (data['status'] == 'OK') {
            let categories = data['categories'];
            if (categories.length > 0) {
                for (let i = 0; i < categories.length; i++) {
                    $('#categoriesSelect').append(`<option value="${categories[i].id}">${categories[i].name}</option>>`);
                }
            }
        }
    }).fail(function(err){
        console.log(err);
    });
}



function getItems(onlyUndone) {
    filterVAl = onlyUndone;
    let req = JSON.stringify({
        "action" : onlyUndone ? "GETUNDONE" : "GETALL"
    });
    $.ajax({
        type: 'POST',
        url: document.URL,
        data: req,
        dataType: 'json'
    }).done(function(data) {
        $('#itemsList').empty();
        if (data['status'] == 'OK') {
            let items = data['items'];
            if (items.length > 0) {
                for (let i = 0; i < items.length; i++) {
                    let status = '';
                    if (items[i].done) {
                        status = '<h3>Завершено</h3>';
                    }else {
                        status = `<button id=\"${items[i].id}\" onclick=\"return markTask(${items[i].id})\" class=\"btn btn-primary\" type=\"button\">Завершить</button>`;
                    }
                    let cats = '';
                    items[i].categories.forEach(el => cats += el.name + ' | ')
                    let evalStr = '<tr><td>'
                        + items[i].id
                        + '</td><td>'
                        + items[i].descr
                        + '</td><td>'
                        + cats
                        + '</td><td>'
                        + items[i].created
                        + '</td><td>'
                        + status
                        + '</td></tr>';

                    $('#itemsList').append(evalStr);
                }
            }
        }
        if (onlyUndone) {
            $('#filterBtn').removeClass();
            $('#filterBtn').addClass("btn btn-primary");
            $('#filterBtn').html("Незавершенные");
        } else {
            $('#filterBtn').removeClass();
            $('#filterBtn').addClass("btn btn-success");
            $('#filterBtn').html("Все");
        }
        $('#filterBtn').click(function (){
            getItems(!onlyUndone);
        });
    }).fail(function(err){
        console.log(err);
    });
};

function markTask(taskId) {
    let item = {
        "id" : taskId,
        "done" : true
    };
    let req = JSON.stringify({
        "action" : "MARK",
        "items" : [item]
    });
    $.ajax({
        type: 'POST',
        url: document.URL,
        data: req,
        dataType: 'json'
    }).done(function(data) {
        if (data['status'] == 'OK') {
            $(`#${taskId}`).replaceWith("<h3>Завершено</h3>");
        }
    }).fail(function(err){
        console.log(err);
    });
};

function createTask(){
    alert('start task creation');
    let description = $('#description').val();
    let cats = $('#categoriesSelect').val();
    if (description.length > 0 && cats.length > 0) {
        let cat = [];
        for (let i = 0; i < cats.length; i++) {
            let c = {
                "id" : cats[i]
            }
            cat.push(c);
        }

        let item = {
            "descr" : description,
            "done" : false,
            "categories" : cat
        };
        let req = JSON.stringify({
            "action" : "ADD",
            "items" : [item]
        });
        $.ajax({
            type: 'POST',
            url: document.URL,
            data: req,
            dataType: 'json'
        }).done(function(data) {
            if (data['status'] == 'OK') {
                getItems(filterVAl);
            }
        }).fail(function(err){
            console.log(err);
        });
    } else {
        alert("Описание пусто!");
    }
}