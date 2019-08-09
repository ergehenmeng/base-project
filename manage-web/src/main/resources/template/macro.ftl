<#macro search placeholder advance=false>
    <div class="left">
        <form id="queryForm" method="post">
            <input name="queryName" placeholder="${placeholder}" /><a href="#" onclick="$.fn.dataGridOptions.searchFun('#queryForm');" class="searchBtn"><i class="fa fa-search"></i>&nbsp;查询</a>
            <#if advance>
                <a href="#" class="dropBtn">查询条件<i class="fa fa-angle-double-down"></i></a>
            </#if>
        </form>
    </div>
    <#if advance >
        <form id="showAdw" method="post">
            <ul class="showAdw" style="display: none;">
                <a href="javascript:void(0);" class="close"><i class="fa fa-remove fa-lg"></i></a>
            <#nested>
                <li>
                    <div class="submitBtn">
                        <a href="javascript:void(0);" class="searchBtn"
                           onclick="$.fn.dataGridOptions.searchFun('#showAdw');">确定</a>
                        <a href="javascript:void(0);" class="searchBtn"
                           onclick="$.fn.dataGridOptions.cleanFun('#searchForm');">重置</a>
                    </div>
                </li>
            </ul>
        </form>
        <script type="text/javascript" src="/static/js/search.js?v=${version!}"></script>
    </#if>
</#macro>

