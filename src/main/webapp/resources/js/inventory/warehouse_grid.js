var inventoryData = {};	//각 구역의 데이터를 저장

document.addEventListener('DOMContentLoaded', function() {
	loadZoneData('A');	//페이지 최초 출력시는 A구역으로 출력
});

//탭으로 선택한 구역의 재고 데이터를 저장시킴
function loadZoneData(zoneId){
	
    // 모든 구역 버튼에서 active 클래스를 제거하고, 선택한 구역 버튼에만 추가
    document.querySelectorAll('.btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.getElementById(`zone-${zoneId.toLowerCase()}-tab`).classList.add('active');	
	
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
	}).catch(error => {
		console.log(error);
	});
}

$(document).ready(function () {
    // Popover 활성화
    $('[data-toggle="popover"]').popover({
        html: true,
        trigger: 'focus',
        placement: 'right',
        container: 'body'  // popover가 body에 붙어서 표시되도록 설정
    });
    
    //랙과 파트 클릭 시 해당 데이터로 팝오버 
    $('.wms-part').on('click',function(){
		const level = 5-$(this).closest('.wms-rack-level').index();
		const part = $(this).index() + 1;
		const rackNumber = $(this).closest('.wms-rack').data('rack-number');
		const zoneId = $('.btn.active').text().trim()[0]; //현재 선택된 구역의 id
		//해당 구역에서 선택된 단과 열에 해당하는 데이터 찾음
		const partData = findPartData(zoneId, rackNumber ,level, part);
		
		if(partData) {
			const popoverContent = 
			`위치 : L${level}-P${part}<br>상품명: ${partData.product_nm}<br>수량 :${partData.product_qty}`;
			
			$(this).attr('data-content',popoverContent);
			$(this).popover('show');	//팝오버 표시
		}else{
			$(this).attr('data-content','해당 위치에 등록된 재고가 없습니다.');
			$(this).popover('show');
		}
	});	
});

//특정 구역, 단, 열에 해당하는 데이터를 찾는 함수
function findPartData(zoneId,rackNumber,level,part){
	if(!inventoryData[zoneId]){
		return null;	//해당 구역에 데이터 없을 경우 
	}
    // rackNumber와 level을 각각 두 자리로 맞추기 위해 padStart 사용
    const formattedRackNumber = String(rackNumber).padStart(2, '0'); // 2자리로 맞추기
    const formattedLevel = String(level).padStart(1, '0'); // 1자리로 맞추기	
    
	//선택된 구역, 랙, 단, 열을 기반으로 location_cd 를 구성
	const locationCode = `${zoneId}${formattedRackNumber}L${formattedLevel}P${part}`;
	console.log(locationCode);
	//location_cd 가 정확히 일치하는 데이터를 찾음
	return inventoryData[zoneId]
	.find(item => item.location_cd == locationCode);
}

// 팝업창 띄우기 예시
$(document).ready(function() {
  $('#inspectionModal').modal('show');
});
