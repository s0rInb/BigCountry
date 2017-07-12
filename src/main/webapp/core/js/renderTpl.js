//TODO deprecated, need use dustHelper
function renderTpl(tplName,model,elementID,callback){
    $.ajax({
       url: '/static/tpl/'+tplName+'.dust',
       success: function(data){
            dust.renderSource(data, model, function(err, out) {
                $('#'+elementID).empty();
                $('#'+elementID).append(out);
                if(callback){
                    callback();
                }
            });
       },
    cache: false
    });
};
//TODO deprecated, need use dustHelper
function renderTplToTop(tplName,model,elementID,callback){
    $.ajax({
       url: '/static/tpl/'+tplName+'.dust',
       success: function(data){
            dust.renderSource(data, model, function(err, out) {
                $('#'+elementID).prepend(out);
                if(callback){
                    callback();
                }
            });
       },
       cache: false
    });
};
//TODO deprecated, need use dustHelper
function renderTplToBottom(tplName,model,elementID,callback){
    $.ajax({
       url: '/static/tpl/'+tplName+'.dust',
       success: function(data){
            dust.renderSource(data, model, function(err, out) {
                $('#'+elementID).append(out);
                if(callback){
                    callback();
                }
            });
       },
       cache: false
    });
};

var dustHelper = (function(){
        var templates = {};
        return {
            renderTpl: renderTpl,
            renderTplToTop: renderTplToTop,
            renderTplToBottom: renderTplToBottom
        };
        function render(tplName, model, elementID, callback, isAsync, force , action){
            if(isAsync == "undefined"){
                isAsync = true;
            }
            if($('#'+elementID).length < 1){
                console.error("Element with id '"+elementID+"' not found!");
                return;
            }
            if(templates[tplName] && !force) {
                renderWithAction(action);
            } else {
                $.ajax({
                    url: '/static/tpl/'+tplName+'.dust',
                    async: isAsync,
                    success: function(data){
                         if(!templates[tplName]) {
                             templates[tplName] = data;
                         }
                         renderWithAction(action);
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                         console.error("Template '"+tplName+"' load failed!");
                    },
                    cache: false
                });
            };

            function renderWithAction(action){
                dust.renderSource(templates[tplName], model, function(err, out) {
                                                    if(action){
                                                        action(out);
                                                    }
                                                    if(callback){
                                                        callback();
                                                    }
                                                });
            };
        };
        function renderTpl(tplName, model, elementID, callback, isAsync, force){
            render(tplName, model, elementID, callback, isAsync, force , function(out){
                                                                    $('#'+elementID).empty();
                                                                    $('#'+elementID).append(out);});
        };

        function renderTplToTop(tplName, model, elementID, callback, isAsync, force){
            render(tplName, model, elementID, callback, isAsync, force , function(out){
                                                                    $('#'+elementID).prepend(out);});
        };
         function renderTplToBottom(tplName, model, elementID, callback, isAsync, force){
            render(tplName, model, elementID, callback, isAsync, force , function(out){
                                                                    $('#'+elementID).append(out);;});

         };
}());

