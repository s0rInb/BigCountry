(function($, window, document) {

    var DT_RefreshButton = function ( oDTSettings ){
        oDTSettings.aoInitComplete.push( {
            "fn": function ( oDTSettings ) {
                var table =  this;
                var sTableWrap = oDTSettings.nTableWrapper;
                $(sTableWrap).prepend('<div class="ColVis"><button class="ColVis_Button ColVis_MasterButton refresh"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button></div>');
                $(sTableWrap).on('click', '.refresh',table, function(e){
                    var tableIn = e.data;
                    tableIn.fnDraw(false);
                });
            }
        } );
    };

    if ( typeof $.fn.dataTable == "function" && typeof $.fn.dataTableExt.fnVersionCheck == "function" && $.fn.dataTableExt.fnVersionCheck('1.8.0') ){
        $.fn.dataTableExt.aoFeatures.push( {
            "fnInit": function( oDTSettings ) {
                new DT_RefreshButton( oDTSettings );
            },
            "cFeature": "W",
            "sFeature": "DT_RefreshButton"
        } );
    }
    else{
        alert( "Warning: RefreshButton requires DataTables 1.8.0 or greater - www.datatables.net/download");
    }
})(jQuery, window, document);