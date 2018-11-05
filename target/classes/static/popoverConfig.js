var selectSize;
var sizePopover;
var tim;
var dX, dY;
var popoverItemY;

$(document).ready(function() {

	$('#hover1').on('mouseenter', function() {$(this).fadeTo(1,1)});
	$('#hover1').on('mouseleave', function() {$(this).fadeTo(1,0)});

	$(document).on( "mousemove", function( event ) {
		dX = event.pageX;
		dY = event.pageY;
	});

	selectSize = 0;
	changeSizeBrush((selectSize + 1) * 10);

	sizePopover = createSizePopover('popoverSize', 'popoverColor', 'popoverColorLabel');
	$(document.body).append(sizePopover);

	$('.popoverColor').on('shown.bs.popover', function(){
		$('.popoverSize').each(function(index, elem) {

			index === selectSize ? $(elem).addClass('active') : $(elem).removeClass('active');

			$(elem).on('click', function() {
				$('.popoverSize').removeClass('active');
				$('.popoverSize input:checked').parent().addClass('active');
				selectSize = parseInt($('.popoverSize input:checked').attr('value'));
			});
		});
	});

	$('.popoverColor').each(function(i, item) {
		$(item).on('click', function() {
			changeSizeBrush(parseInt($(this).find('i').css('font-size')));
		})
	});
})

function configureIdTool(idTool) {
    $(idTool + ' label').each(function(index, elem) {
        elem.onmousedown=function(){
            tim=setTimeout( function() {showPopover(elem)}, 1000);
        };
        elem.onmouseup=function(){
            clearTimeout(tim);
        };
        elem.onmouseleave=function(){
            clearTimeout(tim);
        };
    });
}

function showPopover(labelItem) {
	$('#hover1').off('mouseleave');

	height = ($($('.popoverColor')[0]).height()) + (parseInt($($('.popoverColor')[0]).css('padding-top'))*2) + (parseInt($($('.popoverColor')[0]).css('border-top-width'))*2);
	elem = dY - ($('#hover1').offset().top);

	popoverItemY = Math.floor(elem / height);
	sizePopover.offset({
		top: $('#hover1').offset().top + (popoverItemY * height + height / 2) - ($(sizePopover).height() / 2), 
		left: ($($('.popoverColor')[0]).width()) + (parseInt($($('.popoverColor')[0]).css('padding-left'))*2) + (parseInt($($('.popoverColor')[0]).css('border-top-width'))*2) 
	});

	sizePopover.find('label').removeClass('active');

	$(sizePopover.find('label')[(Math.floor(parseInt($(labelItem).find('i').css('font-size')) / 10)) - 1]).addClass('active');

	sizePopover.on('mouseenter', function() {
		sizePopover.off('mouseenter');
		sizePopover.on('mouseleave', function() {
			sizePopoverEventHandler();
		});
	});

	sizePopover.removeClass('invisible');
}

function sizePopoverEventHandler() {
			$('#hover1').on('mouseleave', function() {$(this).fadeTo(1,0)});
			$('#hover1').fadeTo(1,0);

			sizePopover.off('mouseleave');
			sizePopover.addClass('invisible');
}

function createSizePopover(classLabel, classParentLabel, idLabel) {
	divMain = createElement('<div></div>', 'btn-group btn-group-toggle invisible');
	configureElement(divMain, {'data-tongle' : 'buttons'});
	divMain.css({'position' : 'absolute', 'z-index': '999', 'top' : '0', 'left' : '0'});

	for (var i = 0; i < 3; i++) {
		divMain.append(createLableInputI(classLabel, i, classParentLabel, idLabel));
	}

	return divMain;
}

function createLableInputI(classLabel, valueRadio, classParentLabel, idLabel) {
	label = createElement('<label></label>', classLabel + ' btn btn-secondary');
	input = createElement('<input></input>', '');	
	configureElement(input, {'type': 'radio', 'name': 'optionsSize', 'autocomplete': 'off', 'value': valueRadio});

	i = createElement('<i></i>', 'far fa-circle');
	i.css({'font-size' : 10 * (valueRadio + 1) + 'px', 'display' : 'inline-block', 'vertical-align' : 'middle', 'float' : 'none'});

	label.on('click', function() {
		selectSize = valueRadio;
		changeSizeBrush((selectSize + 1) * 10);

		$('.' + classParentLabel).each(function(i, item) {$(item).removeClass('active')});
		label.addClass('active');
		$('#' + idLabel + popoverItemY + ' i').css('font-size', 10 * (valueRadio + 1) + 'px');
		sizePopover.css('opacity', 1);

		sizePopoverEventHandler();
	});

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