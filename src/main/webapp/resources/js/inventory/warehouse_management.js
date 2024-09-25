document.addEventListener('click', function(event){
	
	//상세보기 클릭시 
	if(event.target.classList.contains('view-details')){
		event.preventDefault();
		const a_check_idx = event.target.getAttribute('data-id');
		fetch(`/getCheckRecordDetails/${a_check_idx}`)
		.then(response => response.json())
		.then(data => {
			document.getElementById('StockCheckZoneDetails').value=data.area_cd;
			document.getElementById('manager_nmDetails').value=data.manager_nm;
			document.getElementById('check_startDetails').value=formatDateTime(data.check_start_dt);
			document.getElementById('check_endDetails').value=formatDateTime(data.check_end_dt);
            document.getElementById('temperatureDetails').value=data.temperature;
			document.getElementById('humidityDetails').value=data.humidity;
            document.getElementById('type1_stockDetails').value = data.type1_stock;
            document.getElementById('type2_stockDetails').value = data.type2_stock;
            document.getElementById('type3_stockDetails').value = data.type3_stock;
			document.getElementById('statementDetails').value=data.statement;
			document.getElementById('notesDetails').value=data.check_log;
		})
		.catch(error => {
			console.log(error);
		})
	}
});

//back-end 에서 받은 시간 커스텀
function formatDateTime(dateTimeString) {
    const dateTime = new Date(dateTimeString);
    
    const year = dateTime.getFullYear();
    const month = ('0' + (dateTime.getMonth() + 1)).slice(-2); // 두 자리로
    const day = ('0' + dateTime.getDate()).slice(-2); // 두 자리로
    const hours = ('0' + dateTime.getHours()).slice(-2); // 두 자리로
    const minutes = ('0' + dateTime.getMinutes()).slice(-2); // 두 자리로
    
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

//점검 등록 제출 
document.getElementById("wareck_enroll").disabled = true; // 기본적으로 비활성화

document.getElementById("wareck_enroll").addEventListener('click',function(){
	const form = document.getElementById("frm_wareck_enroll");
	const formdata = new FormData(form);

    // 각 필드 값을 가져오기
    const fields = {
        managerNm: document.getElementById("manager_nm").value.trim(),
        startDate: document.getElementById("check_start").value,
        endDate: document.getElementById("check_end").value,
        temperature: document.getElementById("temperature").value,
        humidity: document.getElementById("humidity").value,
        statement: document.getElementById("statement").value
    };
    
    // 유효성 검사를 위한 필드별 조건 체크
    const checks = [
        { condition: !fields.managerNm, message: '담당자를 입력하세요.' },
        { condition: !fields.startDate || !fields.endDate, message: '점검 시작 시간과 종료 시간을 입력하세요.' },
        { 
            condition: new Date(fields.startDate) >= new Date(fields.endDate), 
            message: '점검 시작 시간은 종료 시간보다 앞서야 합니다.'
        },
        { condition: isNaN(fields.temperature) || fields.temperature === '', message: '온도를 입력하세요.' },
        { condition: isNaN(fields.humidity) || fields.humidity === '', message: '습도를 입력하세요.' },
        { condition: !fields.statement, message: '점검 상태를 선택하세요.' }
    ];
    
    //유효성 검사 실행
    for (let check of checks) {
        if (check.condition) {
            alert(check.message);
            return;
        }
    }          
            	
	fetch('/insertInspectData',{
		method : "POST",
		body :formdata
	})
	.then(response => response.text())
	.then(data => {
		alert(data);
        if (data.includes("정상적으로 등록")) {
            location.reload();  // 페이지 새로고침
        }
	})
	.catch(error =>{
		console.log(error);
	});
});

//재고 수량 일치 조회 버튼
document.getElementById("checkButton").addEventListener('click',function(){
	const type1Stock = document.getElementById('type1_stock').value;
	const type2Stock = document.getElementById('type2_stock').value;
	const type3Stock = document.getElementById('type3_stock').value;
	
	const zoneId = document.getElementById('StockCheckZone').value;
	if(!zoneId){
		alert('구역을 선택하세요');
		return false;
	}
    if (!type1Stock || isNaN(type1Stock) || !type2Stock || isNaN(type2Stock) || !type3Stock || isNaN(type3Stock)) {
        alert("모든 재고 수량을 입력하세요.");
        return;
    }	
	
	fetch('/getZoneStockData',{
		method : 'POST',
		headers : {
			'content-type':'application/json'
		},
		body : JSON.stringify({
			zoneId : zoneId
		})
	}).then(response => response.json())
	.then(data => {
        let totalType1Stock = 0, totalType2Stock = 0, totalType3Stock = 0;

        // 데이터에 따른 유형별 재고 수량을 집계
        data.forEach(item => {
            if (item.product_type === '대형') totalType1Stock += item.product_qty;
            if (item.product_type === '중형') totalType2Stock += item.product_qty;
            if (item.product_type === '소형') totalType3Stock += item.product_qty;
        });
			
		console.log(totalType1Stock);
		console.log(totalType2Stock);
		console.log(totalType3Stock);
        // 유형별로 입력한 값과 서버에서 가져온 값을 비교
        const isType1Match = (type1Stock == totalType1Stock);
        const isType2Match = (type2Stock == totalType2Stock);
        const isType3Match = (type3Stock == totalType3Stock);

        if (isType1Match && isType2Match && isType3Match) {
            alert('입력한 재고 수량이 모두 일치합니다.');
            wareck_enroll.disabled = false;  // 재고 수량이 일치하면 버튼 활성화
        } else {
            let mismatchMessage = '재고 수량이 일치하지 않습니다:\n';
            if (!isType1Match) mismatchMessage += `일치하지 않는 데이터 : 1유형 재고\n`;
            if (!isType2Match) mismatchMessage += `일치하지 않는 데이터 : 2유형 재고\n`;
            if (!isType3Match) mismatchMessage += `일치하지 않는 데이터 : 3유형 재고\n`;
            alert(mismatchMessage);
             wareck_enroll.disabled = true;
        }   
	}).catch(error => {
		console.log(error);
	});
});