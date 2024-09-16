export class product_search_modal {
	pd_paging(pagenum){
		// 페이지 그룹 계산
		const group_size = 5;
		const current_group = Math.floor((pagenum - 1) / group_size); // 현재 페이지 그룹
		const start_page = current_group * group_size + 1;

		const pd_nm = document.getElementById("pd_nm").value;
		const pd_cd = document.getElementById("pd_cd").value;
		
		//ajax를 통해 해당 페이지의 데이터 리스트 가져오기
		fetch('/product_paging?page=' + pagenum + '&pd_nm=' + pd_nm + '&pd_cd=' + pd_cd, {
			method: 'get'
		})
		.then(function(result_data) {
			return result_data.json();
		})
		.then(function(result_res) {
				var page_ea = Math.ceil(result_res.pd_total / result_res.page_size); // 페이지 개수 계산
				var pagination = document.querySelector("#page_ul");

				
				if (result_res.pd_total != 0) {

					// 기존 페이지 번호 비우기 (이전과 다음 버튼은 남기고 페이지 번호만 제거)
					document.querySelectorAll('#page_ul .page-num').forEach(function(item) {
						item.remove();
					});


					// 페이지 번호 생성 (5개 단위로 제한)
					for (var i = start_page; i < start_page + group_size && i <= page_ea; i++) {
						var li = document.createElement('li');
						li.classList.add('page-item', 'page-link', 'page-num');

						li.innerHTML = `<a class="page-link page-num" href="javascript:void(0)" data-page="${i}">${i}</a>`;

						//선택된 데이터에 active추가
						if (i == pagenum) {
							li.classList.add('active'); // 현재 페이지에 active 클래스 추가
						}

						pagination.insertBefore(li, document.getElementById('next'));

					}

					document.getElementById('prev').replaceWith(document.getElementById('prev').cloneNode(true));
					document.getElementById('next').replaceWith(document.getElementById('next').cloneNode(true));


					// Previous 버튼 이벤트 설정
					document.getElementById('prev').addEventListener('click', function() {
						if (pagenum > 1) {
							const prev_group_las = start_page - 1;
							new product_search_modal().pd_paging(Math.max(prev_group_las - group_size + 1, 1));
						}
					});

					// Next 버튼 이벤트 설정
					document.getElementById('next').addEventListener('click', function() {
						if (pagenum < page_ea) {
							const next_group_first = start_page + group_size;
							new product_search_modal().pd_paging(Math.min(next_group_first, page_ea));
						}
					});


					// 이벤트 위임을 사용하여 클릭 이벤트를 처리
					document.querySelector('#page_ul').addEventListener('click', function(event) {
						// 페이지 번호가 클릭된 경우만 처리 (클래스가 'page-num'인 경우)
						if (event.target.classList.contains('page-num')) {
							var pagenum = event.target.getAttribute('data-page');

							// 이미 이벤트가 처리 중인지 확인
							if (!event.target.classList.contains('clicked')) {
								event.target.classList.add('clicked');
								new product_search_modal().pd_paging(pagenum);
							}
						}
					});

					// 매입처 조회결과 업데이트
					let tableBody = document.querySelector("#product_tbody");
					tableBody.innerHTML = ''; // 테이블 내용 초기화
					result_res.pdlist_part.forEach(function(product, index) {
						let tr = document.createElement('tr');
						tr.innerHTML = `<td style="width:10%;">${result_res.pd_total - (pagenum - 1) * result_res.page_size - index}</td>
                                <td style="width:40%; text-align: center;">${product.product_cd}</td>
                                <td style="width:50%; text-align: center;">${product.product_nm}</td>`;

						tr.addEventListener('click', function() {
							document.getElementById("search_pd_cd").value = product.product_cd; // 상품 코드 자동 입력
							document.getElementById("search_pd_nm").value = product.product_nm; // 상품명 자동 입력
							$('#productModal').modal('hide'); // 모달 닫기
						});

						tableBody.appendChild(tr);
					});
				}
				else {
					// 테이블 내용 초기화
					let tableBody = document.querySelector("#product_tbody");
					tableBody.innerHTML = '';

					// 테이블 내용 생성
					let tr = document.createElement('tr');
					tr.innerHTML = `<td colspan='3' style="text-align: center;">조회된 정보가 없습니다.</td>`;
					tableBody.appendChild(tr);

					// 기존 페이지 번호 비우기 (이전과 다음 버튼은 남기고 페이지 번호만 제거)
					document.querySelectorAll('#page_ul .page-num').forEach(function(item) {
						item.remove();
					});

					// 페이지 번호 1개 생성
					var li = document.createElement('li');
					li.classList.add('page-item', 'page-link', 'page-num', 'active');

					li.innerHTML = `<a class="page-link">1</a>`;
					pagination.insertBefore(li, document.getElementById('next'));
				}
				
			})
			.catch(function(error) {
				console.log(error);
			});
			
	}
}



