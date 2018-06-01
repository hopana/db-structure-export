<html>
<head>
    <title>PL/SQL Developer Export</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <style type="text/css">
        table.hovertable {
            font-family: "微软雅黑", verdana, arial, sans-serif;
            font-size: 14px;
            color: #444;
            border: 1px #999999;
            border-collapse: separate;
        }

        table.hovertable th {
            background-color: #eee;
            padding: 4px;
            border: 1px solid #a9c6c9;
        }

        table.hovertable tr {
            /*background-color: #d4e3e5;*/
        }

        table.hovertable td {
            padding: 4px;
            border: 1px solid #a9c6c9;
        }
    </style>
</head>
<body>


<table BORDER="1" class="hovertable">
    <tr>
        <th>名称</th>
        <th>类型</th>
        <th>可为空</th>
        <th>默认</th>
        <th>存储</th>
        <th>注释</th>
    </tr>
    <#if table.rows?? && table.rows?size gt 0>
        <#list rows as row>
            <tr>
                <td>${row.colName}</td>
                <td>${row.colType}</td>
                <td>${row.isNull}</td>
                <td>${row.defaultValue}</td>
                <td>${row.colStorage}</td>
                <td>${row.colComment}</td>
            </tr>
        </#list>
    </#if>
</table>
</body>
</html>
