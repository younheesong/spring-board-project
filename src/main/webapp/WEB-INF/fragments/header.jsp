<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

</head>
<script src="https://cdn.tailwindcss.com"></script>
<body>
<header class="bg-white">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div class="flex flex-1">
            <a href="/" class="-m-1.5 p-1.5">
                <span class="font-bold text-lg" >Community</span>
            </a>
        </div>

        <div class="flex flex-1 justify-end">
            <sec:authorize access="isAnonymous()">
                <a href="/users/login" class="text-sm font-semibold leading-6 text-gray-900">Log in <span aria-hidden="true">&rarr;</span></a>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                관리자 페이지
            </sec:authorize>

            <sec:authorize access="isAuthenticated()" >
                <a href="/users/boards" class="hover:border-b">
                마이페이지
                </a>
                <a href="/users/logout" class="text-sm font-semibold leading-6 ml-4 text-gray-900">로그아웃</a>
            </sec:authorize>
        </div>
    </nav>
</header>
</body>
</html>