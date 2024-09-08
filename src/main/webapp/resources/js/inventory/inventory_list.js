function toggleCheckbox(row) {
    // 클릭한 행에서 첫 번째 체크박스를 찾음
    var checkbox = row.querySelector('input[type="checkbox"]');
    
    // 체크박스가 존재하면, 체크 상태를 토글
    if (checkbox) {
        checkbox.checked = !checkbox.checked;
    }
}