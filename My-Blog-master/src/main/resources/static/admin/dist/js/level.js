$(function () {
    $("#jqGrid").jqGrid({
        url: '/bug/levels/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'levelId', index: 'levelId', width: 50, key: true, hidden: true},
            {label: 'BUG状态', name: 'levelSts', index: 'levelSts',width: 100},
            {label: 'BUG描述', name: 'levelName', index: 'levelName', width: 240},
            // {label: 'BUG图标', name: 'levelIcon', index: 'levelIcon', width: 120, formatter: imgFormatter},
            {label: '添加时间', name: 'levelCreateTime', index: 'levelCreateTime', width: 120}
        ],
        height: 600,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    jQuery("select.image-picker").imagepicker({
        hide_select: false,
    });

    jQuery("select.image-picker.show-labels").imagepicker({
        hide_select: false,
        show_label: true,
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    var container = jQuery("select.image-picker.masonry").next("ul.thumbnails");
    container.imagesLoaded(function () {
        container.masonry({
            itemSelector: "li",
        });
    });

});


function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值
{
    var days = 7; //设置cookie保留7天
    var datime= new Date();
    time.setTime(exp.getTime() + days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";aa=" + datime.toGMTString();
}

//给cookie的name赋值
function savecookie(levelName,levelSts){
    // var levelName=$("#levelName").val();//#orderno ：表身input标签的id
    // var levelSts=$("#levelSts").val();
    SetCookie("levelName",levelName);
    SetCookie("levelSts",levelSts);
}

$("#levelName").val(getCookie("levelName"));
$("#levelSts").val(getCookie("levelSts"));

//获取cookie的值
function getCookie(name)//取cookies函数
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return (arr[2]); return null;
}


function imgFormatter(cellvalue) {
    return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"64\" width=\"64\" alt='icon'/></a>";
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function levelAdd() {
    reset();
    $('.modal-title').html('分类添加');
    $('#categoryModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var levelName = $("#levelName").val();
    if (!validCN_ENString2_18(levelName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的级别名称！");
    } else {
        var params = $("#categoryForm").serialize();
        var url = '/bug/levels/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/bug/levels/update';
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                }
                else {
                    $('#categoryModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

function levelEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('.modal-title').html('级别编辑');
    $('#categoryModal').modal('show');
    $("#levelId").val(id);
}

function deleteLevel() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/bug/levels/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

function deleteAll() {
    // var ids = getSelectedRows();
    // if (ids == null) {
    //     return;
    // }
    swal({
        title: "确认弹框",
        text: "确认要删除库中所有历史BUG数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "GET",
                    url: "/bug/levels/deleteAll",
                    contentType: "application/json",
                    success: function (r) {
                        if (r.resultCode == 200) {
                            // swal("删除成功1", {
                            swal(r.message, {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}


function reset() {
    $("#levelName").val('');
    $("#levelIcon option:first").prop("selected", 'selected');
}