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
										</tr>


										<c:choose>
											<c:when test="${ fn:length(batch.batchItems) gt 0}">
												<c:forEach items="${batch.batchItems}" var="item">

													<tr>

														<td>${item.id}</td>
														<td>${item.status}</td>
														<td>${item.resourceLocation}</td>

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