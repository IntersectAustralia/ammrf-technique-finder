
//
// init UI
//

$(function() {

	$("#mediaList tbody.sortableContent").sortable({ 
		axis:'y',
		containment:'parent',
                handle: '.sortHandle',
                tolerance: 'pointer',
                scrollSensitivity: 1
	});

        /*
	$("#contactsList tbody.sortedContent").sortable({ 
		axis:'y',
		containment:'parent',
                handle:'.sortHandle',
                tolerance: 'pointer'
	});*/

	$("#mediaList tbody.sortableContent").disableSelection();

	/*$("#contactsList tbody.sortedContent").disableSelection();*/

	var $dialog = $("#resourcesDialog").dialog({
		width: 640, 
		height: 480,
		autoOpen: false,
		modal: true
	});

	var $errMessage = $("#errorDialog").dialog({
		width: 440, 
		height: 200,
		autoOpen: false,
		modal: true,
		buttons: {
			"Ok":function(){$(this).empty().dialog("close");}
		}
	});

        $("form").submit(function() {
                $("form input.mediaSection").each(function() {
                    var mediaIds = ''
		    $("tr._" + this.id + " #mediaList tbody.sortableContent tr").each(function(){
                        var mid = this.id.substr(this.id.indexOf('_')+1);
                        mediaIds += mediaIds.length == 0 ? mid : ',' + mid;
                        })
                    this.value = mediaIds
                    })
       });

});

//
// callbacks
//

function addMedia(section) {
	var $checked = $("input[name=media]:checked");
        if (section == 'LIST') {
		$('tr._LIST #mediaList tbody.sortableContent').empty()
        }
	$checked.each(function(){
		$('tr._' + section + ' #mediaList tbody.sortableContent')[0].appendChild($('#mediaDB #media_' + this.value)[0])
	});
}

// dictionary comparisson between to non-single typed arrays
function compareKeys(keys1, keys2) {
	for(var i = 0; i < keys1.length; i++) {
		if (i >= keys2.length) return 1;
		if (isNaN(keys1[i]) || isNaN(keys2[i])) {
			if (keys1[i] > keys2[i]) return 1;
			if (keys1[i] < keys2[i]) return -1;
		} else {
			if (+keys1[i] > +keys2[i]) return 1;
			if (+keys1[i] < +keys2[i]) return -1;
		}
        }
	return keys1.length < keys2.length ? -1 : 1;
}

function addAssociates(type) {
	var $checked = $('#new' + type + 'List input[name=associate]:checked');
        // inserts each checked associate in corresponding possition
	$checked.each(function(){
                var newRow = $('#' + type + 'DB #' + type + '_' + this.value)[0]
		var tbody = $('#' + type + 'List tbody.sortedContent')[0]
                var rows = $('#' + type + 'List tbody.sortedContent tr')
                var inserted = false
                for(var i = 0; i < rows.size() && !inserted; i++) {
			    var oldRow = rows[i]
                            if (compareKeys($(newRow).data('keys'),$(oldRow).data('keys')) < 0) {
                            	tbody.insertBefore(newRow, oldRow)
                                inserted = true
                            }                            
                }
                if (!inserted) {
			tbody.appendChild(newRow)
                }
	});
}

//
// click handlers
//

function listMedia(url, section) {
	var ids = $("tr._" + section + " #mediaList tbody.sortableContent").sortable('serialize', {key:'mediaIds'});
	var data = 'mediaSection=' + section + '&' + ids;
	listGeneric(url, data, '#resourcesDialog', (function(){addMedia(section)}) );
}

function listAssociates(type, url) {
	var ids = ''
        $("tr._" + type + " #" + type + "List tbody.sortedContent tr").each(function() {
            ids += 'ids=' + this.getAttribute('theId') + '&'
        })
	ids += 'type=' + type
	listGeneric(url, ids, '#resourcesDialog', (function(){addAssociates(type)}) );
}

function listGeneric(url, data, dialogId, actionOk) {
	$.ajax({
	   type: "POST",
	   url: url,
	   data: data,
           error: function(req, status, error) {
		$("#errorDialog").html(window.messages.requestError).dialog('open');
           },
	   success: function(data){
                if (data.match(/EMPTY/)) {
    		    $("#errorDialog").html(window.messages.requestEmpty).dialog('open');
                } else {
                    $(dialogId).dialog({
		        buttons: {
			    "Select":function(){$(this).dialog("close"); actionOk(); $(this).empty();},
			    "Cancel":function(){$(this).empty().dialog("close");}
		        }
                    })
    		    $(dialogId).html(data).dialog('open');
                }
	   }
	});
}

function removeMedia(id, section) {
        var row = $("tr._" + section + ' #mediaList #media_' + id)[0];
	$("tr._" + section + ' #mediaList tbody.sortableContent')[0].removeChild(row)
}

function removeAssociate(type, id) {
        var row = $('#' + type + 'List #' + type + '_' + id)[0];
	$('#' + type + 'List tbody.sortedContent')[0].removeChild(row)
}

