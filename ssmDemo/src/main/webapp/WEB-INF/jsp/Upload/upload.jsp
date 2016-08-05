<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>上传实例</title>
<link href="<%=request.getContextPath()%>/static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/static/js/jQuery/jquery-2.1.4.min.js"></script>
<script src="<%=request.getContextPath()%>/static/js/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<!-- Modal -->
<div id="batchImportModal" class="modal fade" role="dialog"
    aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">批量导入用户</h4>
            </div>
            <div class="modal-body">
                <div class="form-group" id="passwordDiv">
                    <label>选择用户数据文件</label> 
                    <input class="form-control" type="file" id="batchFile">
                </div>
                <div class="progress progress-striped active" style="display: none">
                    <div id="progressBar" class="progress-bar progress-bar-info"
                        role="progressbar" aria-valuemin="0" aria-valuenow="0"
                        aria-valuemax="100" style="width: 0%;background-color:green;background:green;color:green;">
                        <span class = "sr-only" id="progressVal">0%</span>
                    </div>
                </div>
                <div class="form-group">
                    <input id="batchUploadBtn" type="submit" name="submit" class="btn btn-success" value="上传" />
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
</body>
<script type='text/javascript'> 
$(function() {
    // 批量导入按钮
    //$("#batchImportBtn").click(function(){
        $('#batchImportModal').modal('show');
    //})
    var base = $("#base").val();//.trim();
    // 上传按钮
    $("#batchUploadBtn").attr('disabled', true);
    // 上传文件按钮点击的时候
    $("#batchUploadBtn").click(function() {
        // 进度条归零
        $("#progressBar").width("0%");
        // 上传按钮禁用
        $(this).attr('disabled', true);
        // 进度条显示
        $("#progressBar").parent().show();
        $("#progressBar").parent().addClass("active");
        // 上传文件
        UpladFile();
    })

    // 文件修改时
    $("#batchFile").change(function() {
        $("#batchUploadBtn").val("上传");
        $("#progressBar").width("0%");
        var file = $(this).prop('files');
        if (file.length != 0) {
            $("#batchUploadBtn").attr('disabled', false);
        }

    });

    function UpladFile() {
        var fileObj = $("#batchFile").get(0).files[0]; // js 获取文件对象
        console.info("上传的文件："+fileObj);
        //获取当前项目的路径
        var urlRootContext = (function () {
            var strPath = window.document.location.pathname;
            var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
            return postPath;
        })();
        var FileController = urlRootContext + "/User/upload.do"; // 接收上传文件的后台地址 
        // FormData 对象
        var form = new FormData();
        // form.append("author", "hooyes"); // 可以增加表单数据
        form.append("file", fileObj); // 文件对象
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function(data) {
            // ShowSuccess("上传完成");
            if(data.target.responseText==""){
            	alert("上传成功");
            	window.location.href = "<%=request.getContextPath()%>/User/showAllUser.do";
            }else{
            	alert(data.target.responseText);
            }
            $("#batchUploadBtn").attr('disabled', false);
            $("#batchUploadBtn").val("上传");
            $("#progressBar").parent().removeClass("active");
            $("#progressBar").parent().hide();
            //$('#myModal').modal('hide');
        };
        xhr.upload.addEventListener("progress", progressFunction, false);
        xhr.send(form);
    }
    ;
    function progressFunction(evt) {
        var progressBar = $("#progressBar");
        if (evt.lengthComputable) {
            var completePercent = Math.round(evt.loaded / evt.total * 100)+ "%";
            progressBar.width(completePercent);
            $("#batchUploadBtn").val("正在上传 " + completePercent);
            $("#progressVal").text(completePercent);
        }
    };
});
</script>
</html>