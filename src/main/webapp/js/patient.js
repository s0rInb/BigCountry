function initForm(entityClass, entityId) {
	$("#subjectRF").select2(returnSelect2options("/api/subjectRF"));
	$("#diagnosis").select2(returnSelect2options("/api/diagnosis"));
	$("#whoCall").select2(returnSelect2options("/api/whoCall"));
	$("#consultationConsultationType").select2(returnSelect2options("/api/consultationType"));
	$("#consultationWhoSentToConsultation").select2(returnSelect2options("/api/whoSentToCons"));
	$("#legalSupportWhoLegalSupport").select2(returnSelect2options("/api/whoLegalSupport"));
}
$("#corpresumeuploader-").uploadFile({
	url: "/api/uploadFile",
	multiple: false,
	dragDrop: false,
	fileName: "file",
	uploadStr: $("#corpresumeuploader-").attr("uploadStr"),
	dynamicFormData: function () {
		var data = {"catalog": "staff", "id": '1', "fileCount":"1"}
		return data;
	},
	// onSubmit: function (files) {
	// },
	// onSuccess: function (files, data, xhr, pd) {
	// },
	// onError: function (files, status, errMsg, pd) {
	// }
});