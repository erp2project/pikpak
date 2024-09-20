function toggleCheckbox(row) {
    // 클릭한 행에서 첫 번째 체크박스를 찾음
    var checkbox = row.querySelector('input[type="checkbox"]');
    
    // 체크박스가 존재하면, 체크 상태를 토글
    if (checkbox) {
        checkbox.checked = !checkbox.checked;
    }
}
//조회 버튼 클릭 (form전송)
document.getElementById('searchButton').addEventListener('click', function(){
	event.preventDefault();
	//폼데이터 가져오기
	const form = document.getElementById('searchForm');
	const formData = new FormData(form);
	const searchData = {};
	
	//입력되지않은값 제외하고 searchParams에 추가
	formData.forEach((value, key) => {
		if(value){
			searchData[key] = value;
		}
	});
	console.log(searchData);
	
	fetch('/getSearchData',{
		method : 'POST',
		headers : {
			'content-type' : 'application/json'
		},
		body : JSON.stringify(searchData)
	})
	.then(response => response.json())
	.then(data => {
		console.log(data);
		renderSearchResults(data);
		/*
		if(data=='ok'){
			alert('조회되었습니다.');
			
		}else{
			alert('조회된 정보가 없습니다.')
		}*/
	})
	.catch(error=> console.error('Error fetching inventory data:',error));
})


function renderSearchResults(data){
	const resultsContainer = document.getElementById('results');
	console.log(resultsContainer);
	resultsContainer.innerHTML = '';
	
	if(data.length ==0){
		const noResults = document.createElement('tr');
		noResults.innerHTML = `<td colspan="7"> 검색 결과가 없습니다.</td>`;
		resultsContainer.appendChild(noResults);
		return;
	}
	
	data.forEach(item => {
		const row = document.createElement('tr');
		row.innerHTML=`
			<td>${item.location_cd}</td>
			<td>${item.product_cd}</td>
			<td>${item.supplier_nm}</td>
			<td>${item.product_nm}</td>
			<td>${item.product_qty}</td>
			<td style="text-align: center;"></td>
			<td></td>
			<td style="text-align: center;">미정</td>
			<td style="text-align: center;">
			<button class="btn btn-info"
			th:onclick="event.stopPropagation(); openModal([[${item.wh_warehouse_idx}]])"
			data-toggle="modal" 
			data-target="#inventoryModal">관리</button>
			</td>
		`;
		resultsContainer.appendChild(row);
	})
}


//관리 버튼 클릭 시 모달창 열림
function openModal(wh_warehouse_idx){
	/*
	const wh_warehouse_idx = buttonElement.getAttribute('data-warehouse-idx');
	console.log(wh_warehouse_idx);
	*/
	fetch('/getInventoryDetails?wh_warehouse_idx='+wh_warehouse_idx,{
		method : 'GET',
	})
	.then(response => response.json())
	.then(data => {
		const details = data.details;
		const safety_qty = data.safety_inventory;
		
		document.getElementById('productCode').value= details.product_cd;
		document.getElementById('productName').value= details.product_nm;
		document.getElementById('locationCode').value= details.location_cd;
		document.getElementById('currentStock').value= details.product_qty;	//현재 재고
		
		document.getElementById('incomingStock').value= "";	//입고 테이블에서 가져와야함
		document.getElementById('outgoingStock').value= "";
		
		document.getElementById('safetyStock').value= safety_qty;	//안전 재고
		
		document.getElementById('notes').value = details.inventory_log;
		
	$('#inventoryModal').modal('show');		
		
	})
	.catch(error => console.error('Error fetching inventory data:',error));
}

//상품코드를 선택 -> 상품명 드롭다운을 업데이트
function fetchProductDataByCode(product_cd) {
	if(!product_cd){
		return ;
	}
	fetch('/getpdcode',{
		method : 'post',
		headers : {
			'content-type': 'application/json',
		},
		body : JSON.stringify({product_cd:product_cd}),
	})
	.then(response=>response.json())
	.then(data => {
		document.getElementById('product_nm').value = data.product_nm;
	})
	.catch(error=> console.error('Error fetching product data:', error));
	
}

//상품명을 선택 -> 상품코드 드롭다운을 업데이트
function fetchProductDataByName(product_nm) {
    if (!product_nm) {
        return; // 선택된 값이 없으면 아무 작업도 하지 않음
    }

    fetch('/getProductByName', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ product_nm: product_nm }),
    })
    .then(response => response.json())
    .then(data => {
        // 서버에서 받은 상품코드 데이터를 상품코드 드롭다운에 반영
        document.getElementById('product_cd').value = data.product_cd;
    })
    .catch(error => console.error('Error fetching product data:', error));
}

//회사코드를 선택 -> 회사명을 업데이트
function fetchCompanyDataByCode(supplier_cd){
	if(!supplier_cd){
		return ;
	}
		
	fetch('/getCompanyByCode',{
		method : 'post',
		headers : {
		'content-type': 'application/json',
	},
	body : JSON.stringify({supplier_cd:supplier_cd}),			
	})
	.then(response => response.json())
	.then(data => {
		document.getElementById('supplier_nm').value=data.supplier_nm;
	})
	.catch(error => console.error('Error fetching product data:', error));
}

//회사명선택 -> 회사코드 업데이트
function fetchCompanyDataByName(supplier_nm){
	if(!supplier_nm){
		return;
	}
	fetch('/getCompanyByName',{
		method : 'post',
		headers : {
			'content-type':'application/json',
		},
		body : JSON.stringify({supplier_nm : supplier_nm }),
	})
	.then(response => response.json())
	.then (data => {
		document.getElementById('supplier_cd').value = data.supplier_cd;
	})
	.catch(error => console.error('Error fetching company data :',error));
}