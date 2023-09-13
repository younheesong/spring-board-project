<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>홈</title>
</head>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<body>
<jsp:include page="../../fragments/header.jsp"></jsp:include>
<div class="max-w-4xl mx-auto px-6 lg:px-8 py-20 ">

    <h2 class="text-center text-3xl font-bold mb-16">${type=='write'?'글 작성': '글 편집'}</h2>

    <form id="form" action method="post" enctype="multipart/form-data">
        <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">

            <div class="sm:col-span-3">
                <label for="category" class="block text-sm font-medium leading-6 text-gray-900">카테고리</label>
                <div class="mt-2">
                    <select id="category" name="category"
                            class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6">
                        <option value="JAVA">Java</option>
                        <option value="JAVASCRIPT">Javascript</option>
                        <option value="SPRING">Spring</option>
                        <option value="REACT">React</option>
                    </select>
                </div>
            </div>

            <div class="col-span-full">
                <label for="title" class="block text-sm font-medium leading-6 text-gray-900">제목</label>
                <div class="mt-2">
                    <input type="text" name="title" id="title" value="${title}"
                           class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                </div>
            </div>

            <div class="col-span-full">
                <label for="content" class="block text-sm font-medium leading-6 text-gray-900">내용</label>
                <div class="mt-2">
                    <textarea id="content" name="content" rows="3"
                              class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">${content}</textarea>
                </div>
            </div>

            <div class="col-span-full">
                <label class="block text-sm font-medium leading-6 text-gray-900 mb-5">첨부파일</label>
                <div class="flex gap-2 items-center">
                    <ul id="fileList" class="flex gap-2 flex-wrap">
                        <c:if test="${!empty files}">
                            <c:forEach items="${files}" var="file">
                                <label class="relative block w-32 h-32 rounded-lg border-2 border-dashed border-gray-300 p-12 text-center hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z"/>
                                    </svg>
                                    <input type="hidden" name="oldFiles" value=${file.getId()}>

                                    <img src="/images/${file.getSavedFileName()}"
                                         class="w-28 h-28 absolute z-10 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"/>

                                    <svg class="absolute z-50 top-0 right-0" name="file-delete" width="36" height="36"
                                         viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z"
                                              stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                </label>
                            </c:forEach>
                        </c:if>


                        <label for="fileInput1"
                               class="relative block w-32 h-32 rounded-lg border-2 border-dashed border-gray-300 p-12 text-center hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                            <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z"/>
                            </svg>
                            <input id="fileInput1" name=${type=='write'?'files': 'newFiles'} type="file" class="sr-only"
                                   onchange="updatePreview(1)">
                            <img id="fileImage1"/>

                            <svg class="absolute z-50 top-0 right-0" name="file-delete" width="36" height="36"
                                 viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z"
                                      stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                <path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                <path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                            </svg>
                        </label>

                    </ul>

                    <button type="button" onclick="addFile()"
                            class="rounded-full bg-indigo-600 p-2 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                            <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z"/>
                        </svg>
                    </button>
                </div>
                <div> 최대 10개까지 등록이 가능하며, 각 파일 당 1MB 넘지 않아야 합니다.</div>
            </div>


            <div class="col-span-full">
                <button type="button"
                        onclick="uploadBoard()"
                        class="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-3 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                    ${type=='write'?'등록': '저장'}
                </button>
            </div>
        </div>
    </form>


</div>

</body>
<script>

    window.addEventListener('DOMContentLoaded', function(){
        // 초기 카테고리 설정
        if('${category}') {
            $("#category").val(${category}).prop("selected", true);
        }
        // 첨부파일 delete button click 액션 지정
        $("svg[name='file-delete']").on("click", function (e) {
            e.preventDefault();
            console.log("delete")
            deleteFile($(this));
        });
    });

    let fileIdx = 1;
    const addFile = () => {
        console.log('add')
        fileIdx++;
        const inputId = "fileInput" + fileIdx;
        const imgId = "fileImage" + fileIdx;
        console.log(imgId);
        const str = '<label for=' + inputId + ' class="relative block w-32 h-32 rounded-lg border-2 border-dashed border-gray-300 p-12 text-center hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"> ' +
            '<svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true"> ' +
            '<path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z"/> ' +
            '</svg> ' +
            '<input id=' + inputId +
            ' name="files" type="file" class="sr-only" onchange="updatePreview(\'' + fileIdx + '\')"/>' +
            '<img id=' + imgId + ' class="" /> ' +
            '<svg class="absolute z-50 top-0 right-0" name="file-delete" width="36" height="36" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"> ' +
            '<path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '<path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '<path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '</svg>'
        '</label>'
        ;
        $("#fileList").append(str);
        $("svg[name='file-delete']").on("click", function (e) {
            e.preventDefault();
            deleteFile($(this));
        });
    }

    const updatePreview = (number) => {
        console.log(number);

        console.log(document.getElementById("fileInput" + number))

        const file = document.getElementById("fileInput" + number)?.files[0];
        const maxSize = 1024 * 1024; //1MB
        const ext = document.getElementById("fileInput" + number).value.split(".").pop().toLowerCase();


        // 파일 체크
        if ($.inArray(ext, ["gif", "jpg", "jpeg", "png"]) == -1) {

            alert("gif, jpg, jpeg, png 파일만 업로드 해주세요.");
            document.getElementById("fileInput" + number).value = '';
            return;

        }

        // 용량 체크
        if (file.size > maxSize) {
            alert("파일 용량 1MB를 초과하였습니다.");
            document.getElementById("fileInput" + number).value = '';
            return;
        } else {
            console.log("용량 통과")
        }

        if (file) {
            let reader = new FileReader();
            console.log(file)
            console.log("fileImage" + number)

            reader.readAsDataURL(file);
            reader.onload = function () {
                $('#fileImage' + number).attr('src', reader.result);
                $('#fileImage' + number).addClass('w-28 h-28 absolute z-10 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 ')

            }
        }
    }



    const deleteFile = (obj) => {
        obj.parent().remove();
    }

    const uploadBoard = ()=>{
        // title, content null 체크
        if( $('#title').val()=='' || $('#content').val()=='' ){
            alert("제목과 내용을 입력해주세요.");
            return;
        }

        $("#form").submit();
    }


</script>
</html>