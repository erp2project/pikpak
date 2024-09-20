var inventoryData = {};	//각 구역의 데이터를 저장

$(document).ready(function () {
	// 페이지 최초 출력시는 A구역으로 출력
	loadZoneData('A');
	
	//모달창 출력
	$('#inspectionModal').modal('hide');
	
	//이게 있어야 제대로 데이터가 출력 됨.
    $('[data-toggle="popover"]').popover('dispose').popover({
    	html: true,
        trigger: 'focus',
        placement: 'right'
    });  
    
    //랙과 파트 클릭 시 해당 데이터로 팝오버 
    $('.wms-part').on('click',function(event){
		const partElement = event.currentTarget;
		const level = 5-$(partElement).closest('.wms-rack-level').index();
		const part = $(partElement).index() + 1;
		const rackNumber = $(partElement).closest('.wms-rack').data('rack-number');
		const zoneId = $('.btn.active').text().trim()[0]; //현재 선택된 구역의 id
		//해당 구역에서 선택된 단과 열에 해당하는 데이터 찾음
		const partData = findPartData(zoneId, rackNumber ,level, part);
		//console.log(partData.location_cd);
		
		if(partData) {		
			console.log(partData.location_cd);
			const popoverContent = 
			`위치 : ${partData.product_nm}<br>
			상품명: ${partData.product_nm}<br>
			수량 :${partData.product_qty}<br>
			제조사 :${partData.supplier_nm}`;

			$(partElement).attr('data-content',popoverContent);
			
			$(partElement).popover({
			    html: true,
			    trigger: 'focus',
			    placement: 'right',
			    container: 'body'
			}).popover('show');	//팝오버 표시
		}else{
			$(partElement).attr('data-content','해당 위치에 등록된 재고가 없습니다.');
			$(partElement).popover('show');
		}
	});	
});

function resetRackColors(){
	const allRackParts = document.querySelectorAll('.wms-part');
	allRackParts.forEach(part => {
		part.classList.remove('has-stock','no-stock');
	});
}

