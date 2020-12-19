getItems(false);
function getItems(onlyUndone) {
    let req = JSON.stringify({
        "action" : onlyUndone ? "GETUNDONE" : "GETALL"
    });
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/chapter_003_war/tasks.do',
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
                    let evalStr = '<tr><td>'
                        + items[i].id
                        + '</td><td>'
                        + items[i].descr
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
        url: 'http://localhost:8080/chapter_003_war/tasks.do',
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
    let description = $('#description').val();
    if (description.length > 0) {
        let item = {
            "descr" : description,
            "done" : false
        };
        let req = JSON.stringify({
            "action" : "ADD",
            "items" : [item]
        });
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/chapter_003_war/tasks.do',
            data: req,
            dataType: 'json'
        }).done(function(data) {
            if (data['status'] == 'OK') {
                getItems();
            }
        }).fail(function(err){
            console.log(err);
        });
    } else {
        alert("Описание пусто!");
    }
}