export class company_search_modal {
	paging(pagenum) {
		// 페이지 그룹 계산
		const group_size = 5;
		const current_group = Math.floor((pagenum - 1) / group_size); // 현재 페이지 그룹
		const start_page = current_group * group_size + 1;

		const comp_nm = document.getElementById("comp_nm").value;
		const comp_cd = document.getElementById("comp_cd").value;

		//ajax를 통해 해당 페이지의 데이터 리스트 가져오기
		fetch('/company_paging?page=' + pagenum + '&comp_nm=' + comp_nm + '&comp_cd=' + comp_cd, {
			method: 'get'
		})
			.then(function(result_data) {
				return result_data.json();
			})
			.then(function(result_res) {

				var page_ea = Math.ceil(result_res.sp_total / result_res.page_size); // 페이지 개수 계산
				var pagination = document.querySelector("#page_ul");

				if (result_res.sp_total != 0) {

					// 기존 페이지 번호 비우기 (이전과 다음 버튼은 남기고 페이지 번호만 제거)
					document.querySelectorAll('#page_ul .page-num').forEach(function(item) {
						item.remove();
					});


					// 페이지 번호 생성 (5개 단위로 제한)
					for (var i = start_page; i < start_page + group_size && i <= page_ea; i++) {
						var li = document.createElement('li');
						li.classList.add('page-item', 'page-link', 'page-num');

						li.innerHTML = `<a class="page-link page-num" href="javascript:void(0)" data-page="${i}">${i}</a>`;

						//선택된 데이터에 active추가
						if (i == pagenum) {
							li.classList.add('active'); // 현재 페이지에 active 클래스 추가
						}

						pagination.insertBefore(li, document.getElementById('next'));

					}


					/* 이걸 이벤트들보다 먼저 실행시켜야 작동하네...*/
					document.getElementById('prev').replaceWith(document.getElementById('prev').cloneNode(true));
					document.getElementById('next').replaceWith(document.getElementById('next').cloneNode(true));


					// Previous 버튼 이벤트 설정
					document.getElementById('prev').addEventListener('click', function() {
						if (pagenum > 1) {
							const prev_group_las = start_page - 1;
							new company_search_modal().paging(Math.max(prev_group_las - group_size + 1, 1));
						}
					});

					// Next 버튼 이벤트 설정
					document.getElementById('next').addEventListener('click', function() {
						if (pagenum < page_ea) {
							const next_group_first = start_page + group_size;
							new company_search_modal().paging(Math.min(next_group_first, page_ea));
						}
					});


					// 이벤트 위임을 사용하여 클릭 이벤트를 처리
					document.querySelector('#page_ul').addEventListener('click', function(event) {
						// 페이지 번호가 클릭된 경우만 처리 (클래스가 'page-num'인 경우)
						if (event.target.classList.contains('page-num')) {
							var pagenum = event.target.getAttribute('data-page');

							// 이미 이벤트가 처리 중인지 확인
							if (!event.target.classList.contains('clicked')) {
								event.target.classList.add('clicked');
								new company_search_modal().paging(pagenum);
							}
						}
					});

					// 매입처 조회결과 업데이트
					let tableBody = document.querySelector("#supplier_tbody");
					tableBody.innerHTML = ''; // 테이블 내용 초기화
					result_res.splist_part.forEach(function(supplier, index) {
						let tr = document.createElement('tr');
						tr.innerHTML = `<td style="width:10%;">${result_res.sp_total - (pagenum - 1) * result_res.page_size - index}</td>
                                <td style="width:40%; text-align: center;">${supplier.supplier_cd}</td>
                                <td style="width:50%; text-align: center;">${supplier.supplier_nm}</td>`;

						tr.addEventListener('click', function() {
							document.getElementById("search_cp_cd").value = supplier.supplier_cd; // 회사 코드 자동 입력
							document.getElementById("search_cp_nm").value = supplier.supplier_nm; // 회사명 자동 입력
							$('#companyModal').modal('hide'); // 모달 닫기
						});

						tableBody.appendChild(tr);
					});
				}
				else {
					// 테이블 내용 초기화
					let tableBody = document.querySelector("#supplier_tbody");
					tableBody.innerHTML = '';

					// 테이블 내용 생성
					let tr = document.createElement('tr');
					tr.innerHTML = `<td colspan='3' style="text-align: center;">조회된 정보가 없습니다.</td>`;
					tableBody.appendChild(tr);

					// 기존 페이지 번호 비우기 (이전과 다음 버튼은 남기고 페이지 번호만 제거)
					document.querySelectorAll('#page_ul .page-num').forEach(function(item) {
						item.remove();
					});

					// 페이지 번호 1개 생성
					var li = document.createElement('li');
					li.classList.add('page-item', 'page-link', 'page-num', 'active');

					li.innerHTML = `<a class="page-link">1</a>`;
					pagination.insertBefore(li, document.getElementById('next'));
				}
			})
			.catch(function(error) {
				console.log(error);
			});
	}
}


