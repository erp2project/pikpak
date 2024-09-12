function toggleCheckbox(row) {
    // 클릭한 행에서 첫 번째 체크박스를 찾음
    var checkbox = row.querySelector('input[type="checkbox"]');
    
    // 체크박스가 존재하면, 체크 상태를 토글
    if (checkbox) {
        checkbox.checked = !checkbox.checked;
    }
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
	}).then(response=>response.json())
	.then(data => {
		document.getElementById('product_nm').value = data.product_nm;
	}).catch(error=> console.error('Error fetching product data:', error));
	
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