<html>

<head>

</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<body>
<nav class="flex flex-1 flex-col" aria-label="Sidebar">
    <ul role="list" class="-mx-2 space-y-1 w-40">
        <li>
            <a href="/boards/java" id="java" class="text-gray-700 hover:text-indigo-600 hover:bg-gray-50 group flex gap-x-3 rounded-md p-2 pl-3 text-sm leading-6 font-semibold">Java</a>
        </li>
        <li>
            <a href="/boards/javascript" id="javascript" class="text-gray-700 hover:text-indigo-600 hover:bg-gray-50 group flex gap-x-3 rounded-md p-2 pl-3 text-sm leading-6 font-semibold">Javascript</a>
        </li>
        <li>
            <a href="/boards/spring" id="spring" class="text-gray-700 hover:text-indigo-600 hover:bg-gray-50 group flex gap-x-3 rounded-md p-2 pl-3 text-sm leading-6 font-semibold">Spring</a>
        </li>
        <li>
            <a href="/boards/react" id="react" class="text-gray-700 hover:text-indigo-600 hover:bg-gray-50 group flex gap-x-3 rounded-md p-2 pl-3 text-sm leading-6 font-semibold">React</a>
        </li>
    </ul>
</nav>

</body>

<script>
    window.addEventListener('DOMContentLoaded', function(){
        const categories = ['java', 'javascript', 'spring', 'react']
        const pathArray = window.location.pathname.split("/")
        const category = pathArray[2];

        if(category && categories.includes(category)){

            $('#'+category).addClass('bg-gray-50 text-indigo-600').removeClass('text-gray-700 hover:text-indigo-600 hover:bg-gray-50');
        }
    });
</script>
</html>