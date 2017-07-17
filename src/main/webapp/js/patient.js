var fileCount = 0;
function initForm(entityClass, entityId) {
	$("#subjectRF").select2(returnSelect2options("/api/subjectRF"));
	$("#diagnosis").select2(returnSelect2options("/api/diagnosis"));
	$("#whoCall").select2(returnSelect2options("/api/whoCall"));
	$("#consultationConsultationType").select2(returnSelect2options("/api/consultationType"));
	$("#consultationWhoSentToConsultation").select2(returnSelect2options("/api/whoSentToCons"));
	$("#legalSupportWhoLegalSupport").select2(returnSelect2options("/api/whoLegalSupport"));
	$("#hotLineCallDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationFullDocumentDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationFullDocumentSendDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationConsultationDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationAdditionalResearch" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationConclusionDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationSendRegionDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#consultationMedicalCommissionDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportAppealMinHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportResultMinHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportAppealRusHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportResultRusHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportAppealProcuratorHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportResultProcuratorHealthDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportAppealCourtDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	$("#legalSupportPlannedCourtDate" ).datepicker({ dateFormat: 'yy-mm-dd'});
	var infoConsent= $("#infoConsent");
	infoConsent.uploadFile({
		url: "/api/uploadFile",
		multiple: false,
		dragDrop: false,
		uploadStr: infoConsent.attr("uploadStr"),
		dynamicFormData: function () {
			var data = {"patientId": entityId, "fileType": "infoConsent", "name": $("#div-file-"+(fileCount-1)).text()}
			return data;
		},
		onSubmit: function (files) {
			$("#files-container").append('<div id="div-file-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
				files[0] + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
			$('#div-file-' + fileCount + " div span").on('click', function () {
				$(this).closest("div").parent("div").remove();
			});
			fileCount++;
		},
		onSuccess: function (files, data, xhr, pd) {
			var divFile = $('#div-file-' + fileCount);
			debugger;
			divFile.empty();
			divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + data.entity.id+'">' +
				data.entity.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
			divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + data.entity.id + '"/>');
			divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + data.entity.name + '"/>');
			$('#div-file-' + fileCount + " div span").on('click', function () {
				$(this).closest("div").parent("div").remove();
			});
		},
		onError: function (files, status, errMsg, pd) {
			$("#file-loading-error").append(' "' + files + '"');
			$("#file-loading-error").show();
		}
	});
	$.ajax({
		url: "/api/fileLinks/"+entityId,
		data: null,
		success: function (data) {
			data.entity.forEach(function(entrya){
				$("#files-container").append('<div id="div-file-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
					files[0] + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
				$('#div-file-' + fileCount + " div span").on('click', function () {
					$(this).closest("div").parent("div").remove();
				});

				var divFile = $('#div-file-' + fileCount);
				divFile.empty();
				divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id='+entrya.id+'">' +
					entrya.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
				divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + entrya.id + '"/>');
				divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + entrya.name + '"/>');
				$('#div-file-' + fileCount + " div span").on('click', function () {
					$(this).closest("div").parent("div").remove();
				});
				fileCount++;
			})
		}
	})
}
function deletePatient(entityId) {
	$.ajax({
		url: "/api/patientDelete/"+entityId,
		data: null,
		success: function () {
			window.location.hash="patients"
		}
		})
}
// $("#corpresumeuploader-").uploadFile({
// 	url: "/api/uploadFile",
// 	multiple: false,
// 	dragDrop: false,
// 	fileName: "file",
// 	uploadStr: $("#corpresumeuploader-").attr("uploadStr"),
// 	dynamicFormData: function () {
// 		var data = {"catalog": "staff", "id": '1', "fileCount":"1"}
// 		return data;
// 	},
// 	// onSubmit: function (files) {
// 	// },
// 	// onSuccess: function (files, data, xhr, pd) {
// 	// },
// 	// onError: function (files, status, errMsg, pd) {
// 	// }
// });