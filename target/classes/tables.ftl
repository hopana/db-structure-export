<html>
<head>
    <title>Schema for database ${database}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <style type="text/css">
        .toptext {
            font-family: verdana, sans-serif;
            color: #000000;
            font-size: 20px;
            font-weight: 600;
            width: 550px;
            background-color: #999999;
        }

        .normal {
            font-family: Verdana, Arial, Helvetica, sans-serif;
            font-size: 11px;
            font-weight: normal;
            color: #000000
        }

        .fieldheader {
            font-family: verdana, sans-serif;
            color: #000000;
            font-size: 12px;
            font-weight: 600;
            background-color: #c0c0c0;
        }

        .fieldcolumn {
            font-family: verdana, sans-serif;
            color: #000000;
            font-size: 10px;
            font-weight: 600;
            background-color: #ffffff;
        }

        .c1 {
            width: 330px;
        }

        .c2 {
            width: 150px;
        }

        .c3 {
            width: 100px;
        }

        .c4 {
            width: 100px;
        }

        .c5 {
            width: 100px;
        }

        .c6 {
            width: 500px;
        }

        .header {
            background-color: #ECE9D8;
        }

        .headtext {
            font-family: verdana, sans-serif;
            color: #000000;
            font-size: 12;
            font-weight: 600;
            width: 550px;
            background-color: #999999;
        }

        br.page {
            page-break-after: always
        }

        table.table-content {
            font-family: "微软雅黑", verdana, arial, sans-serif;
            font-size: 14px;
            color: #444;
            border-width: 1px;
            border-color: #999999;
            border-collapse: separate;
        }

        table.table-content th {
            background-color: #eee;
            border-width: 1px;
            padding: 4px;
            border-style: solid;
            /*border-color: #a9c6c9;*/
        }

        table.table-content tr {
            /*background-color: #d4e3e5;*/
        }

        table.table-content td {
            border-width: 1px;
            padding: 4px;
            border-style: solid;
            /*border-color: #a9c6c9;*/
        }
    </style>
</head>
<body bgcolor='#ffffff' topmargin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td class="toptext"><p align="center">${database}</td>
    </tr>
</table>
<a name="header">&nbsp</a>
<ul>
    <#if tables?? && tables?size gt 0>
        <#list tables as table>
            <li><a href="#${table.tableName}"><p class="normal">${table.tableName} (${table.tableComment!})</a></li>
        </#list>
    </#if>
</ul>

<br class=page>

<#if tables?? && tables?size gt 0>
    <#list tables as table>
        <p><a name='${table.tableName}'>&nbsp</a>
        <table class="table-title" width="100%" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td class="headtext" width="30%" align="left" valign="top">${table.tableName} (${table.tableComment!})</td>
                <td>&nbsp</td>
            <tr>
        </table>

        <table width="100%" border="0" cellspacing="0" cellpadding="3">
            <tr>
                <td class="fieldheader" width="100%" align="left" valign="top">Fields</td>
            </tr>
        </table>
        <table class="table-content" width="100%" cellspacing="0" cellapdding="2" border="1">
            <tr>
                <td align="center" valign="top" class="fieldcolumn c1">名称</td>
                <td align="center" valign="top" class="fieldcolumn c2">类型</td>
                <td align="center" valign="top" class="fieldcolumn c3">可为空</td>
                <td align="center" valign="top" class="fieldcolumn c4">默认</td>
                <td align="center" valign="top" class="fieldcolumn c5">存储</td>
                <td align="center" valign="top" class="fieldcolumn c6">注释</td>
            </tr>
            <#if table.columns?? && table.columns?size gt 0>
                <#list table.columns as column>
                    <tr>
                        <td align="left" valign="top"><p class="normal">${column.columnName}</td>
                        <td align="left" valign="top"><p class="normal">${column.dataType}</td>
                        <td align="left" valign="top"><p class="normal">${column.nullable}</td>
                        <td align="left" valign="top"><p class="normal">${column.dataDefault!}</td>
                        <td align="left" valign="top"><p class="normal">--</td>
                        <td align="left" valign="top"><p class="normal">${column.columnComments!}</td>
                    </tr>
                </#list>
            </#if>
        </table>
        <!-- TODO:索引 -->
        <a href="#header"><p class="normal">Back</a><br class=page>
    </#list>
</#if>

<h1 width="100%">
</body>
</html>