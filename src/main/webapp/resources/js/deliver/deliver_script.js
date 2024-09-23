document.addEventListener('DOMContentLoaded', function() {
    var openPopupBtn = document.getElementById('openPopupBtn');
    var closePopupBtn = document.getElementById('closePopupBtn');
    var closePopupBtn2 = document.getElementById('closePopupBtn2');
    var popupContainer = document.getElementById('popupContainer');
    var productQuantity = document.getElementById('productQuantity');	//납품 수량
    var product_qty = document.getElementById('product_qty');			//상품 수량
    var enrollBtn = document.getElementById('enrollBtn');
    var madeDate = document.getElementById('madeDate');
    var selectElement = document.getElementById('number-select');

    // Open the popup
    openPopupBtn.addEventListener('click', function() {
        popupContainer.style.display = 'flex';
    });

    // Close the popup
    closePopupBtn.addEventListener('click', function() {
        popupContainer.style.display = 'none';
    });
    
    closePopupBtn2.addEventListener('click', function() {
        popupContainer.style.display = 'none';
    });

    // Optionally close the popup when clicking outside of it
    window.addEventListener('click', function(event) {
        if (event.target === popupContainer) {
            popupContainer.style.display = 'none';
        }
    });
    
    //수량 체크
	function qtyck(){
			
		var qty = productQuantity.value;
		var valid = /^[1-9]\d*$/.test(qty);
		
		if(!valid || qty.includes('.')){
			productQuantity.value = "";
			if(qty != 0){
				alert('잘못된 값입니다.');				
			}
		}
	}
	
	productQuantity.addEventListener('input',qtyck);
    
    //납품 소요기간
    for (let i = 1; i <= 30; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        selectElement.appendChild(option);
    }
    
    //등록 버튼
    enrollBtn.addEventListener('click', function() {
        if(productQuantity.value=="" || madeDate.value==""){
			alert('입력하지 않은 값이 존재합니다.');
		}
		else if(productQuantity.value>product_qty.value){
			alert('납품 수량이 입고 요청 수량보다 많습니다.');
		}
		else{
			console.log("ok");
		}
    });
    
});