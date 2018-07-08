<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>

<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<head>
<title>Submitted Batch Jobs</title>
</head>



<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

tr.job:hover {
	background-color: #ffa;
}
</style>



<script>
	function showJobItems(row) {
		$('.jobItems').hide();
		$(row).next('tr').toggle();
	}

	function approve(itemId) {

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/approveItem.do?itemId="
					+ itemId,
			dataType : "json",
			processData : false,
			contentType : false,
			success : function(response) {
				if (response.STATUS === "SUCCESS") {
					alert("Successfully approved");
					location.reload();
				} else {
					alert("Couldn't approve because of some internal problem");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				alert("Couldn't approve because of some internal problem");
			}
		});

	}

	function reject(itemId) {

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rejectItem.do?itemId="
					+ itemId,
			dataType : "json",
			processData : false,
			contentType : false,
			success : function(response) {
				if (response.STATUS === "SUCCESS") {
					alert("Successfully rejected");
					location.reload();
				} else {
					alert("Couldn't reject because of some internal problem");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				alert("Couldn't reject because of some internal problem");
			}
		});

	}
</script>

<body>

	<div>
		<h1>Submitted Jobs:</h1>
		(Click to view items)
		<table>
			<tr>
				<th>Batch ID</th>
				<th>Status</th>
				<th>Created At</th>
				<th>Updated At</th>
			</tr>

			<c:choose>
				<c:when test="${ fn:length(batch_list) gt 0}">
					<c:forEach items="${batch_list}" var="batch">

						<tr class="job" onclick='showJobItems(this)'>
							<td>${batch.id }</td>
							<td>${batch.status }</td>
							<td>${batch.createdAt }</td>
							<td>${batch.updatedAt }</td>
						</tr>


						<tr class="jobItems" style="display: none;">
							<td colspan="4">


								<div>
									<h2>Job Items:</h2>
									<table>
										<tr>
											<th>Item ID</th>
											<th>Status</th>
											<th>URL</th>
											<th>Download</th>
											<th>Approve</th>
											<th>Reject</th>

										</tr>


										<c:choose>
											<c:when test="${ fn:length(batch.batchItems) gt 0}">
												<c:forEach items="${batch.batchItems}" var="item">

													<tr>
														<td>${item.id}</td>
														<td>${item.status}</td>
														<td>${item.resourceLocation}</td>
														<td>

															<c:choose>

																<c:when test="${item.status eq 'UNAVAILABLE'}">

																</c:when>
																<c:otherwise>
																	<a href="${pageContext.request.contextPath}/downloadItem/${item.id}"
																			download>Download</a>

																</c:otherwise>
															</c:choose></td>

														</td>
														<td><c:choose>
																<c:when test="${item.status eq 'APPROVED'}">
																Already Approved
															</c:when>
															<c:when test="${item.status eq 'UNAVAILABLE'}">

															</c:when>
																<c:otherwise>
																	<a href="#" onclick="approve('${item.id}')">Approve</a>

																</c:otherwise>
															</c:choose></td>
														<td><c:choose>
																<c:when test="${item.status eq 'REJECTED'}">
																Already Rejected
															</c:when>
															<c:when test="${item.status eq 'UNAVAILABLE'}">

															</c:when>
																<c:otherwise>
																	<a href="#" onclick="reject('${item.id}')">Reject</a>
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>



											</c:when>

											<c:otherwise>
												<tr>
													<td colspan="3">No items found</td>
												</tr>
											</c:otherwise>
										</c:choose>

									</table>
								</div>
							</td>

						</tr>



					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">No submitted jobs</td>
					</tr>
				</c:otherwise>
			</c:choose>

		</table>

	</div>




</body>
</html>