var fileCount = 0;
function initForm(entityClass, entityId) {
    $("#subjectRF").select2(returnSelect2options("/api/subjectRF"));
    $("#diagnosis").select2(returnSelect2options("/api/diagnosis"));
    $("#whoCall").select2(returnSelect2options("/api/whoCall"));
    $("#consultationConsultationType").select2(returnSelect2options("/api/consultationType"));
    $("#consultationWhoSentToConsultation").select2(returnSelect2options("/api/whoSentToCons"));
    $("#legalSupportWhoLegalSupport").select2(returnSelect2options("/api/whoLegalSupport"));
    $("#expertCenter").select2(returnSelect2options("/api/expertCenter"));
    $("#doctorExpertCenter").select2(returnSelect2options("/api/doctorExpertCenter"));
    $("#hotLineCallDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationFullDocumentDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationFullDocumentSendDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationConsultationDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationConclusionDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationSendRegionDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#consultationMedicalCommissionDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportAppealMinHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportResultMinHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportAppealRusHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportResultRusHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportAppealProcuratorHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportResultProcuratorHealthDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportAppealCourtDate").datepicker({dateFormat: 'yy-mm-dd'});
    $("#legalSupportPlannedCourtDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#birthday").datepicker({dateFormat: 'yy-mm-dd'});
	$("#infoConsentDate").datepicker({dateFormat: 'yy-mm-dd'});
    var infoConsentPatient = $("#infoConsent-patient");
    infoConsentPatient.uploadFile({
        url: "/api/uploadFile",
        multiple: false,
        dragDrop: false,
        uploadStr: infoConsentPatient.attr("uploadStr"),
        dynamicFormData: function () {
            var data = {
                "patientId": entityId,
                "fileType": "infoConsent",
                "name": $("#div-file-patient-" + (fileCount - 1)).text()
            };
            return data;
        },
        onSubmit: function (files) {
            $("#files-container-patient").append('<div id="div-file-patient-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                files[0] + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
            $('#div-file-patient-' + fileCount + " div span").on('click', function () {
                $(this).closest("div").parent("div").remove();
                $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                })
            });
            fileCount++;
        },
        onSuccess: function (files, data, xhr, pd) {
            var divFile = $('#div-file-patient-' + fileCount);
            divFile.empty();
            divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + data.entity.id + '">' +
                data.entity.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + data.entity.id + '"/>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + data.entity.name + '"/>');
        },
        onError: function (files, status, errMsg, pd) {
            $("#file-loading-error-patient").append(' "' + files + '"');
            $("#file-loading-error-patient").show();
        }
    });

    $.ajax({
        url: "/api/fileLinks/" + entityId,
        data: null,
        success: function (data) {
            data.entity.forEach(function (entrya) {
                if (entrya.fileType === 'infoConsent') {
                    $("#files-container-patient").append('<div id="div-file-patient-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                        entrya.path + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');

                    var divFile = $('#div-file-patient-' + fileCount);
                    divFile.empty();
                    divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + entrya.id + '">' +
                        entrya.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + entrya.id + '"/>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + entrya.name + '"/>');
                    $('#div-file-patient-' + fileCount + " div span").on('click', function () {
                        $(this).closest("div").parent("div").remove();
                        $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                        })
                    });
                    fileCount++;
                }
            })
        }
    });

    var infoConsentConsultation = $("#infoConsent-consultation");
    infoConsentConsultation.uploadFile({
        url: "/api/uploadFile",
        multiple: false,
        dragDrop: false,
        uploadStr: infoConsentConsultation.attr("uploadStr"),
        dynamicFormData: function () {
            var data = {
                "patientId": entityId,
                "fileType": "consultation",
                "name": $("#div-file-consultation" + (fileCount - 1)).text()
            }
            return data;
        },
        onSubmit: function (files) {
            $("#files-container-consultation").append('<div id="div-file-consultation' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                files[0] + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
            $('#div-file-consultation' + fileCount + " div span").on('click', function () {
                $(this).closest("div").parent("div").remove();
                $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                })
            });
            fileCount++;
        },
        onSuccess: function (files, data, xhr, pd) {
            var divFile = $('#div-file-consultation' + fileCount);
            divFile.empty();
            divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + data.entity.id + '">' +
                data.entity.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + data.entity.id + '"/>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + data.entity.name + '"/>');
        },
        onError: function (files, status, errMsg, pd) {
            $("#file-loading-error-consultation").append(' "' + files + '"');
            $("#file-loading-error-consultation").show();
        }
    });

    $.ajax({
        url: "/api/fileLinks/" + entityId,
        data: null,
        success: function (data) {
            data.entity.forEach(function (entrya) {
                if (entrya.fileType === 'consultation') {
                    $("#files-container-consultation").append('<div id="div-file-consultation' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                        entrya.path + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
                    var divFile = $('#div-file-consultation' + fileCount);
                    divFile.empty();
                    divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + entrya.id + '">' +
                        entrya.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + entrya.id + '"/>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + entrya.name + '"/>');
                    $('#div-file-consultation' + fileCount + " div span").on('click', function () {
                        $(this).closest("div").parent("div").remove();
                        $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                        })
                    });
                    fileCount++;
                }
            })
        }
    });

    var infoConsentLegalSupport = $("#infoConsent-legalSupport");
    infoConsentLegalSupport.uploadFile({
        url: "/api/uploadFile",
        multiple: false,
        dragDrop: false,
        uploadStr: infoConsentLegalSupport.attr("uploadStr"),
        dynamicFormData: function () {
            var data = {
                "patientId": entityId,
                "fileType": "legalSupport",
                "name": $("#div-file-legalSupport-" + (fileCount - 1)).text()
            }
            return data;
        },
        onSubmit: function (files) {
            $("#files-container-legalSupport").append('<div id="div-file-legalSupport-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                files[0] + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');
            $('#div-file-legalSupport-' + fileCount + " div span").on('click', function () {
                $(this).closest("div").parent("div").remove();
                $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                })
            });
            fileCount++;
        },
        onSuccess: function (files, data, xhr, pd) {
            var divFile = $('#div-file-legalSupport-' + fileCount);
            divFile.empty();
            divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + data.entity.id + '">' +
                data.entity.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + data.entity.id + '"/>');
            divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + data.entity.name + '"/>');

        },
        onError: function (files, status, errMsg, pd) {
            $("#file-loading-error-legalSupport").append(' "' + files + '"');
            $("#file-loading-error-legalSupport").show();
        }
    });

    $.ajax({
        url: "/api/fileLinks/" + entityId,
        data: null,
        success: function (data) {
            data.entity.forEach(function (entrya) {
                if (entrya.fileType === 'legalSupport') {
                    $("#files-container-legalSupport").append('<div id="div-file-legalSupport-' + fileCount + '"><div class="well well-sm task-file" style="margin-bottom: 5px;"><div class="loading"></div>' +
                        entrya.path + '<span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div></div>');

                    var divFile = $('#div-file-legalSupport-' + fileCount);
                    divFile.empty();
                    divFile.append('<div class="well well-sm task-file" style="margin-bottom: 5px;"><a href="/api/getFile?id=' + entrya.id + '">' +
                        entrya.name + '</a><span class="remove-span glyphicon glyphicon-remove" aria-hidden="true"></span></div>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].id" value="' + entrya.id + '"/>');
                    divFile.append('<input hidden name="fileLinks[' + fileCount + '].name" value="' + entrya.name + '"/>');
                    $('#div-file-legalSupport-' + fileCount + " div span").on('click', function () {
                        $(this).closest("div").parent("div").remove();
                        $.post("/api/deleteFileLinks/"+$(this).closest('div').next('input').val(), function (data) {
                        })
                    });
                    fileCount++;
                }
            })
        }
    })
	$("#birthday").change(function (){var ageDifMs = Date.now() - new Date($("#birthday").val()).getTime();
		var ageDate = new Date(ageDifMs); // miliseconds from epoch
		$("#age").val(Math.abs(ageDate.getUTCFullYear() - 1970));});
}


function deletePatient(entityId) {
    $.ajax({
        url: "/api/patientDelete/" + entityId,
        data: null,
        success: function () {
            window.location.hash = "patients"
        }
    })
}

function beckToPatients () {
    window.location.hash = "patients"
}