export class inboundreq_list {
	//관리 눌렀을 때
	inreq_management(){
		/*
		const product_qty = frm_inreq_manage.product_qty.value;
		const request_idx = frm_inreq_manage.request_idx.value;
		const hope_dt = frm_inreq_manage.hope_dt.value;
		const add_req = frm_inreq_manage.add_req.value;
		*/
		frm_inreq_manage.method = "post";
		frm_inreq_manage.action = "./inreq_modifyok";
		frm_inreq_manage.submit();
	}
	
	
	//검색 조회 눌렀을 때
	inreq_list_search(){
		
		const start_date = document.getElementById("start_date").value;
		const end_date = document.getElementById("end_date").value;
		const search_cp_cd = document.getElementById("search_cp_cd").value;
		const search_pd_cd = document.getElementById("search_pd_cd").value;
		const search_state = document.getElementById("search_state").value;
		const search_operator = document.getElementById("search_operator").value;
		
		if(start_date > end_date){
			alert('정상적인 일자를 입력해주세요');
		}
		else{
			/*
			var data = {
				"start_date" : start_date,
				"end_date" : end_date, 
				"search_cp_cd" : search_cp_cd, 
				"search_pd_cd" : search_pd_cd, 
				"search_state" : search_state, 
				"search_operator" : search_operator, 
				
			};
			
			this.search_data = JSON.stringify(data);
			console.log(this.search_data);
			
			fetch("/inboundreq_search",{
				method : "post",
				headers : {"Content-type":"application/json"},
				body : this.search_data
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
			
			*/
		}
		
	}
	
	
	
	//전체 체크 누르고 끌 때
	all_ckbox() {
		this.all_ck_checked = document.getElementById("all_ck").checked;
		for (this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++) {
			frm_inreq_list.each_ck[this.f].checked = this.all_ck_checked;
		}

	}

	//개별 체크 누르고 끌 때
	each_ckbox() {
		this.ck_count = 0;

		for (this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++) {
			if (frm_inreq_list.each_ck[this.f].checked == true) {
				this.ck_count++;
			}

		}


		if (this.ck_count == frm_inreq_list.each_ck.length) {
			document.getElementById("all_ck").checked = true;
		}
		else {
			document.getElementById("all_ck").checked = false;
		}
		return this.ck_count;
	}

	delete_data() {
		if (this.each_ckbox() == 0) {
			alert('삭제할 데이터를 선택해주세요');
		}
		else if (confirm("정말로 삭제하시겠습니까?")) {
			this.idx_data = new Array();

			for (this.f = 0; this.f < frm_inreq_list.each_ck.length; this.f++) {
				if (frm_inreq_list.each_ck[this.f].checked == true) {
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
	submit_data() {
		if (frm_inreq_modal.product_cd.value == "") {
			alert('상품코드를 입력해주세요');
		}
		else if (frm_inreq_modal.product_qty.value == "") {
			alert('상품수량을 입력해주세요');
		}
		else if (isNaN(frm_inreq_modal.product_qty.value) == true) {
			alert('상품수량은 숫자로 입력해주세요');
		}
		else if (frm_inreq_modal.hope_dt.value == "") {
			alert('희망 납기일자를 선택해주세요 ');
		}
		else {
			frm_inreq_modal.method = "post";
			frm_inreq_modal.action = "./inreq_enrollok";
			frm_inreq_modal.submit();
		}
	}
}