/**
 * 
 */
$(function(){
	$("#predictBtn").on('click', predictFunc);
});

function predictFunc() {
	let petalLength = $("#petalLength").val();
	let petalWidth = $("#petalWidth").val();
	let sepalLength = $("#sepalLength").val();
	let sepalWidth = $("#sepalWidth").val();
	
	if(petalLength.length == 0 || petalWidth.length == 0 
			|| sepalLength.length == 0 || sepalWidth.length == 0) {
		alert("데이터를 입력해 주세요");
		$("#sepalLength").focus()
		return;
	}
	
	if(isNaN(petalLength) || isNaN(petalWidth) 
			|| isNaN(sepalLength) || isNaN(sepalWidth)) {
		alert("데이터는 숫자로 입력해 주세요");
		return;
	}
	
	let sendData = {"sepalLength":sepalLength
			      , "sepalWidth":sepalWidth
			      , "petalLength" :petalLength
			      , "petalWidth" : petalWidth}
	
	$.ajax({
		url: 'predict'
		, method: 'POST'
		, async : false
		, data : sendData
		, success : function(resp) {
			$("#result").text(resp["predict_result"]);
		}
		, error: function(resp) {
			alert("err" + JSON.stringify(resp));
		}
	});
}