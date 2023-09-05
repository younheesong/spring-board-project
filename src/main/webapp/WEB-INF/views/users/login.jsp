<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
</head>
<script src="https://cdn.tailwindcss.com"></script>
<body>
    <jsp:include page="../../fragments/header.jsp"></jsp:include>
    <div class="flex max-w-7xl mx-auto flex-col justify-center px-6 lg:px-8 py-20">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">로그인</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" action="#" method="POST">
                <div>
                    <label for="loginId" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                    <div class="mt-2">
                        <input id="loginId" name="loginId" type="text" required class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>

                <div>
                    <label for="password" class="block text-sm font-medium leading-6 text-gray-900">패스워드</label>
                    <div class="mt-2">
                        <input id="password" name="password" type="password" required class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>

                <div>
                    <button type="submit"
                            class="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-3 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        로그인
                    </button>
                </div>
            </form>

            <p class="mt-10 text-center text-sm text-gray-500">
                회원이 아니신가요?
                <a href="/users/join" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">회원가입 하러가기</a>
            </p>
        </div>
    </div>
</body>
</html>