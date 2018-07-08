<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>

<script>
	function submitRequest() {
		var formData = new FormData($('form')[0]);

		formData
				.append("batchInputFile", $("#batchInputFile").prop("files")[0]);

		$('#info').hide();

		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/downloadFiles.do",
			dataType : "json",
			data : formData,
			processData : false,
			contentType : false,
			success : function(response) {
				$('#info').html('<p>' + response.msg + '</p>');
				$('#info').show();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				$('#info').html('<p>An error has occurred</p>');
				$('#info').show();
			}
		});

	}
</script>

<head>
<title>Welcome</title>
</head>
<body>


	<form id="batchInputForm" method="post" enctype="multipart/form-data">

		<h1>Welcome to Universal File Downloader</h1>


		<div>
			<div>
				Input download links below:<br>
				<textarea rows="10" style="width: 100%;" id="batchInputText"
					name="batchInputText"></textarea>
				<br> Or upload a file: <br> <input type="file"
					id="batchInputFile" name="batchInputFile" /> <br> <br>
			</div>
			<div>
				<button type="button" onclick="submitRequest();">Submit</button>			
				
				<br> <br> <a href="${pageContext.request.contextPath}/viewSubmittedJobs.do"> View submitted batch jobs</a>
			</div>

		</div>

		<div id="info"></div>


	</form>



</body>
</html>




