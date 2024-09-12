export class inboundreq_list{
	//전체 체크 누르고 끌 때
	all_ckbox(){
		this.all_ck_checked = document.getElementById("all_ck").checked;
		for(this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++){
			frm_inreq_list.each_ck[this.f].checked = this.all_ck_checked;
		}
		
	}
	
	//개별 체크 누르고 끌 때
	each_ckbox(){
		this.ck_count = 0;
		
		for(this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++){
			if(frm_inreq_list.each_ck[this.f].checked == true){
				this.ck_count++;			
			}
			
		}
		
		
		if(this.ck_count == frm_inreq_list.each_ck.length){
			document.getElementById("all_ck").checked = true;
		}
		else{
			document.getElementById("all_ck").checked = false;
		}
		return this.ck_count;
	}
	
	delete_data(){
		if(this.each_ckbox() == 0){
			alert('삭제할 데이터를 선택해주세요');
		}
		else if(confirm("정말로 삭제하시겠습니까?")){
			this.idx_data = new Array();
			
			for(this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++){
				if(frm_inreq_list.each_ck[this.f].checked == true){
					this.idx_data.push(frm_inreq_list.each_ck[this.f].value);		
				}
			}
			
			frm_inreq_list.request_idx.value = this.idx_data.join(",");
			
			frm_inreq_list.method = "post";
			frm_inreq_list.action = "./delete_inreqok";
			frm_inreq_list.submit();
		}
	}
}


export class page_move {
	go_inboundreq() {
		location.href = "./inboundreq"
	}

	go_exreceiving() {
		location.href = "./exreceiving"
	}

	go_recvenroll() {
		location.href = "./recvenroll"
	}
}

export class inboundreq_enroll {
	/* 아놔..이건 datalist쓸 때
	//[[상품코드,상품명,회사명],[상품코드,상품명,회사명],[상품코드,상품명,회사명]...] 이차배열 리턴
	product_info() {
		this.options = document.querySelectorAll("#products option"); //옵션 태그의 데이터

		this.rows = this.options.length; //행(그룹) => 옵션 개수만큼
		this.cols = 3;	//열(데이터)

		this.option_data = new Array(this.rows); //이차배열 생성

		this.w = 0;
		while (this.w < this.rows) {
			//각 행 배열로 초기화
			this.option_data[this.w] = [];

			//각 행마다 [상품코드, 상품명, 회사명]
			this.option_data[this.w].push(this.options[this.w].value);
			this.option_data[this.w].push(this.options[this.w].dataset.productName);
			this.option_data[this.w].push(this.options[this.w].dataset.supplierName);

			this.w++;
		}

		return this.option_data;
	}

	//상품코드 변경할 때마다 상품명, 회사명 자동 입력
	insert_pdvalue() {
		this.pd_datas = this.product_info(); //상품코드에 대한 정보를 담고있는 이차배열

		this.pdcode = frm_inreq_modal.product_cd.value; //현재 사용자가 입력한 값
		this.pdname = frm_inreq_modal.product_nm; //현재 사용자가 입력한 상품코드에 대한 상품명
		this.spname = frm_inreq_modal.supplier_nm; //현재 사용자가 입력한 상품코드에 대한 회사명
		
		this.w = 0;
		while (this.w < this.pd_datas.length) {
			if (this.pdcode == this.pd_datas[this.w][0]) {
				this.pdname.value = this.pd_datas[this.w][1];
				this.spname.value = this.pd_datas[this.w][2];
				break;
			}
			this.w++;
		}
	}
	*/
	
	//등록을 위해 값 날리기
	submit_data(){
		if(frm_inreq_modal.product_cd.value == ""){
			alert('상품코드를 입력해주세요');	
		}
		else if(frm_inreq_modal.product_qty.value == ""){
			alert('상품수량을 입력해주세요');
		}
		else if(isNaN(frm_inreq_modal.product_qty.value) == true){
			alert('상품수량은 숫자로 입력해주세요');
		}
		else if(frm_inreq_modal.hope_dt.value == ""){
			alert('희망 납기일자를 선택해주세요 ');
		}
		else{
			frm_inreq_modal.method = "post";
			frm_inreq_modal.action = "./inreq_enrollok";
			frm_inreq_modal.submit();
		}
	}
}