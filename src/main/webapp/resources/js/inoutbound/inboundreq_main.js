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
}