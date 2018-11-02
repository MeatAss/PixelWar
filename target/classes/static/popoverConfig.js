var selectSize;

$(document).ready(function() {
	selectSize = 0;
	changeSizeBrush((selectSize + 1) * 5);

	sizePopover = createSizePopover('popoverSize');

	$('.popoverColor').each(function(index, elem) {
		$(elem).popover({ delay: {show: 1000, hide: 1000}, trigger: 'hover' });
		$(elem).attr('data-original-title', sizePopover.html());
	});

	$('.popoverColor').on('shown.bs.popover', function(){
		$('.popoverSize').each(function(index, elem) {

			index === selectSize ? $(elem).addClass('active') : $(elem).removeClass('active');

			$(elem).on('click', function() {
				$('.popoverSize').removeClass('active');
				$('.popoverSize input:checked').parent().addClass('active');
				selectSize = parseInt($('.popoverSize input:checked').attr('value'));
				changeSizeBrush((selectSize + 1) * 5);
			});
		});
	});
})

function createSizePopover(idLabel) {
	divMain = createElement('<div></div>', 'btn-group-toggle btn-group-vertical');
	configureElement(divMain, {'data-tongle' : 'buttons'})

	for (var i = 0; i < 3; i++) {
		divMain.append(createLableInputI(idLabel, i));
	}

	return createElement('<div></div>', '').append(divMain);
}

function createLableInputI(idLabel, valueRadio) {
	label = createElement('<label></label>', idLabel + ' btn btn-secondary');
	input = createElement('<input></input>', '');	
	configureElement(input, {'type': 'radio', 'name': 'optionsSize', 'autocomplete': 'off', 'value': valueRadio});

	i = createElement('<i></i>', 'far fa-circle');
	i.css('font-size', 5 * (valueRadio + 1) + 'px');

	label.append(input);
	label.append(i);

	return label;
}

function configureElement(elem, arrParam) {
	Object.getOwnPropertyNames(arrParam).forEach(function(val, idx, array) {
	  		elem.attr(val, arrParam[val]);
		});
}

function createElement(tagElement, classElement) {
    return $(tagElement).attr('class', classElement);
}