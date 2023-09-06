<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
    <jsp:include page="../../fragments/header.jsp"></jsp:include>
    <div class="flex max-w-7xl mx-auto flex-col justify-center px-6 lg:px-8 py-20">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">회원가입</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form id="joinForm" modelAttribute="userJoinDto"  class="space-y-6" action="/users/join" method="POST">
                <div>
                    <label for="loginId" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                    <div class="mt-2">
                        <input id="loginId"
                               name="loginId"
                               type="text"
                               class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                        >
                        <form:errors path="loginId"/>
                    </div>
                </div>

                <div>
                    <label for="password" class="block text-sm font-medium leading-6 text-gray-900">패스워드</label>
                    <div class="mt-2">
                        <input id="password" name="password" type="password" required class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>

                <div>
                    <label for="username" class="block text-sm font-medium leading-6 text-gray-900">닉네임</label>
                    <div class="mt-2">
                        <input id="username" name="username" type="text" required class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                    </div>
                </div>

                <div>
                    <button type="button"
                            id="joinBtn"
                            onclick="join()"
                            class="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-3 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        회원가입
                    </button>
                </div>
                

            </form>
        </div>
    </div>
</body>

<script>
    const join = async ()=>{
        // 유효성 검사
        const loginId = $("#loginId").val();
        const password = $("#password").val();
        const username = $("#username").val();

        const regIdPw = /^[a-zA-Z0-9]{4,12}$/;
        const regName = /^[가-힣a-zA-Z0-9]{2,15}$/;

        if (!regIdPw.test(loginId)) {
            alert("아이디는 숫자, 영문 4글자 이상 12자 이하만 가능합니다.")
            return;
        }
        if (!regIdPw.test(password)) {
            alert("패스워드 숫자, 영문 4글자 이상 12자 이하만 가능합니다.")
            return;
        }
        if (!regName.test(username)) {
            alert("닉네임은 숫자, 영문, 한글 2글자 이상 15글자 이하만 가능합니다.")
            return;
        }

        const requestBody = {
            loginId,
            password,
            username
        }
        const result = await proceedJoinAPI(requestBody);
        if(result==200){
            alert("회원가입 축하드립니다. 로그인 부탁드립니다.")
            location.href="/users/login"
        }else if(result==409){
            alert("아이디가 중복됩니다. 다른 아이디로 시도해주세요.")
        }

    }



    const proceedJoinAPI = async (requestBody)=>{
        const resp = await fetch("http://localhost:8080/users/join", {
            method:"POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody)
        });
        return resp.status
    }

</script>
</html>