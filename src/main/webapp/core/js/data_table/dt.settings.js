(function($, window, document) {

   var DT_Settings = function ( oDTSettings ){
       oDTSettings.aoInitComplete.push( {
           "fn": function ( oDTSettings ) {
               var table =  this;
               var tableId = table.api().table().node().id;
               var sTableWrap = oDTSettings.nTableWrapper;
               $(sTableWrap).prepend('<div class="ColVis"><button type="button" id="'+tableId+'-settings"'+
                                     'class="cm-settings-button" data-toggle="modal" data-target="#'+tableId+'-settings-modal">'+
                                                                      '<i class="icon-dots cm-control-icon"></i>'+
                                                                  '</button></div>');
               var columnList = getColumnList(table);
               $(sTableWrap).prepend('<div class="modal fade" id="'+tableId+'-settings-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'+
                                     '    <div class="modal-dialog" id="cm-'+tableId+'-settings-modal" role="document">'+
                                     '        <div class="modal-content">'+
                                     '            <div class="modal-body" id="'+tableId+'-settings-modal-body">'+
                                     '                <div class="dataTables_length" id="'+tableId+'_length" style="width: 100%;">'+
                                     '                    <label>'+ StrLocalizer.get("table:settings:show_by") + ' '+
                                     '                    <select id="'+tableId+'-select"  name="'+tableId+'_length" aria-controls="table-deals" class="">'+
                                     '                    <option value="10">10</option>'+
                                     '                    <option value="25">25</option>'+
                                     '                    <option value="50">50</option>'+
                                     '                    <option value="100">100</option>'+
				   					 '                    <option value="1000">1000</option>'+
				   					 '                    <option value="2147483647">' + StrLocalizer.get("table:settings:all") + '</option>'+
                                     '                    </select> ' + StrLocalizer.get("table:settings:records") + '</label>'+
                                     '                </div>'+
                                     '                <ul id="'+tableId+'-columns" class="dataTables_columns">'+
                                                            columnList +
                                     '                </ul>'+
                                     '            </div>'+
                                     '            <div class="modal-footer cm-filters-modal-footer">'+
                                     '                <button type="button" class="cm-filter-btn cm-filter-close" data-dismiss="modal">' + StrLocalizer.get("table:settings:cancel") + '</button>'+
                                     '                <button type="button" class="cm-filter-btn cm-filter-submit setting-submit">' + StrLocalizer.get("table:settings:apply") + '</button>'+
                                     '            </div>'+
                                     '        </div>'+
                                     '    </div>'+
                                     '</div>');
               $('#'+tableId+'-columns li').on('click', function(e){
                   if ($(this).hasClass("active")){
                       $(this).removeClass("active");
                       $(this).find("input").prop('checked', false);
                   } else {
                       $(this).addClass("active");
                       $(this).find("input").prop('checked', true);
                   }
               });

               $('.setting-submit').on('click', function(e){
                    $('#'+tableId+'-settings-modal').modal('hide');
                     var column;
                     var columnInputs = $('#'+tableId+'-settings-modal').find('li input').toArray();
                    for (k in columnInputs){
                        column = table.api().column( columnInputs[k].value );
                        column.visible($(columnInputs[k]).prop( "checked" ));
                    }
                    table.api().page.len( $('#'+tableId+'-select').val() ).draw();
               });
           }
       } );
   };

    if ( typeof $.fn.dataTable == "function" && typeof $.fn.dataTableExt.fnVersionCheck == "function" && $.fn.dataTableExt.fnVersionCheck('1.8.0') ){
        $.fn.dataTableExt.aoFeatures.push( {
            "fnInit": function( oDTSettings ) {
                new DT_Settings( oDTSettings );
            },
            "cFeature": "Y",
            "sFeature": "DT_Settings"
        } );
    }
    else{
        alert( "Warning: DT_Settings requires DataTables 1.8.0 or greater - www.datatables.net/download");
    }
})(jQuery, window, document);


function getColumnList(table){
    var headers = table.api().columns().header();
    var result="";
    var liStart = '<li class="';
    var liInput = '"><input value="';
    var liValue = '" ';
    var liCheckBox = ' type="checkbox"><p>';
    var liEnd = '</p></li>';
    for (k in headers){
        if (headers[k].textContent!="" && headers[k].textContent!=null) {
            var columnIndex = $(headers[k]).attr("data-column-index");
            var isVisibleColumn = table.api().column(columnIndex).visible();
            result = result + liStart + (isVisibleColumn?"active":"")+ liInput + columnIndex + liValue + (isVisibleColumn?"checked":"") + liCheckBox + headers[k].textContent + liEnd;
        }
    }
    return result;
}


