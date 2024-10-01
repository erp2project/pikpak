document.addEventListener('DOMContentLoaded', function() {
	var oal_reset_btn = document.getElementById('oal_reset_btn');
	var oal_search_btn = document.getElementById('oal_search_btn');
	var oal_start_dt = document.getElementById('oal_start_dt');
	var oal_end_dt = document.getElementById('oal_end_dt');
	var oal_product_cd = document.getElementById('oal_product_cd');
	var oal_product_nm = document.getElementById('oal_product_nm');

	//초기화 버튼
	oal_reset_btn.addEventListener('click', function() {
		location.href = '/order_aplist';
	});

	//조회 버튼
	oal_search_btn.addEventListener('click', function() {
		if(oal_start_dt.value == "" && oal_end_dt.value != ""){
			alert("날짜를 확인해 주세요.");
		}
		else if(oal_start_dt.value != "" && oal_end_dt.value == ""){
			alert("날짜를 확인해 주세요.");
		}
		else{
			oal_frm.method = "get";
			oal_frm.action = "/order_aplistck";
			oal_frm.submit();
		}
		
		/*
		else{
			const params = new URLSearchParams({
				process_st: ol_process_st.value,
				start_dt: ol_start_dt.value,
				end_dt: ol_end_dt.value,
				product_cd: ol_product_cd.value,
				product_nm: ol_product_nm.value
			}).toString();
			fetch('/order_listck?' + params, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			})
				.then(response => response.json())
				.then(data => {
					console.log(data);
					var tablebody = document.getElementById('order_tablebody');
					tablebody.innerHTML = '';
					
					data.forEach((order) => {
						const row = document.createElement('tr');
						row.innerHTML = `
							<td><input type="checkbox"></td>
							<td style="text-align: center;">NO.</td>
							<td style="text-align: center;"><a
								${order.order_cd}></a></td>
							<td style="text-align: center;"><a
								${order.product_cd}></a></td>
							<td style="text-align: left;"><a
								${order.product_nm}></a></td>
							<td style="text-align: right;"><a
								${order.order_qty}></a></td>
							<td style="text-align: right;"><a
								${order.purchase_pr}></a>원(<a
								${order.order_price}></a>원)</td>
							<td style="text-align: center;"><a
								${order.order_dt}></a></td>
							<td style="text-align: center;"><a
								${order.process_st}></a></td>
							<td style="text-align: center;"><nav
							th:replace="~{/order/order_check.html :: order_check}"></nav></td>
						`;
						tablebody.appendChild(row);
					});
				})
				.catch((error) => {
					console.log(error);
				});
		}
		*/
	});

	//날짜
	oal_start_dt.addEventListener('input', dateck)
	oal_end_dt.addEventListener('input', dateck)

	function dateck() {
		if (oal_start_dt.value > oal_end_dt.value && oal_end_dt.value != "") {
			oal_end_dt.value = "";
			alert("날짜를 확인해 주세요.");
		}
	}
});