export class inreq_list {
	//검색어 조회 ajax
	inreqstate_list() {
		const inreqst_start_date = document.getElementById("inreqst_start_date").value;
		const inreqst_end_date = document.getElementById("inreqst_end_date").value;
		const inreqst_search_pdcd = document.getElementById("inreqst_search_pdcd").value;
		const inreqst_search_state = document.getElementById("inreqst_search_state").value;

		if (inreqst_start_date > inreqst_end_date) {
			alert('정상적인 일자를 입력해주세요');
		}
		else if ((inreqst_start_date != "" && inreqst_end_date == "") || (inreqst_start_date == "" && inreqst_end_date != "")) {
			alert('등록일자로 검색 시 모두 입력되어야합니다.');
		}
		else {
			var inreqst_data = {
				"start_date": inreqst_start_date,
				"end_date": inreqst_end_date,
				"product_cd": inreqst_search_pdcd,
				"request_st": inreqst_search_state
			};

			this.search_data = JSON.stringify(inreqst_data);

			fetch("/inreqstate_search", {
				method: "post",
				headers: { "Content-type": "application/json" },
				body: this.search_data
			})
				.then(function(result_data) {
					return result_data.json();
				})
				.then(function(result_res) {
					const tbody = document.querySelector("#irst_tbody");

					tbody.innerHTML = '';


					result_res.forEach(function(irstate) {
						//const disabled = ['진행', '완료', '거절'].includes(inputreq.request_st) ? 'disabled' : '';
						//const manageButtonDisabled = disabled ? 'disabled' : '';


						const list = `<tr>
        			
            		<td style="text-align: center; width: 9%;">${irstate.product_cd}</td>
            		<td style="text-align: center; width: 17%;">${irstate.product_nm}</td>
            		<td style="text-align: center; width: 7%;">${irstate.total_requested_qty}</td>
            		<td style="text-align: center; width: 7%;">${irstate.remaining_qty}</td>
            		<td style="text-align: center; width: 17%;">${irstate.add_req}</td>
            		<td style="text-align: center; width: 11%;">${irstate.hope_dt}</td>
            		<td style="text-align: center; width: 6%;">${irstate.request_st}</td>
            		<td style="text-align: center; width: 11%;">${irstate.request_dt.substring(0, 10)}</td>
													
            		<td style="text-align: center; width: 15%;">
            		<button class="btn btn-primary inreq_delivery" 
					th:data-supplier-cd="${irstate.supplier_cd}"
					th:data-supplier-nm="${irstate.supplier_nm}"
					th:data-product-cd="${irstate.product_cd}"
					th:data-product-nm="${irstate.product_nm}"
					th:data-product-qty="${irstate.total_requested_qty}"
					th:data-add-req="${irstate.add_req}"
					th:data-hope-dt="${irstate.hope_dt}"
					th:data-request-cd="${irstate.request_cd}"
							
					style="padding-right:5px; padding-left:5px; width:40%;"
					type="button">납품</button>
										
					<button class="btn btn-danger inreq_reject" 
					style="padding-right:5px; padding-left:5px; width:40%;"
					th:data-product-qty="${irstate.total_requested_qty}"
					th:data-remain-qty="${irstate.remaining_qty}"
					th:data-request-st="${irstate.request_st}"
					th:data-request-idx="${irstate.request_idx}"
					type="button">거절</button>
            		</td>
					</tr>`;

						tbody.innerHTML += list;
					});

				})
				.catch(function(error) {
					console.log(error);
					alert('데이터 조회에 문제가 발생하였습니다.');
				});
		}
	}



	//입고요청에 대한 거절 버튼 클릭시
	reject_list(inreq_idx, product_qty, remain_qty, request_st) {
		console.log(inreq_idx);
		this.idx = "request_idx=" + inreq_idx;

		if (product_qty != remain_qty) {
			alert("'대기' 상태의 요청만 거절 가능합니다.");
		}
		else if (request_st == "거절") {
			alert('이미 거절된 요청입니다.');
		}
		else if (confirm("해당 입고요청을 거절하시겠습니까?")) {
			fetch('/reject_deliverylist', {
				method: "post",
				headers: { "Content-type": "application/x-www-form-urlencoded" },
				body: this.idx
			})
				.then(function(result_data) {
					return result_data.text();
				})
				.then(function(result_res) {
					if (result_res == "ok") {
						alert('해당 입고요청이 거절되었습니다.');
						location.reload();
					}
				})
				.catch(function(error) {
					console.log(error);
				});
		}

	}

	//거절로 상태변화된 리스트에 대해 납품 클릭 못하게 하기


}


export class delivery_list {
	//상태 가져와서 '배송' 이면 버튼 비활성화
	delivery_btn() {
		const current_st = document.querySelectorAll(".current_st"); //상태 컬럼
		const deliver_manage = document.querySelectorAll(".deliver_manage"); //배송 버튼

		current_st.forEach(function(st, index) {
			if (st.innerText == "배송") {  // 상태가 '배송'이면
				deliver_manage[index].disabled = true;  // 해당 인덱스의 배송 버튼 비활성화
			}

		});
	}

