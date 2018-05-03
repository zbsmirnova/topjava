function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    debugger;
    $("#detailsForm").find(":input").val("");
    debugger;
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
            successNoty("Deleted");
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}



function save() {
    var form = $("#detailsForm");
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
    });
}

function checkboxUser(id, enabled) {
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: {enabled:enabled.value}
    });
    debugger;

}
function saveMeal(startDate, startTime, endDate, endTime) {
    var form = $("#detailsForm");
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrlMeal,
        data: form.serialize(),
        success: function () {
            debugger;
            $("#editRow").modal('hide');
            //updateMealsTable();
            successNoty("Meal Saved");
            filter(startDate, startTime, endDate, endTime)
        }
    });
}
function updateMealsTable() {
    debugger;
    $.get(ajaxUrlMeal, function (data) {
        datatableApiMeal.clear().rows.add(data).draw();
    });
}



    function deleteRowMeal(id) {
        debugger;
        $.ajax({
            url: ajaxUrlMeal + id,
            type: "DELETE",
            success: function () {
                debugger;
                updateMealsTable();
                successNoty("Deleted");
            }
        });
    }

    function filter(startDate, startTime, endDate, endTime) {
     var dateTimeArray = {startDate:startDate.value, startTime:startTime.value, endDate:endDate.value, endTime:endTime.value}
        debugger;
        filterUtil(dateTimeArray);
        successNoty("Filtered");
        }

    function clearFilter() {
        debugger;
        filterUtil();
        successNoty("Filter cleared");
    }

    function filterUtil(dateArray) {
    debugger;
        $.ajax({
            type: "GET",
            url: ajaxUrlMeal + "filter",
            data: dateArray,
            success: function (data) {
                $("#editRow").modal('hide');
                debugger;
                datatableApiMeal.clear().rows.add(data).draw();
            }
        });

    }

    var failedNote;

    function closeNoty() {
        if (failedNote) {
            failedNote.close();
            failedNote = undefined;
        }
    }

    function successNoty(text) {
        closeNoty();
        new Noty({
            text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
            type: 'success',
            layout: "bottomRight",
            timeout: 1000
        }).show();
    }

    function failNoty(jqXHR) {
        closeNoty();
        failedNote = new Noty({
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
            type: "error",
            layout: "bottomRight"
        }).show();
    }

    var failedNote;

    function closeNoty() {
        if (failedNote) {
            failedNote.close();
            failedNote = undefined;
        }
    }

    function successNoty(text) {
        closeNoty();
        new Noty({
            text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
            type: 'success',
            layout: "bottomRight",
            timeout: 1000
        }).show();
    }

    function failNoty(jqXHR) {
        closeNoty();
        failedNote = new Noty({
            text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
            type: "error",
            layout: "bottomRight"
        }).show();
    }
