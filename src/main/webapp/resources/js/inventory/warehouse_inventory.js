// 각 구역의 정보 데이터

var selectedZone = null;
var selectedRack = null;
var selectedRackStatus = null;


// 페이지 로드 시 모든 선택 값을 초기화하고 기본값을 설정
document.addEventListener('DOMContentLoaded', function() {
    selectedZone = null;
    selectedRack = null;
    selectedRackStatus = null;
    updateRackInfo(); // 초기 값으로 정보 업데이트
  
});
//창고별 재고관리 / 창고관리 탭
function selectMenu(element, menu) {
    // 모든 메뉴 항목의 선택 상태를 해제
    document.querySelectorAll('.panel_menu-item a').forEach(function(link) {
        link.classList.remove('active');
    });

    // 선택된 메뉴 항목에 선택 상태 추가
    element.classList.add('active');
}

// 알림 메시지 함수 (alert로 대체)
function showAlertMessage() {
    alert('먼저 구역을 선택하세요!');
}

// 구역 선택 시 
function selectZone(zoneId) {
    selectedZone = zoneId; // 선택된 구역 저장
    updateRackInfo(); // 정보 업데이트

    // 선택된 구역에 대한 정보 컨테이너 선택
    const infoContainer = document.getElementById(`info-${zoneId}`);    
    
    // 이미 열려 있는 구역을 클릭하면 닫기
    if (infoContainer.classList.contains('active')) {
        infoContainer.classList.remove('active');
        return; // 닫혔으므로 함수를 종료
    }    

    // 모든 구역 정보 컨테이너를 숨김
    const containers = document.querySelectorAll('.zone-info-container');
    containers.forEach(container => {
        container.classList.remove('active');
    });
    
    
    //ajax 로 구역 정보 + 그 구역의 재고 정보들 가져옴
    fetch('/getAreadata',{
		method : 'POST',
		headers : {
			'content-type' : 'application/json'
		},body : JSON.stringify({"area_cd" :zoneId})
	})
	.then(response => response.json())
	.then(data =>{
		const {getAreaData,getAreaStockData} = data;
		
		//구역정보 없데이트
		const { manager_nm, manager_tel, lastcheck_start_dt, lastcheck_end_dt, area_st, full_capacity,available_space} = getAreaData;
		infoContainer.innerHTML=`
			<div class="zone-info">
				<p><strong>담당자:</strong> ${manager_nm}</p>
				<p><strong>연락처:</strong> ${manager_tel}</p>
				<p><strong>마지막 점검일시:</strong><br> ${lastcheck_start_dt}<br> ~ ${lastcheck_end_dt}</p>
				<p><strong>상태:</strong> ${area_st}</p>
				<p><strong>가용 적재 용량:</strong> ${available_space}</p>
				<p><strong>구역 전체 적재용량:</strong> ${full_capacity}</p>
			</div>	
		`;
		
		//재고 리스트 업데이트
		const tablebody = document.querySelector('.table2 tbody');
		tablebody.innerHTML = '';
		
        if (getAreaStockData.length > 0) {
            getAreaStockData.forEach(item => {
                const row = `<tr>
                                <td>${item.product_cd}</td>
                                <td>${item.location_cd}</td>
                                <td>${item.product_nm}</td>
                                <td>${item.product_qty}</td>
                            </tr>`;
                tablebody.innerHTML += row;
            });
        } else {
            tablebody.innerHTML = '<tr><td colspan="4">등록된 재고가 없습니다.</td></tr>';
        }		
		
		
		
	    // 선택된 구역의 정보 컨테이너를 활성화 (슬라이드 효과)
	    setTimeout(() => {
	        infoContainer.classList.add('active');
	    }, 100); // 약간의 딜레이를 줘서 더 부드럽게
	    
	    // 구역 선택 시 초기화
	    selectedRack = null;
	    selectedRackStatus = null;   
	    
	    // 모든 구역의 선택을 해제
	    document.querySelectorAll('.zone').forEach(function(area) {
	        area.classList.remove('selected');
	    });     
	    
	    // 선택된 구역에 대한 정보 출력
	   	document.getElementById('rack-info-text').textContent = `${selectedZone} 구역의 재고 리스트`;
		
		document.querySelector(`[data-id="${zoneId}"]`).classList.add('selected');
	}).catch(error=>{
		console.log(error);
	});

}

