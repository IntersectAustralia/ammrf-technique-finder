//script to change background-color of the selected
//button and put the appropriate value in an input object
function optionClick(optionName, value) {
	if(!window.isOptionsDisabled || !isOptionsDisabled()) {
		var selectedOption = $("li[id^='" + optionName + "'][style*='tf_bullet_active.gif']")
		var newOption = $('#' + optionName + value)
		if(selectedOption.id != optionName + value) {
			selectedOption.css("color", '').css("listStyleImage", 'url(../images/ammrf/tf_bullet_inactive.gif)')
			newOption.css("color", '#c55b19').css("listStyleImage", 'url(../images/ammrf/tf_bullet_active.gif)')
			optionChanged(optionName, value)
		}
	}
}

// restore options selection from the values of the hidden input fields
function restoreOptionSelection(optionName) {
	var optionValue = $('#'+optionName+'Val').val()
	if(optionValue != '') {
		optionClick(optionName, optionValue)
	}
}