	//납품등록 관리 버튼 클릭시
	decide_delivery() {
		if (frm_decide_deli.departure_dt.value == "") {
			alert('배송일시를 입력해주세요');
		}
		else {
			if (confirm("실제 배송이 완료된 데이터 코드인지 확인해주세요. 배송 확정 하시겠습니까?")) {
				frm_decide_deli.method = "post";
				frm_decide_deli.action = "./insert_exreceiving";
				frm_decide_deli.submit();
			}

		}

		/*	인자로 deliveryData 받았음
			this.exreceiving_data = {
				"request_cd": deliveryData.requestCd,
				"deliver_cd": deliveryData.deliverCd,
				"supplier_cd": deliveryData.supplierCd,
				"product_cd": deliveryData.productCd,
				"exreceiving_qty": deliveryData.deliverQty,
				"exreceiving_size": deliveryData.deliverSize,
				"exreceiving_area": deliveryData.deliverArea,
				"make_dt": deliveryData.makeDt
			}
	
			if (confirm('실제 배송이 완료된 데이터 코드인지 확인해주세요. 배송 확정 하시겠습니까?')) {
				fetch('/insert_exreceiving', {
					method: "post",
					headers: { "Content-type": "application/json" },
					body: JSON.stringify(this.exreceiving_data)
				})
					.then(function(result_data) {
						return result_data.text();
					})
					.then(function(result_res) {
						//console.log(result_res);
						if (result_res == "ok") {
							alert("배송 변경 완료되었습니다.");
							location.reload();
						}
						else {
							alert("데이터베이스 문제로 변경이 되지 못하였습니다.");
						}
					})
					.catch(function(error) {
						console.log(error);
					});
			}
	
		*/
	}

	//전체 체크 누르고 끌 때
	deli_all_ckbox() {
		this.all_ck_checked = document.getElementById("del_all_ck").checked;
		this.names_ea = document.getElementsByName("del_each_ck").length;

		if (this.names_ea == 1) {
			frm_delivery_list.del_each_ck.checked = this.all_ck_checked;
		}
		else {
			for (this.f = 0; this.f < frm_delivery_list.del_each_ck.length; this.f++) {
				frm_delivery_list.del_each_ck[this.f].checked = this.all_ck_checked;
			}
		}
	}

	//개별 체크 누르고 끌 때
	deli_each_ckbox() {
		this.ck_count = 0;
		this.names_ea = document.getElementsByName("del_each_ck").length;

		if (this.names_ea == 1 && frm_delivery_list.del_each_ck.checked == true) {
			this.ck_count++;
			document.getElementById("del_all_ck").checked = frm_delivery_list.del_each_ck.checked;
		}
		else {
			for (this.f = 0; this.f < frm_delivery_list.del_each_ck.length; this.f++) {
				if (frm_delivery_list.del_each_ck[this.f].checked == true) {
					this.ck_count++;
				}
			}

			if (this.ck_count == frm_delivery_list.del_each_ck.length) {
				document.getElementById("del_all_ck").checked = true;
			}
			else {
				document.getElementById("del_all_ck").checked = false;
			}

		}

		return this.ck_count;
	}

	//납품등록 리스트 삭제
	delete_delivery() {
		this.names_ea = document.getElementsByName("del_each_ck").length;

		if (this.deli_each_ckbox() == 0) {
			alert('삭제할 데이터를 선택해주세요');
		}
		else if (confirm("정말로 삭제하시겠습니까?")) {

			this.deli_idx_data = new Array();

			if (this.names_ea == 1) {
				this.deli_idx_data.push(frm_delivery_list.del_each_ck.value);
			}
			else {
				for (this.f = 0; this.f < frm_delivery_list.del_each_ck.length; this.f++) {
					if (frm_delivery_list.del_each_ck[this.f].checked == true) {
						this.deli_idx_data.push(frm_delivery_list.del_each_ck[this.f].value);
					}
				}


			}

			frm_delivery_list.deliver_idx.value = this.deli_idx_data.join(",");
			frm_delivery_list.method = "post";
			frm_delivery_list.action = "./delete_deliveryok";
			frm_delivery_list.submit();
		}
	}
	//이거 삭제하고 관련 트리거 걸어볼거야
}

export class delivery_modal {
	delivery_load() {
		const list_inreqst = document.getElementsByClassName("list_inreqst");
		this.w = 0;

		while (this.w < list_inreqst.length) {

			switch (list_inreqst[this.w].innerText) {
				case "대기":
					list_inreqst[this.w].style.color = "#808080"; //회색
					break;
				case "진행":
					list_inreqst[this.w].style.color = "#007BFF"; //파란색
					break;
				case "거절":
					list_inreqst[this.w].style.color = "#DC3545"; //빨간색
					break;
				case "완료":
					list_inreqst[this.w].style.color = "#28A745"; //초록색
					break;
			}

			this.w++;
		}



	}

	delvery_enroll() {
		const deliver_qty = frm_del_modal.deliver_qty.value;
		const make_dt = frm_del_modal.make_dt.value;
		const expect_dt = frm_del_modal.expect_dt.value;
		const deliver_area = frm_del_modal.deliver_area.value;
		const deliver_size = frm_del_modal.deliver_size.value;


		if (deliver_qty == "" || make_dt == "" || expect_dt == "" || deliver_area == "" || deliver_size == "") {
			alert('값을 모두 입력해주세요');
		}
		else {
			frm_del_modal.method = "post";
			frm_del_modal.action = "./delivery_enrollok";
			frm_del_modal.submit();
		}

	}
}

export class delivery_page_move {
	go_delivery_return() {
		location.href = "./returnstate";
	}

	go_delivery_enroll() {
		location.href = "./deliveryenroll";
	}

	go_inreq_state() {
		location.href = "./inreqstate";
	}
}