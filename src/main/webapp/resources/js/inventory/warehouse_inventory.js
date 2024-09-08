// 각 구역의 정보 데이터
const zoneData = {
    A: {
        담당자: '홍길동',
        연락처: '010-1234-5678',
        마지막점검: '2024/08/08 23:50 ~ 00:00',
        상태: '정상',
        총수량: 200,
        히스토리: '2024/08/01 ~ 정상'
    },
    B: {
        담당자: '이순신',
        연락처: '010-9876-5432',
        마지막점검: '2024/08/10 14:20 ~ 15:00',
        상태: '정상',
        총수량: 150,
        히스토리: '2024/08/05 ~ 정상'
    },
    C: {
        담당자: '김유신',
        연락처: '010-4567-8910',
        마지막점검: '2024/08/15 11:30 ~ 12:00',
        상태: '점검 필요',
        총수량: 100,
        히스토리: '2024/08/07 ~ 문제 발생'
    },
    D: {
        담당자: '강감찬',
        연락처: '010-1234-6789',
        마지막점검: '2024/08/20 10:00 ~ 11:00',
        상태: '정상',
        총수량: 300,
        히스토리: '2024/08/15 ~ 정상'
    }
};

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
    
    
    // 정보 업데이트
    const { 담당자, 연락처, 마지막점검, 상태, 총수량, 히스토리 } = zoneData[zoneId];
    infoContainer.innerHTML = `
        <div class="zone-info">
            <p><strong>담당자:</strong> ${담당자}</p>
            <p><strong>연락처:</strong> ${연락처}</p>
            <p><strong>마지막 점검일시:</strong> ${마지막점검}</p>
            <p><strong>상태:</strong> ${상태}</p>
            <p><strong>총수량:</strong> ${총수량}</p>
            <p><strong>히스토리:</strong> ${히스토리}</p>
        </div>
    `;

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
        console.log("다 선택함");
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
        inventoryList.innerHTML = ''; // 선택되지 않으면 리스트 초기화
    }
}

