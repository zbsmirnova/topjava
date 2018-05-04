function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).attr("id"));
    });

    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function updateRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
            successNoty("Deleted");
        }
        type: "DELETE"
    }).done(function () {
        updateTable();
        successNoty("Deleted");
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}