function initForm(entityClass, entityId){
    $.get("/api/notReadPatient", function(data){
        data.data.forEach(function (id) {
            $("#row-"+id).css("background","#a9dfbf");
        })
    });
    $('.table-adaptive').DataTable({
        "paging": false,
        "info": false,
        "scrollX": true,
        "language": {
            "search": "Найти",
            "zeroRecords":    "Нет совподающих записей, измените условия поиска",
            "buttons": {
                "search": "Click to copy"
            }
        }
    });
}