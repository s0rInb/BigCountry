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
	$("#threeBirthDate").datepicker();
	$("#four_aStartDate").datepicker();
	$("#four_bStartDate").datepicker();
	$("#four_cStartDate").datepicker();
	$("#four_dStartDate").datepicker();
	$("#four_aEndDate").datepicker();
	$("#four_bEndDate").datepicker();
	$("#four_cEndDate").datepicker();
	$("#four_dEndDate").datepicker();
	$("#five_aEndDate").datepicker();
	$("#five_aStartDate").datepicker();
	$("#five_bEndDate").datepicker();
	$("#five_bStartDate").datepicker();
	$("#five_cEndDate").datepicker();
	$("#five_cStartDate").datepicker();
	$("#five_dEndDate").datepicker();
	$("#five_dStartDate").datepicker();
	$("#five_eEndDate").datepicker();
	$("#five_eStartDate").datepicker();
	$("#five_fEndDate").datepicker();
	$("#five_fStartDate").datepicker();
	
	$("#six_5StartDate").datepicker();
	$("#six_5EndDate").datepicker();
	$("#six_4StartDate").datepicker();
	$("#six_4EndDate").datepicker();
	$("#six_3StartDate").datepicker();
	$("#six_3EndDate").datepicker();
	$("#six_2StartDate").datepicker();
	$("#six_2EndDate").datepicker();
	$("#six_1StartDate").datepicker();
	$("#six_1EndDate").datepicker();

	$("#seven_1Date").datepicker();

	$("#seven_2Date").datepicker();

	$("#seven_3Date").datepicker();

	$("#lethalDate").datepicker();
	if(entityId!=null){
		makeReadonly();
	}
}