// 랙 선택 시 
function selectRack(areaId) {
	
    if (!selectedZone) {
        showAlertMessage(); // 구역을 선택하지 않았으면 경고 메시지
        return;
    }
    
    selectedRack = areaId;
    updateRackInfo(); // 정보 업데이트  
      	
    // 모든 구역의 선택을 해제
    document.querySelectorAll('.area').forEach(function(area) {
        area.classList.remove('selected');
    });
    
    // 선택된 구역에 클래스 추가
    document.querySelector(`[data-id="${areaId}"]`).classList.add('selected');
    
    // 선택된 랙 정보 출력
    document.getElementById('rack-info-text').textContent = `${selectedZone} 구역 · ${selectedRack} 번 랙의 재고 리스트`;
}

// 단, 열번호 선택 시
function inventory_specific_position(level_part) {
    if (!selectedZone) {
        showAlertMessage(); // 구역을 선택하지 않았으면 경고 메시지
        return; // 함수 종료 (빨간색이 되지 않도록)
    }else if(!selectedRack){
		alert('랙번호를 선택하세요.');
		return;
	}

    // 모든 랙의 선택 해제
    document.querySelectorAll('.rack').forEach(r => r.classList.remove('selected'));

    // 선택된 랙에 `selected` 클래스 추가
    level_part.classList.add('selected');

    // 선택된 랙에 대한 추가 정보 처리
    const level = level_part.closest('.level').getAttribute('data-level');
    const rackPosition = level_part.getAttribute('data-id');

    // 열번호 선택 처리
    selectRackStatus(level, rackPosition);
}

// 모든 `.rack` 요소에 대해 클릭 이벤트 추가
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.rack').forEach(function(rack) {
        rack.addEventListener('click', function() {
            inventory_specific_position(this); // 클릭한 랙을 함수로 전달하여 처리
        });
    });
});


// 모든 `.rack` 요소에 대해 클릭 이벤트 추가
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.rack').forEach(function(rack) {
        rack.addEventListener('click', function() {
            const level = this.closest('.level').getAttribute('data-level'); // 해당 열의 level 값
            const rackPosition = this.getAttribute('data-id'); // 해당 랙의 rackPosition 값
            
            // 선택된 level과 rackPosition을 기반으로 열번호 선택 처리
            selectRackStatus(level, rackPosition);
        });
    });
});

// 열번호 선택 시 동작 처리
function selectRackStatus(level, rackPosition) {
    if (selectedZone && selectedRack) {
	    selectedRackStatus = `${level} · ${rackPosition}`;
	    updateRackInfo(); // 정보 업데이트		
		}			
}

// 구역, 랙 번호, 랙 상태가 모두 선택되었을 때 정보를 업데이트
function updateRackInfo() {
    const rackInfoText = document.getElementById('rack-info-text');
    const inventoryList = document.getElementById('inventory-list'); // 재고 리스트 테이블
    
    if (selectedZone && selectedRack && selectedRackStatus) {
        rackInfoText.textContent = `${selectedZone} 구역 · ${selectedRack} 번 랙의  ${selectedRackStatus}의 재고 리스트`;
        // 샘플 데이터로 재고 리스트를 업데이트 (이 부분에 실제 데이터를 넣어야 함)
        inventoryList.innerHTML = `
            <tr>
                <td>상품코드01</td>
                <td>${selectedZone}-${selectedRack}-${selectedRackStatus}</td>
                <td>상품명01</td>
                <td>10</td>
            </tr>
        `;
    } else {
        rackInfoText.textContent = '선택된 값이 없음';
    }
}

