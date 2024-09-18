export class delivery_list{
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