//탭으로 선택한 구역의 재고 데이터를 저장시킴
function loadZoneData(zoneId){
	
    // 모든 구역 버튼에서 active 클래스를 제거하고, 선택한 구역 버튼에만 추가
    document.querySelectorAll('.btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.getElementById(`zone-${zoneId.toLowerCase()}-tab`).classList.add('active');	

    // 이전 구역의 CSS 상태 초기화
    resetRackColors();	
	
	//데이터 로드
	fetch('/getZoneData',{
		method : 'post',
		headers :{
			'content-type' : 'application/json'},
			body : JSON.stringify({"area_cd" : zoneId})
	})
	.then(response => response.json())
	.then(data => {
		inventoryData[zoneId] = data.getAreaStockData;	//구역 전체 데이터를 저장
		
		//가져온 데이터를 기반으로 각 위치에 맞는 정보 설정
		updateRackInfo(zoneId,inventoryData[zoneId]);
		
		//재고 데이터를 기반으로 색상 업데이트
		updateRackColors(zoneId);
	}).catch(error => {
		console.log(error);
	});
}

function updateRackInfo(zoneId, zoneData) {
    // 위치별로 상품을 그룹화하기 위한 객체 생성
    const locationItemsMap = {};
    	
    //	zoneData에서 각 위치에 있는 모든 상품을 그룹화
     zoneData.forEach(item => {
        const locationKey = item.location_cd;
        if (!locationItemsMap[locationKey]) {
            locationItemsMap[locationKey] = [];
        }
        locationItemsMap[locationKey].push(item);
    });   
    
    // location_cd를 Rxx-Lx-Px 형식으로 변환하는 함수
    function convertLocationFormatForQuery(locationCd) {
        const rackNumber = locationCd.slice(1, 3); // 랙 번호 (A01 -> 01)
        const rackLevel = locationCd.slice(4, 5);  // 단 (L1 -> L1)
        const rackPart = locationCd.slice(6);      // 열 (P1 -> P1)

        return `R${rackNumber}-L${rackLevel}-P${rackPart}`; // Rxx-Lx-Px 형식 반환
    }    
    //각 위치에 대한 상품 정보 설정 및 popover 설정
    Object.keys(locationItemsMap).forEach(locationKey => {
		const formattedLocation = convertLocationFormatForQuery(locationKey);
		const locationElement = 
		document.querySelector(`[data-location='${formattedLocation}']`);
		
		if(locationElement){
			let popoverContent = locationItemsMap[locationKey].map(item=>`
                    <strong>상품명:</strong> ${item.product_nm}<br>
                    <strong>수량:</strong> ${item.product_qty}<br>
                    <strong>공급자:</strong> ${item.supplier_nm}<br>
                `).join('<hr>');//구분선 추가
                 
                        
            //popover 에 사용할 데이터 설정
            locationElement.setAttribute('data-content',popoverContent);
            
            // 새 popover 설정
            $(locationElement).popover({
                html: true,
                trigger: 'focus',
                placement: 'right',
                container: 'body'
            });            
		}
	});
}

function convertLocationFormat(locationKey) {
    const rackNumber = locationKey.slice(1, 3); // 랙 번호 (01, 02, 03 등)
    const rackLevel = locationKey.slice(4, 5); // 단 (L1, L2, L3, L4)
    const partNumber = locationKey.slice(6); // 열 (P1, P2, P3 등)

    // 새로운 형식으로 변환
    const formattedLocation = `R${rackNumber}-L${rackLevel}-P${partNumber}`;
    
    return formattedLocation;
}

function updateRackColors(zoneId){
	console.log(zoneId+'색 구별 구역');
	//inventoryData에 저장된 해당 구역 데이터를 가져옴
	const zoneData = inventoryData[zoneId];
    // 각 위치의 상품 수를 추적할 객체 생성
    const locationCount = {};	
    
    //데이터를 순회하면서 각 위치별 상품 수를 누적
    zoneData.forEach(item => {
		const locationKey = item.location_cd;
		if(!locationCount[locationKey]){
			locationCount[locationKey]=0;//해당 위치가 처음 등장하면 0으로 초기화
		}
		locationCount[locationKey]++;	//해당 위치에 상품 추가
	});
	
    //모든 랙 위치 요소 초기화
     document.querySelectorAll('.wms-part').forEach(part => {
	   const partLocation = part.getAttribute('data-location');
	   const formattedLocation = partLocation.replace(/R(\d+)-L(\d+)-P(\d+)/, `${zoneId}$1L$2P$3`); // zoneId를 동적으로 삽입
	   const rackLevel = partLocation.slice(5, 6);
	   let maxCapacity;
	    // 단에 따른 최대 수용량 설정
	    if (rackLevel === '1') maxCapacity = 3;  // 1단 (대형)
	    else if (rackLevel === '2' || rackLevel === '3') maxCapacity = 6;  // 2단, 3단 (중형)
	    else if (rackLevel === '4') maxCapacity = 12; // 4단 (소형)
		
	    const currentCount = locationCount[formattedLocation]; // 해당 위치의 누적된 상품 수	   
	    //이전 css 삭제
	    part.classList.remove('no-stock', 'assigned-no-stock', 'no-space', 'partial-space');
	    // 색상 설정 로직
	    if (currentCount === undefined) {
	        // 해당 위치에 상품 데이터가 없을 경우 초록색 (할당되지 않은 위치)
	        part.classList.add('no-stock');
	    } else if (currentCount === 0) {
	        // 민트색 (할당된 위치, 재고 없음)
	        part.classList.add('assigned-no-stock');
	    } else if (currentCount >= maxCapacity) {
	        // 빨간색 (공간 다 찬 상태)
	        part.classList.add('no-space');
	    } else {
	        // 파란색 (일부 공간 남음)
	        part.classList.add('partial-space');
	    }	    
    });   
}

//특정 구역, 단, 열에 해당하는 데이터를 찾는 함수
function findPartData(zoneId,rackNumber,level,part){
	if(!inventoryData[zoneId]){
		return null;	//해당 구역에 데이터 없을 경우 
	}
    // rackNumber와 level을 각각 두 자리로 맞추기 위해 padStart 사용
    const formattedRackNumber = String(rackNumber).padStart(2, '0'); // 2자리로 맞추기
    const formattedLevel = String(level).padStart(1, '0'); // 1자리로 맞추기	
    
	//location_cd 생성
	const locationCode = `${zoneId}${formattedRackNumber}L${formattedLevel}P${part}`;
	//location_cd 가 정확히 일치하는 데이터를 찾음
	return inventoryData[zoneId].find(item => item.location_cd == locationCode);
}

