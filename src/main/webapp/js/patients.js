function initForm(entityClass, entityId){
    $.get("/api/notReadPatient", function(data){
        data.data.forEach(function (id) {
            $("#row-"+id).css("background","#a9dfbf");
        })
    });
}