export class outbound_enroll{
	qty_ui(location, lotDetails, quantity, whWarehouseIdx, receivingCd){
	var container = document.getElementById('item_container');
    
        var item = document.createElement('div');
        item.className = 'pickedItem';  // 클래스 이름 설정
    
        // 정보 표시를 위한 텍스트
        var textContent = document.createElement('span');
        textContent.textContent = `${location} - ${lotDetails} - 수량: ${quantity}`;
        item.appendChild(textContent);
    
        // 서버로 보낼 데이터를 담는 hidden input (하나의 입력 필드로 모든 정보 결합)
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'item_data';
        input.value = `${location}&${quantity}&${whWarehouseIdx}&${receivingCd}`;
        item.appendChild(input);
    
        // 삭제 버튼
        var removeButton = document.createElement('button');
        removeButton.textContent = 'X';
        removeButton.onclick = function() {
            container.removeChild(item);
        };
        item.appendChild(removeButton);

        container.appendChild(item);
	}
	
	
	//출고수량 정보를 가지고 오기 위한 ajax (상품코드만 보내주면 됨)
	qty_info(pd_cd){
		
		fetch("/outgoing_locations?product_cd=" + pd_cd, {
			method : "get"
		})
		.then(function(result_data){
			return result_data.json();
		})
		.then(function(data){
			 console.log(data);
            var locationSelect = document.getElementById('location_select');
            var lotSelect = document.getElementById('lot_select');

            // 위치 select 초기화
            locationSelect.innerHTML = '<option value="">위치 선택</option>';
            lotSelect.innerHTML = '<option value="">로트번호/수량 선택</option>'; // 초기화

            // 위치 데이터를 이용해 고유한 위치 목록 생성
            var uniqueLocations = data.reduce(function(acc, current) {
                if (!acc.find(item => item.location_cd === current.location_cd)) {
                    acc.push(current);
                }
                return acc;
            }, []);

            // 고유한 위치로 옵션 추가
            uniqueLocations.forEach(function(item) {
                var option = document.createElement('option');
                option.value = item.location_cd;
                option.textContent = item.location_cd;
                locationSelect.appendChild(option);
            });

            // 위치가 선택될 때 로트번호와 수량 업데이트
            locationSelect.addEventListener('change', function() {
                lotSelect.innerHTML = '<option value="">로트번호/수량 선택</option>';  // 선택 시 초기화

                // 선택된 위치에 해당하는 로트번호와 수량을 추출
                var selectedLocation = this.value;
                var filteredItems = data.filter(function(item) {
                    return item.location_cd === selectedLocation;
                });

                // 필터링된 아이템으로 로트번호/수량 선택 옵션 생성
                filteredItems.forEach(function(item) {
                    var option = document.createElement('option');
                    option.value = item.lot_no;  // 로트번호를 값으로 사용
                    option.textContent = item.lot_no + " / " + item.receiving_qty;  // 텍스트 형식 정의
                    option.setAttribute('data-wh_warehouse_idx', item.wh_warehouse_idx);  // hidden으로 추가할 값
                    option.setAttribute('data-receiving_cd', item.receiving_cd);  // hidden으로 추가할 값
                    lotSelect.appendChild(option);
                });
            });
		})
		.catch(function(error){
			console.log(error);
		});
		
	}
	
	go_out_enroll(){
		const qty_items = document.getElementsByName("item_data");
		console.log(qty_items[0]);
		console.log(qty_items[1]);
		//위치코드, 로트번호, 수량   (idx,receiving_cd)
		if(qty_items.length == 0 || frm_out_enroll.exoutgoing_area.value == "" || frm_out_enroll.expect_dt.value == ""){
			alert('값을 모두 입력해주세요');
		}
		else{
			frm_out_enroll.method = "post",
			frm_out_enroll.action = "./outgoing_enrollok";
			frm_out_enroll.submit();
			//console.log("ok");
		}
	}
}


export class page_move_outbound{
	go_outboundenroll(){
		location.href = './outenroll';	
	}
	
	go_outboudstate(){
		location.href = './outstate';	
	}
	
}