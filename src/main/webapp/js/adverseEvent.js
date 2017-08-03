function initForm(entityClass, entityId){
	$("#oneApplicant").change(function () {
		if($(this).val()==="doctor"){
			$("#oneDoctorTextBody").show();

			$("#oneAnotherTextBody").hide();
		} else if($(this).val()==="another"){
			$("#oneDoctorTextBody").hide();
			$("#oneAnotherTextBody").show();
		} else {
			$("#oneDoctorTextBody").hide();
			$("#oneAnotherTextBody").hide();
		}
	}).trigger("change");
	$("#threeEthos").change(function () {
		if($(this).val()==="another"){
			$("#threeAnotherTextBody").show();
		} else {
			$("#threeAnotherTextBody").hide();
		}
	}).trigger("change");
	$("#threeBirthDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_aStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_bStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_cStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_dStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_aEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_bEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_cEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#four_dEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_aEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_aStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_bEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_bStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_cEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_cStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_dEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_dStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_eEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_eStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_fEndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#five_fStartDate").datepicker({dateFormat: 'yy-mm-dd'});
	
	$("#six_5StartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_5EndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_4StartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_4EndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_3StartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_3EndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_2StartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_2EndDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_1StartDate").datepicker({dateFormat: 'yy-mm-dd'});
	$("#six_1EndDate").datepicker({dateFormat: 'yy-mm-dd'});

	$("#seven_1Date").datepicker({dateFormat: 'yy-mm-dd'});

	$("#seven_2Date").datepicker({dateFormat: 'yy-mm-dd'});

	$("#seven_3Date").datepicker({dateFormat: 'yy-mm-dd'});

	$("#lethalDate").datepicker({dateFormat: 'yy-mm-dd'});
	if(entityId!=null){
		makeReadonly();
	}
}
