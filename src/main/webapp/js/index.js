$.fn.select2.defaults.set( "theme", "bootstrap" );

function initIndex(){
    $('#logOut').click(function () {
        $.get('api/logout');
        window.location.hash = "";
    });

    $(".nav a").on("click", function(){
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
    });
}

function submitForm(entityClass, entityId, getDisabled) {
	var formData = form2js(entityClass+'-'+(entityId!=null?entityId:''), '.', true, processSelect2MultiSelect, false, getDisabled);
	var json = JSON.stringify(formData, null, '\t');
	var submittedResponse;
	$.ajax({
		async: false,
		type: "post",
		url: "/api/" + entityClass + "Update",
		dataType: 'json',
		data: json,
		contentType: 'application/json',
		mimeType: 'application/json',
		success: function (data, status, jqXHR) {
			if(window.location.hash!="login"){
			// generalSettings.jQuery.defaultAjaxSuccessHandler(data, status, jqXHR);
			$('#form-' + entityClass + entityId).trigger("submitted");
			if(!(data.entityClass == 'userSession' && data.entity.id == undefined)) {
                window.location.hash = data.entityClass + "?id=" + data.entity.id;
                submittedResponse = data;
            } else {
                window.location.hash = "patients";
			}
			// if (entityClass === "task")
			// 	getCountOverdueTasksForStaff();
		}
		}
	});
	return submittedResponse;

}
function processSelect2MultiSelect(node) {
	if ($(node).length > 0 && $(node).data('select2')) {
		if ($(node).val() && Array.isArray($(node).val())) {
			if ($(node).attr('name')) {
				var dataName = $(node).attr('name').split('.');
				if (dataName.length < 2) {
					console.error("Invalid format of '" + $(node).attr('name') + "'. Must be [fieldName].[valueName]. Will be used [fieldName].id");
				}
				var result = [];
				$(node).val().forEach(function (id) {
					var elements = {};
					elements[dataName[1] ? dataName[1] : "id"] = id;
					result.push(elements);
				});
				return {name: dataName[0], value: result};
			} else {
				console.error("There is no attr 'name' in element: " + $(node).attr("id"));
				return false;
			}
		}
	}
	return false;
}
function returnSelect2options(url, placeholder) {
	return {
		placeholder: placeholder ? {
			id: '-1',
			text: placeholder
		} : "",
		allowClear: true,
		ajax: {
			url: url,
			dataType: 'json',
			delay: 250,
			data: function (params) {
				return {
					q: params.term,
					page: params.page
				};
			},
			processResults: function (data) {
				return {
					results: JSOG.decode(data.data)
				};
			},
			cache: true
		},
		templateResult: function formatDataSelection(data) {
			return (data.surname ? data.surname + " " : "")
				+ (data.name ? data.name + " " : "")
				+ (data.patronymic ? data.patronymic : "" );
		},
		templateSelection: function formatData(data) {
			return (data.surname ? data.surname + " " : "")
				+ (data.name ? data.name + " " : "")
				+ (data.patronymic ? data.patronymic : "" );
		},
		escapeMarkup: function (markup) {
			return markup;
		},
		minimumInputLength: 0
	};
}

function returnDictionarySelect2Options(url, placeholder, fnProcessResults) {
	return {
		placeholder: placeholder ? {
			id: '-1',
			text: placeholder
		} : "",
		allowClear: true,
		ajax: {
			url: url,
			dataType: 'json',
			delay: 250,
			data: function (params) {
				return {
					q: params.term,
				};
			},
			processResults: fnProcessResults ? fnProcessResults : function (data) {
				return {
					results: JSOG.decode(data.data)
				};
			},
			cache: true
		},
		templateResult: function formatDataSelection(data) {
			return data.name;
		},
		templateSelection: function formatData(data) {
			return data.name;
		},
		escapeMarkup: function (markup) {
			return markup;
		},
		minimumInputLength: 0
	};
}