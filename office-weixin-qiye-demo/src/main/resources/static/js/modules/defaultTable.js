function tableHeight() {
    return $(window).height() - 50;
}

function resize(tableId) {
    //根据窗口调整表格高度
    $(window).resize(function () {
        $('#' + tableId).bootstrapTable('resetView', {
            height: tableHeight()
        })
    });
}

/**
 * 列的格式化函数 在数据从服务端返回装载前进行处理
 * @param  {[type]} value [description]
 * @param  {[type]} row   [description]
 * @param  {[type]} index [description]
 * @param option 操作栏html
 * @return {[type]}       [description]
 */
function infoFormatter(value, row, index, option) {
    return option;
}

function renderTable(tableId, url, columns, toolbarId) {
    // 数据表格渲染
    $('#' + tableId).bootstrapTable({
        url: url,//数据源
        // dataField: "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
        height: tableHeight(),//高度调整
        search: true,//是否搜索
        pagination: true,//是否分页
        pageSize: 20,//单页记录数
        pageNumber : 1, //当前第几页
        pageList : [ 10, 25, 50, 100 ], //记录数可选列表
        sidePagination: "server",//服务端分页
        cache: false,//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）,区别客户端分页
        contentType: "application/x-www-form-urlencoded",//请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
        dataType: "json",//期待返回数据类型
        method: "post",//请求方式
        searchAlign: "left",//查询框对齐方式
        // queryParamsType: "limit",//查询参数组织方式
        // queryParams: function getParams(params) {
        //     //params obj
        //     params.other = "otherInfo";
        //     return params;
        // },
        searchOnEnterKey: false,//回车搜索
        // showRefresh: true,//刷新按钮
        // showColumns: true,//列选择按钮
        buttonsAlign: "left",//按钮对齐方式
        toolbar: "#" + toolbarId,//指定工具栏
        toolbarAlign: "right",//工具栏对齐方式
        columns: columns,
        responseHandler: function (res) {
            if (res.data === undefined || res.data == null) {
                return {
                    "total" : 0,
                    "rows" : []
                }
            } else {
                return {
                    "total" : res.count,
                    "rows" : res.data
                }
            }
        },
        // onClickRow: function (row, $element) {
        //     //$element是当前tr的jquery对象
        //     $element.css("background-color", "green");
        // },//单击row事件
        locale: "zh-CN", //中文支持
        // detailView: true, //是否显示详情折叠
        // detailFormatter: function (index, row, element) {
        //     return  '<button type="button" class="btn btn-default btn-sm" style="margin-right:15px;">A权限</button>' +
        //         '<button type="button" class="btn btn-default btn-sm" style="margin-right:15px;">B权限</button>' +
        //         '<button type="button" class="btn btn-default btn-sm" style="margin-right:15px;">C权限</button>' +
        //         '<button type="button" class="btn btn-default btn-sm" id="bind-cell" style="margin-right:15px;">绑定D</button>' +
        //         '<button type="button" class="btn btn-default btn-sm" id="edit=cell" style="margin-right:15px;">编辑</button>';
        // }
    });
}

function refresh(tableId) {
    $("#" + tableId).bootstrapTable('refresh');
}

var defaultTable = {
    renderTable: renderTable,
    resize: resize,
    refresh: refresh,
    infoFormatter: infoFormatter
};