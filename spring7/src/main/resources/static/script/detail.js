    $(function(){
    	$("#replyBtn").on('click', replyWrite);
    	init();
    });

    // 모든 댓글 목록 읽어옴
    function init() {
        const boardNum = $("#boardNum").val();
        const contextPath = $("#contextPath").val(); 	
        
    	$.ajax({
    		method : 'GET'
    		, url : contextPath+"/reply/replyAll"
    	    , data: {
    	        "boardNum": boardNum
    	    }
    		, success : output
    	});
    }

    // 댓글 등록 
    function replyWrite() {
        const writer = $("#loginId").val();
        const contents = $("#replyText").val();
        const boardNum = $("#boardNum").val(); 	
        const contextPath = $("#contextPath").val(); 	
       
          $.ajax({
           // 요청방식: post, 요청주소: /reply/replyInsert, 요청데이터: 작성자, 작성내용, 게시글번호
           method: "POST",
           url: contextPath+"/reply/replyInsert",
           data: {
               "replyWriter": writer,
               "replyText": contents,
               "boardNum": boardNum
           },
           success : function(resp) {
               $("#replyWriter").val('');
               $("#replyText").val('');
	   			init();
   			},
  
           error: function (err) {
               console.log("요청실패", err);
           }
        });
    }
    // 조회 내용 출력
    function output(resp) {
    	 if(resp.length == 0) return;
    	 let data = '<table>';
    	 data += "<tr>";
    	 data += "	<th>작성자</th>";
    	 data += "	<th>내용</th>";
    	 data += "	<th>작성시간</th>";
    	 data += "	<th></th>";
    	 data += "</tr>";
    	 
    	$.each(resp, function(index, item){
    		data += '<tr class="reply-tr" data-sno="'+ item.replyNum +'" >';
    		data += '	<td class="reply-writer">' + item.replyWriter + '</td>';
    		data += '	<td class="reply-text">' + item.replyText + '</td>';
    		data += '	<td class="reply-date">' + item.createDate + '</td>';
    		data += '	<td class="btns" >';
	    		if($("#loginId").val() == item.replyWriter) {
		    		data += '       <input type="button" class="updatebtn btn btn-info" data-sno="'+item.seq +'"value="수정" />';
		    		data += '	    <input type="button" class="delbtn btn btn-danger"  data-num="'+item.replyNum +'"value="삭제" />';
	    		}
    		data += '</td>';
    		data += '</tr>';
    	});
    	
    	data += '</table>';
    	$("#reply-list").html(data);
    	$(".delbtn").on('click', deleteReply);
    	$(".updatebtn").on('click', updateReply);
    }

    // 댓글 삭제
    function deleteReply() {
    	const contextPath = $("#contextPath").val();
    	let replyNum = $(this).attr('data-num');
    	$.ajax({
    		method:"GET"
    		, url: contextPath+"/reply/replyDelete"
    		, data : {"replyNum" : replyNum}
    		, success : function(resp) {
    			alert(resp);
    			init();
    		}
    	});
    }

    function updateReply() {
    	
    }
