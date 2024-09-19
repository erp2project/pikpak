export class inreq_list{
	//입고요청에 대한 거절 버튼 클릭시
	reject_list(inreq_idx, product_qty, remain_qty, request_st){
		this.idx = "request_idx=" + inreq_idx;
		
		if(product_qty != remain_qty){
			alert("'진행' 상태의 요청은 거절하실 수 없습니다.");
		}
		else if(request_st == "거절"){
			alert('이미 거절된 요청입니다.');
		}
		else if(confirm("해당 입고요청을 거절하시겠습니까?")){
			fetch('/reject_deliverylist',{
				method : "post",
				headers : {"Content-type":"application/x-www-form-urlencoded"},
				body : this.idx
			})
			.then(function(result_data){
				return result_data.text();
			})
			.then(function(result_res){
				if(result_res == "ok"){
					alert('해당 입고요청이 거절되었습니다.');
					location.reload();
				}
			})
			.catch(function(error){
				console.log(error);
			});
		}
		
	}
	
	//거절로 상태변화된 리스트에 대해 납품 클릭 못하게 하기
	
	
}


export class delivery_list{
	//납품등록 관리 버튼 클릭시
	decide_delivery(deliveryData){
	
	//상태는 버튼 비활성화할 때나 필요
		
		this.exreceiving_data = {
			"request_cd" : deliveryData.requestCd,
			"deliver_cd" : deliveryData.deliverCd,
			"supplier_cd" : deliveryData.supplierCd,
			"product_cd" : deliveryData.productCd,
			"exreceiving_qty" : deliveryData.deliverQty,
			"exreceiving_size" : deliveryData.deliverSize,
			"exreceiving_area" : deliveryData.deliverArea,
			"make_dt" : deliveryData.makeDt
		}
		
		if(confirm('실제 배송이 완료된 데이터 코드인지 확인해주세요. 배송 확정 하시겠습니까?')){
			fetch('/insert_exreceiving',{
				method : "post",
				headers : {"Content-type":"application/json"},
				body : JSON.stringify(this.exreceiving_data)
			})
			.then(function(result_data){
				return result_data.text();
			})
			.then(function(result_res){
				console.log(result_res);
			})
			.catch(function(error){
				console.log(error);
			});
		}
		
	}
	
	//전체 체크 누르고 끌 때
	deli_all_ckbox() {
		this.all_ck_checked = document.getElementById("del_all_ck").checked;
		
		for (this.f = 0; this.f < frm_delivery_list.del_each_ck.length; this.f++) {
			frm_delivery_list.del_each_ck[this.f].checked = this.all_ck_checked;
		}

	}

	//개별 체크 누르고 끌 때
	deli_each_ckbox() {
		this.ck_count = 0;

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
		return this.ck_count;
	}
	
	//납품등록 리스트 삭제
	delete_delivery() {
		if (this.deli_each_ckbox() == 0) {
			alert('삭제할 데이터를 선택해주세요');
		}
		else if (confirm("정말로 삭제하시겠습니까?")) {
			this.deli_idx_data = new Array();

			for (this.f = 0; this.f < frm_delivery_list.del_each_ck.length; this.f++) {
				if (frm_delivery_list.del_each_ck[this.f].checked == true) {
					this.deli_idx_data.push(frm_delivery_list.del_each_ck[this.f].value);
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

export class delivery_modal{
	delivery_load(){
		const list_inreqst = document.getElementsByClassName("list_inreqst");
		this.w = 0;
		
		while(this.w < list_inreqst.length){
			
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
	
	delvery_enroll(){
		const deliver_qty = frm_del_modal.deliver_qty.value;
		const make_dt = frm_del_modal.make_dt.value;
		const expect_dt = frm_del_modal.expect_dt.value;
		const deliver_area = frm_del_modal.deliver_area.value;
		const deliver_size = frm_del_modal.deliver_size.value;
		
		
		if(deliver_qty == "" || make_dt == "" || expect_dt == "" || deliver_area == "" || deliver_size == ""){
			alert('값을 모두 입력해주세요');
		}
		else{
			frm_del_modal.method = "post";
			frm_del_modal.action = "./delivery_enrollok";
			frm_del_modal.submit();
		}
		
	}
}

export class delivery_page_move {
	go_delivery_enroll() {
		location.href = "./deliveryenroll";
	}
	
	go_inreq_state() {
		location.href = "./inreqstate";
	}
}