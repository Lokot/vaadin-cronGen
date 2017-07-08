window.ru_skysoftlab_crongen_CronGenExt = function() {	
	var connectorId = this.getParentId();
	var element = this.getElement(connectorId);
	var id = $(element).attr('id');
	element.value_set = false;
	$(element).cronGen(id);
	$(element).prop( "disabled", true );
};