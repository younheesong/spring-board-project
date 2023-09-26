<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>홈</title>
</head>
<script src="https://cdn.tailwindcss.com"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<style>
    .ck-editor__editable {
        min-height: 400px;
    }
</style>
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
                    <input type="text" name="title" id="title" value="${board.title}"
                           class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
                </div>
            </div>

            <div class="col-span-full">
                <label for="editor" class="block text-sm font-medium leading-6 text-gray-900">내용</label>
                <div class="mt-2">
                    <textarea id="editor" name="content" rows="3"
                              class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">${board.content}</textarea>


                </div>
            </div>

            <div class="col-span-full">
                <label class="block text-sm font-medium leading-6 text-gray-900 mb-5">첨부파일</label>
                <ul id="fileList" class="flex flex-col gap-2 flex-wrap">
                    <c:if test="${!empty files}">
                        <c:forEach items="${files}" var="file">
                            <label class="relative flex w-full items-center gap-2">
                                <div class="rounded-md px-5 py-2 border text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100">
                                    파일 등록
                                </div>
                                <input type="hidden" name="oldFiles" value=${file.getId()}>

                                <a target="_blank" class="font-bold" href="/files/${file.getSavedFileName()}">
                                    ${file.getOriginalFileName()}
                                </a>

                                <svg name="file-delete" width="36" height="36"
                                     viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z"
                                          stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                    <path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                    <path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                                </svg>
                            </label>
                        </c:forEach>
                    </c:if>


                    <div class="relative flex w-full items-center gap-2">
                        <label for="fileInput1">
                            <div class="rounded-md px-5 py-2 border text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100">
                                파일 등록
                            </div>
                            <input id="fileInput1" name=${type == 'write'? 'files' : 'newFiles'} type="file"
                                   class="sr-only"
                                   onchange="updatePreview(1)">
                        </label>
                        <a id="fileLink1" target="_blank" class="font-bold"></a>
                        <svg name="file-delete"
                             id="fileDelete1"
                             class="hidden"
                             width="36" height="36"
                             viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z"
                                  stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                            <path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                            <path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/>
                        </svg>
                    </div>
                </ul>

                <button type="button"
                        onclick="addFile()"
                        class="rounded-full bg-indigo-600 mt-2 p-2 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                        <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z"/>
                    </svg>
                </button>
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
<script type="text/javascript" src="../../../ckeditor/ckeditor.js"></script>
<script>
    let editor;
    $(function () {
        editor = CKEDITOR.replace('editor');
    })

    window.addEventListener('DOMContentLoaded', function () {
        // 초기 카테고리 설정
        if ('${category}') {
            $("#category").val('${category}').prop("selected", true);
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
        if ($("#fileList").children().length >= 10) {
            alert("최대 10개까지 등록 가능합니다.")
            return;
        }
        fileIdx++;
        const inputId = "fileInput" + fileIdx;
        const inputName = '${type}' == 'write' ? 'files' : 'newFiles'

        const str = '<div class="relative flex flex-row w-full items-center gap-2">' +
            '<label for=' + inputId + '> ' +
            '<div class="rounded-md px-5 py-2 border text-sm font-semibold text-indigo-600 shadow-sm hover:bg-indigo-100"> ' +
            '파일 등록' +
            '</div> ' +
            '<input id=' + inputId +
            ' name=' + inputName + ' type="file" class="sr-only" onchange="updatePreview(\'' + fileIdx + '\')"/>' +
            '</label>'+
            '<a id="fileLink'+ fileIdx + '" target="_blank" class="font-bold"></a>' +
            '<svg id="fileDelete' + fileIdx +
            '" name="file-delete" class="hidden" width="36" height="36" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"> ' +
            '<path d="M12 21C10.8181 21 9.64778 20.7672 8.55585 20.3149C7.46392 19.8626 6.47177 19.1997 5.63604 18.364C4.80031 17.5282 4.13738 16.5361 3.68508 15.4442C3.23279 14.3522 3 13.1819 3 12C3 10.8181 3.23279 9.64778 3.68508 8.55585C4.13738 7.46392 4.80031 6.47177 5.63604 5.63604C6.47177 4.80031 7.46392 4.13738 8.55585 3.68508C9.64778 3.23279 10.8181 3 12 3C13.1819 3 14.3522 3.23279 15.4442 3.68508C16.5361 4.13738 17.5282 4.80031 18.364 5.63604C19.1997 6.47177 19.8626 7.46392 20.3149 8.55585C20.7672 9.64778 21 10.8181 21 12C21 13.1819 20.7672 14.3522 20.3149 15.4442C19.8626 16.5361 19.1997 17.5282 18.364 18.364C17.5282 19.1997 16.5361 19.8626 15.4441 20.3149C14.3522 20.7672 13.1819 21 12 21L12 21Z" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '<path d="M9 9L15 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '<path d="M15 9L9 15" stroke="#33363F" stroke-width="2" stroke-linecap="round"/> ' +
            '</svg>'+
            '</div>'
        ;


        $("#fileList").append(str);
        $("svg[name='file-delete']").on("click", function (e) {
            e.preventDefault();
            deleteFile($(this));
        });
    }

    const updatePreview = (number) => {
        const file = document.getElementById("fileInput" + number)?.files[0];
        console.log(file);
        const maxSize = 1024 * 1024; //1MB

        // 용량 체크
        if (file.size > maxSize) {
            alert("파일 용량 1MB를 초과하였습니다.");
            document.getElementById("fileInput" + number).value = '';
            return;
        }

        if (file) {
            console.log(file);
            let url = window.URL.createObjectURL(file);
            console.log(url);

            $('#fileLink' + number).attr('href', url);
            $('#fileLink' + number).text(file.name);
            $('#fileDelete'+ number).removeClass('hidden')
        }
    }


    const deleteFile = (obj) => {
        obj.parent().remove();
    }

    const uploadBoard = () => {
        // title, content null 체크
        console.log($('#title').val());
        console.log($('#editor').val());
        console.log(editor.getData());
        if ($('#title').val() == '' || editor.getData() == '') {
            alert("제목과 내용을 입력해주세요.");
            return;
        }

        $("#form").submit();
    }


</script>
</html>