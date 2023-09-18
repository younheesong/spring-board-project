<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>홈</title>
</head>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
<jsp:include page="../../fragments/header.jsp"></jsp:include>
<div class="max-w-5xl mx-auto px-6 lg:px-8 py-20">

    <h2 class="text-center text-3xl font-bold mb-16">📝작성한 글📝️</h2>

    <div class="flex flex-row justify-center gap-10">
        <div class="sm:w-full">

            <div class="h-[600px]">
                <table class="min-w-full divide-y divide-gray-300">
                    <thead>
                    <tr>
                        <th scope="col" class="px-3 py-3.5 text-center text-sm font-semibold text-gray-900">카테고리</th>
                        <th scope="col" class="px-3 py-3.5 text-center text-sm font-semibold text-gray-900">제목</th>
                        <th scope="col" class="px-3 py-3.5 text-center text-sm font-semibold text-gray-900">등록일</th>
                        <th scope="col" class="relative py-3.5 pl-3 pr-4 sm:pr-0">
                            조회수
                        </th>
                    </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                    <c:if test="${!empty boards}">
                        <c:forEach items="${boards}" var="board">
                            <tr onclick="gotoDetail('${board.category}', ${board.id})" class="hover:cursor-pointer">
                                <td class="whitespace-nowrap px-3 py-4 text-sm text-center text-gray-500">${board.category}</td>
                                <td class="whitespace-nowrap px-3 py-4 text-center text-sm text-gray-500">${board.title}</td>
                                <td class="whitespace-nowrap px-3 py-4 text-center text-sm text-gray-500">
                                    <fmt:parseDate value="${board.creationDate}" pattern="yyyy-MM-dd'T'HH:mm"
                                                   var="parsedDateTime" type="both"/>
                                    <fmt:formatDate value="${parsedDateTime}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td class="relative whitespace-nowrap py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0">
                                        ${board.viewsCount}
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty boards}">
                        <tr>
                            <td colspan="5" class="text-center p-20 text-sm font-semibold text-gray-900">등록한 글이 없어요.
                            </td>
                        </tr>
                    </c:if>

                    </tbody>
                </table>
            </div>

            <ul id="paging" class="mt-10"></ul>
        </div>

    </div>
</div>
</body>
<script>
    window.onload = function () {
        let nowPage = ${currentPage} +1;    // 현재 페이지
        let totalPage = ${totalPage};  // 전체 페이지 수

        let firstPage;  // 화면에 출력될 첫 페이지
        for (let i = nowPage; i >= 1; i--) {
            if (i % 5 == 1) {
                firstPage = i;
                break;
            }
        }

        let lastPage;   // 화면에 출력될 마지막 페이지
        let nextButton; // 다음 버튼 출력 여부
        if (firstPage + 4 >= totalPage) {
            lastPage = totalPage;
            nextButton = false;
        } else {
            lastPage = firstPage + 4;
            nextButton = true;
        }

        // HTML 생성
        let pageHtml = "";
        pageHtml += "<nav class='flex items-center justify-between border-t border-gray-200 px-4 sm:px-0'>"
        pageHtml += "<li><a class='inline-flex items-center border-t-2 border-transparent pr-1 pt-4 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700'" +
            " href='" + makeUrl(1) + "'>&laquo;</a></li>";
        if (firstPage != 1) {
            pageHtml += "<li><a class='inline-flex items-center border-t-2 border-transparent pr-1 pt-4 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700' href='" + makeUrl(firstPage - 1) + "'>&lsaquo;</a></li>";
        }
        pageHtml += "<div class='-mt-px flex'>"

        for (let i = firstPage; i <= lastPage; i++) {
            if (i == nowPage) {
                pageHtml += "<li class='page-item active'><a class='inline-flex items-center border-t-2 border-indigo-500 px-4 pt-4 text-sm font-medium text-indigo-600'>" + i + "</a></li>";
            } else {
                pageHtml += "<li class='page-item'><a class='inline-flex items-center border-t-2 border-transparent px-4 pt-4 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700' href='" + makeUrl(i) + "'>" + i + "</a></li>";
            }
        }
        pageHtml += "</div>"

        if (nextButton) {
            pageHtml += "<li><a class='inline-flex items-center border-t-2 border-transparent pr-1 pt-4 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700' href='" + makeUrl(lastPage + 1) + "'>&rsaquo;</a></li>";
        }
        pageHtml += "<li><a class='inline-flex items-center border-t-2 border-transparent pr-1 pt-4 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700' href='" + makeUrl(totalPage) + "'>&raquo;</a></li>";
        pageHtml += "</nav>"

        $("#paging").html(pageHtml);
    }


    const gotoDetail = (category, id) => {
        window.location.href = '/boards/' + category.toString().toLowerCase() + '/' + id;
    }


    const makeUrl = (page) => {
        return "/users/boards" + "?page=" + page;
    }
</script>
</html>