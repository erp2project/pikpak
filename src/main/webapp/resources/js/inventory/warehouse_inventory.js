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

let selectedZone = null;
let selectedRack = null;
let selectedRackStatus = null;
// 페이지 로드 시 모든 선택 값을 초기화하고 기본값을 설정
document.addEventListener('DOMContentLoaded', function() {
    selectedZone = null;
    selectedRack = null;
    selectedRackStatus = null;
    updateRackInfo(); // 초기 값으로 정보 업데이트
  
});
function selectMenu(element, menu) {
    // 모든 메뉴 항목의 선택 상태를 해제
    document.querySelectorAll('.panel_menu-item a').forEach(function(link) {
        link.classList.remove('active');
    });

    // 선택된 메뉴 항목에 선택 상태 추가
    element.classList.add('active');
}
function selectRack(areaId) {
    // 모든 구역의 선택을 해제
    document.querySelectorAll('.area').forEach(function(area) {
        area.classList.remove('selected');
    });
    
    // 선택된 구역에 클래스 추가
    document.querySelector(`[data-id="${areaId}"]`).classList.add('selected');
    
    // 해당 구역의 랙 정보 업데이트
    document.querySelector('.rack-info p').textContent = areaId + " 구역의 재고 리스트";
}

// 랙 선택 시 정보 변경
document.querySelectorAll('.rack').forEach(rack => {
    rack.addEventListener('click', function() {
        // 모든 랙 초기화
        document.querySelectorAll('.rack').forEach(r => r.classList.remove('selected'));

        // 선택된 랙 강조
        this.classList.add('selected');
        console.log(`${this.textContent} 랙이 선택되었습니다.`);
    });
});


// 구역 선택 시 동작 처리
function selectZone(zoneId) {
    selectedZone = zoneId; // 선택된 구역 저장
    updateRackInfo(); // 정보 업데이트
    
    // 모든 구역 정보 컨테이너를 숨김
    const containers = document.querySelectorAll('.zone-info-container');
    containers.forEach(container => {
        container.classList.remove('active');
    });

    // 선택된 구역에 대한 정보 컨테이너 선택
    const infoContainer = document.getElementById(`info-${zoneId}`);
    
    // 정보 업데이트
    const { 담당자, 연락처, 마지막점검 } = zoneData[zoneId];
    infoContainer.innerHTML = `
        <div class="zone-info">
            <p><strong>담당자:</strong> ${담당자}</p>
            <p><strong>연락처:</strong> ${연락처}</p>
            <p><strong>마지막 점검일시:</strong> ${마지막점검}</p>
        </div>
    `;

    // 선택된 구역의 정보 컨테이너를 활성화
    infoContainer.classList.add('active');
    // 상세 정보 업데이트
    document.getElementById('manager-name').textContent = 담당자;
    document.getElementById('contact-info').textContent = 연락처;
    document.getElementById('zone-status').textContent = 상태 || '정상';
    document.getElementById('total-items').textContent = 총수량 || '데이터 없음';
    document.getElementById('check-history').textContent = 히스토리 || '기록 없음';    
    console.log(zoneId + '이 선택되었습니다.');
}

// 랙 번호 선택 시 동작 처리
function selectRack(rackId) {
    selectedRack = rackId; // 선택된 랙 번호 저장
    console.log('선택된 랙:', selectedRack); // 로그로 확인
    updateRackInfo(); // 정보 업데이트
    
    // 모든 랙 번호의 selected 클래스 제거 및 선택된 랙에 추가
    document.querySelectorAll('.rack-number').forEach(rack => rack.classList.remove('selected'));
    const selectedRackElement = document.querySelector(`.rack-number[data-id="${rackId}"]`);
    if (selectedRackElement) {
        selectedRackElement.classList.add('selected');
    }
}


// 랙 상태 선택 시 동작 처리
function selectRackStatus(level, rackPosition) {
    selectedRackStatus = `${level} · ${rackPosition}`;
    updateRackInfo(); // 정보 업데이트
}

// 구역, 랙 번호, 랙 상태가 모두 선택되었을 때 정보를 업데이트
function updateRackInfo() {
    const rackInfoText = document.getElementById('rack-info-text');
    const inventoryList = document.getElementById('inventory-list'); // 재고 리스트 테이블
    
    if (selectedZone && selectedRack && selectedRackStatus) {
        rackInfoText.textContent = `${selectedZone} 구역 · ${selectedRack} · ${selectedRackStatus}의 재고 리스트`;
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
        rackInfoText.textContent = '선택된 구역 · 랙 번호 · 랙 상태의 재고 리스트';
        inventoryList.innerHTML = ''; // 선택되지 않으면 리스트 초기화
    }
}

