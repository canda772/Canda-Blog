var blogEditor;
// Tags Input
$('#blogTags').tagsInput({
    width: '100%',
    height: '38px',
    defaultText: 'BUG级别'
});

//Initialize Select2 Elements
$('.select2').select2()

$(function () {
    blogEditor = editormd("blog-editormd", {
        width: "100%",
        height: 640,
        syncScrolling: "single",
        path: "/admin/plugins/editormd/lib/",
        toolbarModes: 'full',
        /**图片上传配置*/
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"], //图片上传格式
        imageUploadURL: "/admin/blogs/md/uploadfile",
        onload: function (obj) { //上传成功之后的回调
        }
    });

    // 编辑器粘贴上传
    document.getElementById("blog-editormd").addEventListener("paste", function (e) {
        var clipboardData = e.clipboardData;
        if (clipboardData) {
            var items = clipboardData.items;
            if (items && items.length > 0) {
                for (var item of items) {
                    if (item.type.startsWith("image/")) {
                        var file = item.getAsFile();
                        if (!file) {
                            alert("请上传有效文件");
                            return;
                        }
                        var formData = new FormData();
                        formData.append('file', file);
                        var xhr = new XMLHttpRequest();
                        xhr.open("POST", "/admin/upload/file");
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState == 4 && xhr.status == 200) {
                                var json=JSON.parse(xhr.responseText);
                                if (json.resultCode == 200) {
                                    blogEditor.insertValue("![](" + json.data + ")");
                                } else {
                                    alert("上传失败");
                                }
                            }
                        }
                        xhr.send(formData);
                    }
                }
            }
        }
    });

    new AjaxUpload('#uploadCoverImage', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#blogCoverImage").attr("src", r.data);
                $("#blogCoverImage").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});



$('#confirmButton').click(function () {
    var bugName = $('#bugName').val();
    var bugLocation = $('#bugLocation').val();
    var bugLevel = $('#bugLevel').val();
    var bugInfo = blogEditor.getMarkdown();
    if (isNull(bugName)) {
        swal("请输入BUG名称", {
            icon: "error",
        });
        return;
    }
    if (!validLength(bugName, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (!validLength(bugLocation, 150)) {
        swal("路径过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(bugLevel)) {
        swal("请选择BUG级别", {
            icon: "error",
        });
        return;
    }
    if (isNull(bugInfo)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(bugInfo, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    $('#articleModal').modal('show');
});

$('#saveButton').click(function () {
    var bugId = $('#bugId').val();
    var bugName = $('#bugName').val();
    var bugLocation = $('#bugLocation').val();
    var bugLevel = $('#bugLevel').val();
    var bugInfo = blogEditor.getMarkdown();
    var bugSts = $("input[name='bugSts']:checked").val();
    var url = '/bug/bugs/save';
    var swlMessage = '保存成功';
    var data = {
        "bugName": bugName, "bugLocation": bugLocation, "bugLevel": bugLevel,
        "bugInfo": bugInfo, "bugSts": bugSts
    };
    if (bugId > 0) {
        url = '/bug/bugs/update';
        swlMessage = '修改成功';
        data = {
            "bugId": bugId,
            "bugName": bugName,
            "bugLocation": bugLocation,
            "bugLevel": bugLevel,
            "bugInfo": bugInfo,
            "bugSts": bugSts
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回博客列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/bug/bugs";
                })
            }
            else {
                $('#articleModal').modal('hide');
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
});

$('#cancelButton').click(function () {
    window.location.href = "/bug/bugs";
});

