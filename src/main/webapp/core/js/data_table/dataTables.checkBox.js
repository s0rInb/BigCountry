(function($, window, document) {

   var DT_Checkboxes = function ( oDTSettings ){
       oDTSettings.aoInitComplete.push( {
           "fn": function ( oDTSettings ) {
               if( oDTSettings.oInit.actions != undefined ){
                   var rows_selected = [];
                   var table =  this;
                   table.on('click', 'tbody .dt-body-center input[type="checkbox"]',table, function(e){
                        var tableIn = e.data;
                        for (i in oDTSettings.oInit.actions){
                            var div = '<div class="action-main-div"><div class="action-container">'+
                                      '<div class="actions">'+
                                      '<span style="padding-right: 10px;" class="glyphicon glyphicon-trash '+
                                      oDTSettings.oInit.actions[i].name+'" aria-hidden="true">'+
                                      oDTSettings.oInit.actions[i].name+'</span></div></div></div>';
                        }
                        var $row = $(this).closest('tr');
                        // Get row data
                        var data = tableIn.api().row($row).data();
                        // Get row ID
                        var rowId = data.id;
                        // Determine whether row ID is in the list of selected row IDs
                        var index = $.inArray(rowId, rows_selected);
                        // If checkbox is checked and row ID is not in list of selected row IDs
                        if(this.checked && index === -1){
                           rows_selected.push(rowId);
                        // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
                        } else if (!this.checked && index !== -1){
                           rows_selected.splice(index, 1);
                        }
                        if (rows_selected.length>1){
                             $('#'+tableIn.api().table().node().id+' input[name="select_all"]').closest('tr .dt-body-center').append(div);
                             $('#'+tableIn.api().table().node().id+' tbody .dt-body-center input[type="checkbox"]').closest('tr .dt-body-center').find('.action-main-div').remove();
                        } else {
                             $('#'+tableIn.api().table().node().id+' input[name="select_all"]').closest('tr .dt-body-center').find('.action-main-div').remove();
                        }
                        if(this.checked && rows_selected.length==1 ){
                           $(this).closest('tr .dt-body-center').append(div);
                        } else {
                           $(this).closest('tr .dt-body-center').find('.action-main-div').remove();
                        }
                        if(rows_selected.length==1 ){
                           $('#'+tableIn.api().table().node().id+' tbody .dt-body-center input[type="checkbox"]:checked').closest('tr .dt-body-center').append(div)
                        }

                        for (i in oDTSettings.oInit.actions){
                            $('.'+oDTSettings.oInit.actions[i].name).on('click',function(data){
                                oDTSettings.oInit.actions[i].action.call(this, rows_selected);
                            })
                        }
                        // Update state of "Select all" control
                        updateDataTableSelectAllCtrl(tableIn);
                        // Prevent click event from propagating to parent
                        e.stopPropagation();
                   });

                   // Handle click on table cells with checkboxes
                   /*table.on('click', 'tbody td, thead th:first-child', function(e){
                       $(this).parent().find('input[type="checkbox"]').trigger('click');
                   });*/

                   // Handle click on "Select all" control
                   table.on('click','thead input[name="select_all"]',table, function(e){
                        var tableIn = e.data
                        if(this.checked){
                           $('#'+tableIn.api().table().node().id+' tbody .dt-body-center input[type="checkbox"]:not(:checked)').trigger('click')
                        } else {
                           $('#'+tableIn.api().table().node().id+' tbody .dt-body-center input[type="checkbox"]:checked').trigger('click')
                        // Prevent click event from propagating to parent
                        }
                        e.stopPropagation();
                   });

                   // Handle table draw event
                   table.on('draw.dt',table, function(e){
                        // Update state of "Select all" control
                        var tableIn = e.data;
                        $('#'+tableIn.api().table().node().id+' thead input[name="select_all"]').prop('checked',false);
                        $('#'+tableIn.api().table().node().id+' thead input[name="select_all"]').prop('indeterminate', false);
                        $('#'+tableIn.api().table().node().id+' input[name="select_all"]').closest('tr .dt-body-center').find('.action-main-div').remove();
                        rows_selected = [];
                        updateDataTableSelectAllCtrl(tableIn);
                   });

               }
           }
       } );
   };

    if ( typeof $.fn.dataTable == "function" && typeof $.fn.dataTableExt.fnVersionCheck == "function" && $.fn.dataTableExt.fnVersionCheck('1.8.0') ){
        $.fn.dataTableExt.aoFeatures.push( {
            "fnInit": function( oDTSettings ) {
                new DT_Checkboxes( oDTSettings );
            },
            "cFeature": "X",
            "sFeature": "DT_Checkboxes"
        } );
    }
    else{
        alert( "Warning: DT_Checkboxes requires DataTables 1.8.0 or greater - www.datatables.net/download");
    }
})(jQuery, window, document);

//
// Updates "Select all" control in a data table
//
function updateDataTableSelectAllCtrl(table){
   var $table             = table.api().table().node();
   var $chkbox_all        = $('tbody .dt-body-center input[type="checkbox"]', $table);
   var $chkbox_checked    = $('tbody .dt-body-center input[type="checkbox"]:checked', $table);
   var chkbox_select_all  = $('thead input[name="select_all"]', $table).get(0);

   // If none of the checkboxes are checked
   if($chkbox_checked.length === 0){
      chkbox_select_all.checked = false;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If all of the checkboxes are checked
   } else if ($chkbox_checked.length === $chkbox_all.length){
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If some of the checkboxes are checked
   } else {
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = true;
      }
   }
}



