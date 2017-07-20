$(function() {
	renderHeader(Router());
	window.onhashchange = function () {
		renderPage(Router());
	};
})/*.ajaxError(function (event, jqXHR) {
	if (jqXHR.responseJSON && jqXHR.responseJSON.status == 401) {
		//Тут ловишь ajax error'ы централизовано
	}
    debugger;
    if (jqXHR.responseJSON && jqXHR.responseJSON.status == 403) {
		debugger;
        location.hash="/";
    }
});*/

function Router() {
	var router = {};
	router["args"] = {};
	var locationHash = location.hash;
	if(locationHash != "") {
		var pathAndArgs = locationHash.split('?');

		var path = pathAndArgs[0].split('/');
		path[0] = path[0].slice(1);
		for (var i = 0; i<path.length; i++){
			if(path[i] == ""){
				path.splice(i, 1);
				i-=1;
			}
		}
		router["path"] = path;
		if(pathAndArgs[1]){
			var args = pathAndArgs[1].split('&');
			args.forEach(function (arg) {
				var singleArg = arg.split('=');
				router["args"][singleArg[0]]=singleArg[1];
			});
		}
	} else {
		router["path"] = ["login"];
	}
	return router;
}
function renderHeader(router) {
	renderPage(router);
	initIndex();
}

function renderPage(router) {
        renderTpl(router.path[0],router.args.id)
}
function renderTpl(tplName, elementID, callback) {
	if(tplName!="login") {
		$.getJSON('/api/' + tplName + (elementID != null ? ('/' + elementID) : ''), function (model) {
			model = JSOG.decode(model);
			$.ajax({
				url: '/tpl/' + tplName + '.dust',
				success: function (data) {
					dust.renderSource(data, model, function (err, out) {
						$('#page').empty();
						$('#page').append(out);
						if (callback) {
							callback();
						}
					});
					$("form").submit(function (event) {
						console.log("Handler for .submit() called.");
						event.preventDefault();
						submitForm(tplName, elementID, false);
					});
				},
				cache: false
			});
			$.getScript('/js/' + tplName + '.js', function () {
				initForm(tplName, elementID);
			});
		}).error(function(data) {
			if (data.status==403) {
            location.hash="";
        }});
	} else{
		$.ajax({
			url: '/tpl/' + tplName + '.dust',
			success: function (data) {
				dust.renderSource(data, null, function (err, out) {
					$('#page').empty();
					$('#page').append(out);
					if (callback) {
						callback();
					}
				});
				$("form").submit(function (event) {
					console.log("Handler for .submit() called.");
					event.preventDefault();
					submitForm(tplName, null, false);
				});
			},
			cache: false
		});
		$.getScript('/js/' + tplName + '.js', function () {
			initForm(tplName, elementID);
		});
	}

};