export class delivery_modal{
	delivery_load(){
		//여기서 뭘할거냐면 만약 남은수량이 0이 되면 ajax로 해당 idx값을 보내서 진행으로 업데이트 시키라고 하려고??? 가 아닌데....아닌데....
		const remain_qty = document.getElementById("remain_qty").value; //남은 수량
		console.log(remain_qty);	
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