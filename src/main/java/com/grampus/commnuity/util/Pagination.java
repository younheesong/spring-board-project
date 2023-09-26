package com.grampus.commnuity.util;

import lombok.Getter;

@Getter
public class Pagination {
    /* 페이지 당 레코드 수*/
    int pageSize = 10;

    /* 전체 레코드 수 */
    int totalRecord = 0;

    /* 현재 페이지 */
    int page = 1;

    /* 전체 페이지 */
    int totalPage = 1;

    /* 조회 시작 인덱스 */
    int startIdx = 0;

    public Pagination(int totalRecord, int page) {
        this.totalRecord = totalRecord;
        this.page = page;
        calcTotalPage();
        calcStartIdx();
    }

    private void calcTotalPage(){
        totalPage = totalRecord / pageSize;
        if(totalRecord % pageSize !=0){
            totalPage++;
        }
    }

    private void calcStartIdx(){
        startIdx = (page-1) * pageSize;
    }
}
